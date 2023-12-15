import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Button, Stack, MenuItem, Grid, Box, Typography } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import FormDateTimePicker from '../../../components/form/FormDateTimePicker';
import SupplyOrderService from "../../../services/supplyOrderService";
import ClinicalHistoryService from "../../../services/clinicalHistoryService";
import PacientService from "../../../services/pacientService";
import OrderService from "../../../services/orderService";
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import { convertToLocalTimeZone, convertDateObjectToDayjs, formatNoteForEmr } from "../../../utils/EntityProcessingMethods";
import { capitalizeFirstLetter } from '../../../utils/CommonMethods';
import dayjs from 'dayjs';
import { useKeycloak } from '@react-keycloak/web'
import SupplyTable from "./SupplyTable";
import { useNavigate } from "react-router-dom";

function SupplyOrderForm({ action, preloadedData, id }) {

    const navigate = useNavigate();

    const { keycloak } = useKeycloak()

    const redirect = (path) => {
        navigate(path);
    };


    const [pacient, setPacient] = useState({})
    const [statuses, setStatuses] = useState([])
    const [chosenBatches, setChosenBatches] = useState([])

    const {
        handleSubmit,
        register,
        formState: { errors },
        control,
        watch,
        setValue,
        reset
    } = useForm({
        defaultValues: preloadedData
    });

    

    const pacientIdWatch = watch('pacientId')

    const addOrUpdateBatch = (batch) => {
        setChosenBatches(prevBatches => {
            const existingBatchIndex = prevBatches.findIndex(b => b.id === batch.id);

            if (existingBatchIndex !== -1) {
                // El objeto ya existe en el arreglo, actualizamos la propiedad assignedQuantity
                const updatedBatches = [...prevBatches];
                updatedBatches[existingBatchIndex].assignedQuantity = batch.assignedQuantity;
                return updatedBatches;
            } else {
                // El objeto no existe en el arreglo, lo agregamos
                return [...prevBatches, batch];
            }
        });
    };

    const removeBatch = (id) => {
        setChosenBatches(prevBatches => prevBatches.filter(batch => batch.id !== id));
    };


    useEffect(() => {
        console.log(pacient)
    }, [pacient])

    function fetchPractitioner() {
        keycloak.loadUserProfile().then((profile) => {
            setValue('practitionerId', profile.attributes.cc[0])
        })
    }

    useEffect(() => {
        async function fetchStatuses() {
            let data = await OrderService.getOrderStatuses();
            setStatuses(data);
        }

        fetchStatuses()
        fetchPractitioner()
    }, [])

    function handleReset() {
        reset()
        fetchPractitioner()
    }


    async function addSupplyOrder(supplyOrder) {

        const savedOrder = await SupplyOrderService.addSupplyOrder(supplyOrder);
        
        supplyOrder.id = savedOrder.id
        
        await ClinicalHistoryService.addNoteToEMR(pacient.id, formatNoteForEmr(supplyOrder));
        return savedOrder;
    }

    async function editSupplyOrder(supplyOrder) {
        supplyOrder.id = id;
        return await SupplyOrderService.editSupplyOrder(supplyOrder);
    }

    async function findClient() {
        let foundPacient = await PacientService.getPacientById({
            name: "idNumber",
            value: `${pacientIdWatch}`
        });

        if (!foundPacient || foundPacient.length === 0) {
            setPacient({})
            return undefined;
        }

        const firstItem = foundPacient[0];
        const fieldsList = firstItem.fieldsList;

        let name, lastName, secondLastName, middleName, idType, idNumber, age, birthDate, gender, phoneNumber, civilStatus, healthcareProvider, healthcareType;

        for (let i = 0; i < fieldsList.length; i++) {
            switch (fieldsList[i].name) {
                case 'Name':
                    name = fieldsList[i].value;
                    break;
                case 'lastName':
                    lastName = fieldsList[i].value;
                    break;
                case 'secondLastName':
                    secondLastName = fieldsList[i].value;
                    break;
                case 'middleName':
                    middleName = fieldsList[i].value;
                    break;
                case 'idType':
                    idType = fieldsList[i].value;
                    break;
                case 'idNumber':
                    idNumber = fieldsList[i].value;
                    break;
                case 'age':
                    age = fieldsList[i].value;
                    break;
                case 'birthDate':
                    birthDate = fieldsList[i].value;
                    break;
                case 'gender':
                    gender = fieldsList[i].value;
                    break;
                case 'phoneNumber':
                    phoneNumber = fieldsList[i].value;
                    break;
                case 'civilStatus':
                    civilStatus = fieldsList[i].value;
                    break;
                case 'healthcareProvider':
                    healthcareProvider = fieldsList[i].value;
                    break;
                case 'healthcareType':
                    healthcareType = fieldsList[i].value;
                    break;
            }
        }

        setPacient({ id:firstItem.id, name, lastName, secondLastName, middleName, idType, idNumber, age, birthDate, gender, phoneNumber, civilStatus, healthcareProvider, healthcareType });
    }

    function renderPacient() {
        let entries;
        let leftColumnEntries;
        let rightColumnEntries;
        if (pacient != {}) {
            entries = Object.entries(pacient);
            leftColumnEntries = entries.slice(0, 7);
            rightColumnEntries = entries.slice(7);
        }
        return (
            pacient != {} ?
                <Grid container spacing={2}>
                    <Grid item xs={6}>
                        {leftColumnEntries.map(([key, value], index) => (
                            <Typography key={index} variant="body1"><strong>{capitalizeFirstLetter(key)}</strong>: {value}</Typography>
                        ))}
                    </Grid>
                    <Grid item xs={6}>
                        {rightColumnEntries.map(([key, value], index) => (
                            <Typography key={index} variant="body1"><strong>{capitalizeFirstLetter(key)}</strong>: {value}</Typography>
                        ))}
                    </Grid>
                </Grid>
                :
                <Typography key={index} variant="body1">No se encontró el paciente</Typography>
        );
    }


    function addedSuccessfully() {
        triggerInfoAlert('success', 'La nueva orden ha sido agregada', handleReset)
    }
    function errorAdding() {
        triggerInfoAlert('error', 'Ha habido un error agregando la nueva orden')
    }

    function editedSuccessfully() {
        triggerInfoAlert('success', 'La orden ha sido editada', () => redirect(-1))
    }
    function errorEditing() {
        triggerInfoAlert('error', 'Ha habido un error editando la orden')
    }

    async function onSubmit(data) {

        if (chosenBatches.length == 0) {
            triggerInfoAlert('error', 'La orden debe de tener al menos 1 medicamento o consumible asociado')
            return;
        }
        if (!(pacient.name)) {
            triggerInfoAlert('error', 'El paciente no existe o no fue encontrado')
            return;
        }

        data = processOrder(data, chosenBatches)

        console.log(data)

        switch (action) {
            case 'add':
                addSupplyOrder(data)
                    .then((result) => {
                        setPacient({})
                        console.log(result)
                        addedSuccessfully()
                    })
                    .catch((error) => {
                        errorAdding()
                        console.error('Error en la operación:', error);
                    });
                break;
            case 'edit':

                editSupplyOrder(data)
                    .then((result) => {
                        if (result.status === 500) {
                            errorEditing()
                            console.error('Error en la operación:', result);
                        } else {
                            editedSuccessfully()
                        }
                    })
                break;
            default:
                break;
        }
    };

    function processOrder(data, batches) {
        data.type = 'supplyOrder';

        switch (action) {
            case 'add':
                data.authoredOn = convertToLocalTimeZone(data.authoredOn.toISOString());
                break;
            case 'edit':
                data.authoredOn = convertDateObjectToDayjs(data.authoredOn).toISOString();
                break;

            default:
                break;
        }

        console.log(batches)
        data.medicationBatchRequests = []
        data.batchRequests = []

        batches.forEach(batch => {
            let generalRequest = { quantity: batch.assignedQuantity }

            const { assignedQuantity, tableData, expirationDate, ...rest } = batch
            if (batch.cum) {

                generalRequest.medicationBatch = rest
                data.medicationBatchRequests.push(generalRequest);
            } else {
                generalRequest.batch = rest
                data.batchRequests.push(generalRequest);
            }
        });


        return data;
    }

    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)} noValidate>

                <Grid container>

                    <Grid item xs={12} lg={6} mb={5}>
                        <Stack spacing={2} width={500}>
                            <FormTextfield
                                isRequired
                                type='number'
                                disabled={action === 'edit' ? true : false}
                                label='CC del Paciente'
                                name='pacientId'
                                register={register}
                                errors={errors} />

                            {action === 'add' && <Button onClick={findClient} variant="contained" color="info">
                                Buscar paciente
                            </Button>}
                            {pacient.name ? renderPacient() : <Typography>No fue encontrado el paciente</Typography>}

                        </Stack>
                    </Grid>


                    <Grid item xs={12} lg={6}>
                        <Stack spacing={2} width={500}>

                            <FormTextfield
                                isRequired
                                type='number'
                                disabled
                                label='CC del Autorizador'
                                name='practitionerId'
                                register={register}
                                errors={errors} />

                            <FormDateTimePicker
                                disableFuture
                                label='Fecha de Autorización'
                                disabled={action === 'edit' ? true : false}
                                name='authoredOn'
                                control={control}
                                errors={errors}
                                value={action === 'edit' ? preloadedData?.authoredOn : dayjs()} />

                            {statuses.length > 0 &&
                                <FormSelect
                                    disabled={action === 'add' ? true : false}
                                    name="status"
                                    label="Estado"
                                    defaultValue={action === 'edit' ? preloadedData?.status : statuses[0]}
                                    register={register}
                                    errors={errors}
                                >

                                    {statuses.map(status => <MenuItem key={status} value={status}>
                                        {status}
                                    </MenuItem>)}
                                </FormSelect>
                            }

                            <FormTextfield
                                isRequired
                                disabled={action === 'edit' ? true : false}
                                label='Diagnostico'
                                name='diagnostic'
                                register={register}
                                errors={errors} />

                            <FormTextfield isRequired inputProps={{ maxLength: 255 }} multiline maxRows={7} label='Nota (máximo 255 caractéres)' name='note' register={register} errors={errors} />

                            <Button type="submit" variant="contained" color="info">
                                {action === 'add' ? 'Agregar orden' : 'Editar orden'}
                            </Button>
                        </Stack>
                    </Grid>
                </Grid>

            </form>
            <Box my={8} />
            <SupplyTable addOrUpdateBatch={addOrUpdateBatch} removeBatch={removeBatch}></SupplyTable>
            <DevTool control={control} />

        </>
    )
}

export default SupplyOrderForm