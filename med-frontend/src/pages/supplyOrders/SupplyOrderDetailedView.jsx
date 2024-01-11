import { Button, Grid, Stack, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { capitalizeFirstLetter, refreshPage } from '../../utils/CommonMethods';
import { dateArrayToString, formatNoteForEmr } from '../../utils/EntityProcessingMethods';
import CustomTable from '../../components/tables/CustomTable';
import FabActionButton from '../../components/buttons/FabActionButton';
import SupplyOrderService from '../../services/supplyOrderService';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import ClinicalHistoryService from '../../services/clinicalHistoryService';
import triggerConfirmationAlert from "../../components/alerts/ConfirmationAlert";


function SupplyOrderDetailedView({ data }) {
    const [firstHalf, setFirstHalf] = useState([]);
    const [secondHalf, setSecondHalf] = useState([]);

    const entity = 'orden de paciente';

    useEffect(() => {

        function splitArray() {

            const { medicationBatchRequests, batchRequests, ...restData } = data;

            if (restData && Object.keys(restData).length > 0 && Object.keys(restData).length > 3) {
                const half = Object.keys(restData).length / 2;

                const tempFirstHalf = [];
                const tempSecondHalf = [];

                Object.entries(restData).forEach(([key, value], index) => {
                    if (index < half) {
                        tempFirstHalf.push({ [key]: value });
                    } else {
                        tempSecondHalf.push({ [key]: value });
                    }
                });

                setFirstHalf(tempFirstHalf);
                setSecondHalf(tempSecondHalf);
            }
        }


        splitArray()


    }, [data]);

    async function changeSupplyOrderStatusAndSaveToEmr(id, status) {

        const foundSupplyOrder = await SupplyOrderService.getSupplyOrderById(id)
        foundSupplyOrder.authoredOn = dateArrayToString(foundSupplyOrder.authoredOn)
        await SupplyOrderService.changeSupplyOrderStatus(id, status)
        //await ClinicalHistoryService.addNoteToEMR(formatNoteForEmr(foundSupplyOrder))
    }


    async function changeSupplyOrderStatus(id, status) {

        triggerConfirmationAlert({
            title: `${status === 'COMPLETED' ? 'Completar' : 'Cancelar'} la ${entity} con ID ${id}`,
            text: `¿Estas seguro que quieres ${status === 'COMPLETED' ? 'completarla' : 'cancelarla'}?`,
            type: "confirm",
            confirmButtonColor: "#04b44c",
            confirmText: "Confirmar",
            action: async () => await changeSupplyOrderStatusAndSaveToEmr(id, status),
            successTitle: `La ${entity} con ID ${id} fue ${status === 'COMPLETED' ? 'completada' : 'cancelada'} con éxito.`,
            successType: "success",
            successAction: refreshPage,
            errorTitle: `El estado de la ${capitalizeFirstLetter(entity)} con ID ${id} NO pudo ser modificado.`,
            errorType: "error"
        })

    }





    return (
        <>
            {data && Object.keys(data).length > 3 ? (
                <Grid container>
                    <Grid item xs={6}>
                        {firstHalf.map((item, index) => (
                            Object.entries(item).map(([key, value]) => (
                                <Stack m={3} key={key}>
                                    <Typography fontWeight='bold' variant='subtitle1'>{capitalizeFirstLetter(key)}</Typography>
                                    <Typography>{value}</Typography>
                                </Stack>
                            ))
                        ))}
                    </Grid>
                    <Grid item xs={6}>
                        {secondHalf.map((item, index) => (
                            Object.entries(item).map(([key, value]) => (
                                <Stack m={3} key={key}>
                                    <Typography fontWeight='bold' variant='subtitle1'>{capitalizeFirstLetter(key)}</Typography>
                                    <Typography>{value}</Typography>
                                </Stack>
                            ))
                        ))}
                    </Grid>
                </Grid>

            ) : (
                <Stack>
                    {Object.entries(data).map(([key, value]) => (
                        <Stack m={3} key={key}>
                            <Typography fontWeight='bold' variant='subtitle1'>{capitalizeFirstLetter(key)}</Typography>
                            <Typography>{value}</Typography>
                        </Stack>
                    ))}
                </Stack>
            )}




            {data.medicationBatchRequests && data.medicationBatchRequests.length > 0 && <CustomTable
                title='Medicamentos Solicitados'
                columns={[

                    { title: 'ID', field: 'medicationBatch.supply.id' },
                    { title: 'Nombre', field: 'medicationBatch.supply.name' },
                    { title: 'Concentración', field: 'medicationBatch.supply.concentration' },
                    { title: 'ID Lote', field: 'medicationBatch.id' },
                    { title: 'Vía de Administración', field: 'medicationBatch.administrationRoute' },
                    { title: 'Ubicación', field: 'medicationBatch.location.name' },
                    { title: 'Cantidad Solicitada', field: 'quantity', type: 'numeric' },
                    { title: 'Unidad de Conteo', field: 'medicationBatch.supply.countingUnit.name' },
                    { title: 'Fecha de Vencimiento', field: 'medicationBatch.expirationDate' },
                    { title: 'Estado', field: 'medicationBatch.status' },
                    { title: 'Fabricante', field: 'medicationBatch.manufacturer' },
                    { title: 'CUM', field: 'medicationBatch.cum' },

                ]}

                editable={false}
                deleteable={false}
                data={data.medicationBatchRequests}
            />
            }
            {data.batchRequests && data.batchRequests.length > 0 &&
                <CustomTable
                    title='Insumos Solicitados'
                    columns={[

                        { title: 'ID', field: 'batch.supply.id' },
                        { title: 'Nombre', field: 'batch.supply.name' },
                        { title: 'ID Lote', field: 'batch.id' },
                        { title: 'Vía de Administración', field: 'batch.administrationRoute' },
                        { title: 'Ubicación', field: 'batch.location.name' },
                        { title: 'Cantidad Solicitada', field: 'quantity', type: 'numeric' },
                        { title: 'Unidad de Conteo', field: 'batch.supply.countingUnit.name' },
                        { title: 'Fecha de Vencimiento', field: 'batch.expirationDate' },
                        { title: 'Estado', field: 'batch.status' },
                        { title: 'Fabricante', field: 'batch.manufacturer' },


                    ]}

                    editable={false}
                    deleteable={false}
                    data={data.batchRequests}
                />

            }

            {(data.status !== 'COMPLETED') && (data.status !== 'CANCELLED') &&
                <Stack direction='row' spacing={4} justifyContent='center' alignItems='center' mt={2}>
                    <FabActionButton color='secondary' icon={<CheckIcon />} handleClick={() => changeSupplyOrderStatus(data.id, 'COMPLETED')} />
                    <FabActionButton color='error' icon={<CloseIcon />} handleClick={() => changeSupplyOrderStatus(data.id, 'CANCELLED')} />

                </Stack>

            }

        </>
    );
}

export default SupplyOrderDetailedView;