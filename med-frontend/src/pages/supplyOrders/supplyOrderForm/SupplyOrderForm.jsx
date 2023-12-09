import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Button, Stack, MenuItem, Grid, Box } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import FormDateTimePicker from '../../../components/form/FormDateTimePicker';
import SupplyOrderService from "../../../services/supplyOrderService";
import OrderService from "../../../services/orderService";
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import { refreshPage } from "../../../utils/CommonMethods";
import { convertToLocalTimeZone, convertDateObjectToDayjs } from "../../../utils/EntityProcessingMethods";
import dayjs from 'dayjs';
import SupplyTable from "./SupplyTable";

function SupplyOrderForm({ action, preloadedData, id }) {

    const [pacient, setPacient] = useState({})
    const [statuses, setStatuses] = useState([])
    const [chosenBatches, setChosenBatches] = useState([])

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
        async function fetchStatuses() {
            let data = await OrderService.getOrderStatuses();
            setStatuses(data);
        }

        fetchStatuses()
    }, [])


    async function addSupplyOrder(supplyOrder) {
        return await SupplyOrderService.addSupplyOrder(supplyOrder);
    }

    async function editSupplyOrder(supplyOrder) {
        supplyOrder.id = id;
        return await SupplyOrderService.editSupplyOrder(supplyOrder);
    }

    // useEffect(() => {
    //     console.log(chosenBatches)
    // }, [chosenBatches])



    const {
        handleSubmit,
        register,
        formState: { errors },
        control,
    } = useForm({
        defaultValues: preloadedData
    });

    function addedSuccessfully() {
        triggerInfoAlert('success', 'La nueva orden ha sido agregada', refreshPage)
    }
    function errorAdding() {
        triggerInfoAlert('error', 'Ha habido un error agregando la nueva orden')
    }

    function editedSuccessfully() {
        triggerInfoAlert('success', 'La orden ha sido editada', refreshPage)
    }
    function errorEditing() {
        triggerInfoAlert('error', 'Ha habido un error editando la orden')
    }

    async function onSubmit(data) {

        if (chosenBatches.length == 0) {
            triggerInfoAlert('error', 'La orden debe de tener al menos 1 medicamento o consumible asociado')
            return;
        }
        // if (pacient.exists !== true) {
        //     triggerInfoAlert('error', 'El paciente no existe o no fue encontrado')
        //     return;
        // }

        data = processOrder(data, chosenBatches)

        console.log(data)

        switch (action) {
            case 'add':
                addSupplyOrder(data)
                // .then((result) => {
                //     console.log(result)
                //     addedSuccessfully()
                // })
                // .catch((error) => {
                //     errorAdding()
                //     console.error('Error en la operación:', error);
                // });
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
                console.log(data.authoredOn)
                data.authoredOn = convertToLocalTimeZone(data.authoredOn.toISOString());

                break;
            case 'edit':
                data.authoredOn = convertDateObjectToDayjs(data.authoredOn).toISOString();
                break;

            default:
                break;
        }


        batches.forEach(function (batch) {
            batch.supply.purpose = 'ORDER';
            batch.supply.quantity = batch.assignedQuantity;
            if (batch.supply.id) {
                delete batch.supply.id;
            }
        });

      

        data.supplies = batches.map(batch => {
            let supply = { ...batch.supply };
            const { expirationDate, ...batchWithoutExpiration } = batch; // Esto excluye 'expirationDate' de 'batch'
            supply.batches = [batchWithoutExpiration];
            return supply;
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

                            {action === 'add' && <Button type="submit" variant="contained" color="info">
                                Buscar paciente
                            </Button>}
                        </Stack>
                    </Grid>


                    <Grid item xs={12} lg={6}>
                        <Stack spacing={2} width={500}>

                            <FormTextfield
                                isRequired
                                type='number'
                                disabled={action === 'edit' ? true : false}
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