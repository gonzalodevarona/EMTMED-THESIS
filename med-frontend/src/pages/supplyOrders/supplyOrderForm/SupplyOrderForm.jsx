import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Stack, MenuItem, Grid, Box, Typography } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FabSubmitButton from '../../../components/buttons/FabSubmitButton';
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import FormDateTimePicker from '../../../components/form/FormDateTimePicker';
import SupplyOrderService from "../../../services/supplyOrderService";
import ClinicalHistoryService from "../../../services/clinicalHistoryService";
import PacientService from "../../../services/pacientService";
import OrderService from "../../../services/orderService";
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import FabActionButton from "../../../components/buttons/FabActionButton";
import { convertToLocalTimeZone, convertDateObjectToDayjs, formatNoteForEmr } from "../../../utils/EntityProcessingMethods";
import { capitalizeFirstLetter } from '../../../utils/CommonMethods';
import dayjs from 'dayjs';
import { useKeycloak } from '@react-keycloak/web'
import SupplyTable from "../../../components/tables/SupplyTable";
import { useNavigate } from "react-router-dom";
import SearchIcon from '@mui/icons-material/Search';
import { useTranslation } from 'react-i18next';

function SupplyOrderForm({ action, preloadedData, id }) {

    const { t } = useTranslation();

    const navigate = useNavigate();

    const { keycloak } = useKeycloak()

    const redirect = (path) => {
        navigate(path);
    };


    const [pacient, setPacient] = useState({})
    const [userRole, setUserRole] = useState('')
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
            setValue('practitionerId', profile.attributes.idNumber[0])
        })

        if (keycloak.hasRealmRole("ROLE_PRACTITIONER") || keycloak.hasRealmRole("ROLE_ADMIN")) {
            setUserRole('ROLE_PRACTITIONER')
        } else if (keycloak.hasRealmRole("ROLE_NURSE")) {
            setUserRole('ROLE_NURSE')
        }
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

        let firstname, lastName, secondLastName, middleName, idType, idNumber, age, birthDate, gender, phoneNumber, civilStatus, healthcareProvider, healthcareType;
        console.log(fieldsList)
        for (let i = 0; i < fieldsList.length; i++) {
            switch (fieldsList[i].name) {
                case 'firstName':
                    firstname = fieldsList[i].value;
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

        setPacient({ id: firstItem.id, firstname, lastName, secondLastName, middleName, idType, idNumber, age, birthDate, gender, phoneNumber, civilStatus, healthcareProvider, healthcareType });
    }

    function renderPacient() {
        let entries;
        let leftColumnEntries;
        let rightColumnEntries;
        if (pacient != {}) {
            let translatedObject = {};
            for (let key in pacient) {
                let translatedKey = t(`pacient.info.${key}`);
                translatedObject[translatedKey] = pacient[key];
            }
            
            entries = Object.entries(translatedObject);
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


    async function onSubmit(data) {

        if (chosenBatches.length == 0) {
            triggerInfoAlert('error', 'La orden debe de tener al menos 1 medicamento o insumo asociado')
            return;
        }
        if (!(pacient.firstname)) {
            triggerInfoAlert('error', 'El paciente no existe o no fue encontrado')
            return;
        }

        data = processOrder(data, chosenBatches)


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

                <Grid container mt={5}>

                    <Grid item xs={12} lg={6} mb={5}>
                        <Stack spacing={2}>
                            <Stack direction='row' spacing={2} justifyContent='center' alignItems='center'>
                                <FormTextfield
                                    sx={{ width: 500 }}
                                    isRequired
                                    type='number'
                                    disabled={action === 'edit' ? true : false}
                                    label='CC del Paciente'
                                    name='pacientId'
                                    register={register}
                                    errors={errors} />

                                {action === 'add' && <FabActionButton handleClick={findClient} icon={<SearchIcon />} color='info' />}
                            </Stack>
                            {pacient.firstname ? renderPacient() : <Typography>No fue encontrado el paciente</Typography>}

                        </Stack>
                    </Grid>


                    <Grid item xs={12} lg={6}>
                        <Stack spacing={2} width={500}>

                            <FormTextfield
                                isRequired
                                InputLabelProps={{
                                    shrink: true,
                                }}
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
                                        {t(`order.status.${status}`)}
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
                            <Box>

                                <FabSubmitButton color='info' />
                            </Box>
                        </Stack>
                    </Grid>
                </Grid>

            </form>
            <Box my={2} />
            {userRole !== '' && <SupplyTable userRole={userRole} addOrUpdateBatch={addOrUpdateBatch} removeBatch={removeBatch}></SupplyTable>}

            <DevTool control={control} />

        </>
    )
}

export default SupplyOrderForm