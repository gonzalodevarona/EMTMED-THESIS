import { Box, Grid, Stack, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { capitalizeFirstLetter } from '../../utils/CommonMethods';
import CheckIcon from '@mui/icons-material/Check';
import CloseIcon from '@mui/icons-material/Close';
import CustomTable from '../../components/tables/CustomTable';
import FabActionButton from '../../components/buttons/FabActionButton';
import InventoryOrderService from '../../services/inventoryOrderService'
import { dateArrayToString } from '../../utils/EntityProcessingMethods'
import triggerConfirmationAlert from '../../components/alerts/ConfirmationAlert';
import { useTranslation } from 'react-i18next';

function InventoryOrderDetailedView({ data, entity }) {

    const { t } = useTranslation();
    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    const [firstHalf, setFirstHalf] = useState([]);
    const [secondHalf, setSecondHalf] = useState([]);
    const [batches, setBatches] = useState([]);
    const [medicationBatches, setMedicationBatches] = useState([]);

    useEffect(() => {

        function splitArray() {
            delete data.orders
            delete data.batches
            delete data.medicationBatches
            if (data && Object.keys(data).length > 0 && Object.keys(data).length > 3) {
                const half = Object.keys(data).length / 2;

                const tempFirstHalf = [];
                const tempSecondHalf = [];

                Object.entries(data).forEach(([key, value], index) => {
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

        if (data.id !== undefined) {
            let translatedObject = {};

            for (let key in data) {
                if (typeof data[key] !== 'object' && !Array.isArray(data[key])) {
                    let translatedKey = t(`inventoryOrder.info.${key}`);
                    if (key === 'status') {
                        translatedObject[translatedKey] = t(`order.status.${data[key]}`);
                    } else if (key === 'operation') {
                        translatedObject[translatedKey] = t(`inventoryOrder.operation.${data[key]}`);
                    }
                    else {
                        translatedObject[translatedKey] = data[key];
                    }
                } else {
                    translatedObject[key] = data[key];
                }
            }
            data = translatedObject;
        }

        castBatchestoBatchesArray(data.batches, data.medicationBatches)
        splitArray()

    }, [data]);

    async function changeInventoryOrderStatus(id, status) {
        const foundInventoryOrder = await InventoryOrderService.getInventoryOrderById(id)
        foundInventoryOrder.authoredOn = dateArrayToString(foundInventoryOrder.authoredOn)
        triggerConfirmationAlert({
            title: `${status === 'COMPLETED' ? 'Completar' : 'Cancelar'} la ${entity} con ID ${id}`,
            text: `¿Estas seguro que quieres ${status === 'COMPLETED' ? 'completarla' : 'cancelarla'}?`,
            type: "confirm",
            confirmButtonColor: "#04b44c",
            confirmText: "Confirmar",
            action: async () => await InventoryOrderService.changeInventoryOrderStatus(id, status),
            successTitle: `La ${entity} con ID ${id} fue ${status === 'COMPLETED' ? 'completada' : 'cancelada'} con éxito.`,
            successType: "success",
            successAction: () => redirect(-1),
            errorTitle: `El estado de la ${capitalizeFirstLetter(entity)} con ID ${id} NO pudo ser modificado.`,
            errorType: "error"
        })


    }

    useEffect(() => {
        console.log(batches)
    }, [batches])

    function castBatchestoBatchesArray(batches, medicationBatches) {
        let batchesArray = [];
        if (batches) {
            Object.entries(batches).forEach(([_, value]) => {
                value.location = value.location.name
                value.expirationDate = dateArrayToString(value.expirationDate)
                batchesArray.push(value);
            });
            setBatches(batchesArray);
        }
        if (medicationBatches) {
            batchesArray = [];
            Object.entries(medicationBatches).forEach(([_, value]) => {
                value.location = value.location.name
                value.expirationDate = dateArrayToString(value.expirationDate)
                batchesArray.push(value);
            });
            setMedicationBatches(batchesArray);
        }

    }

    return (
        <>
            {data && Object.keys(data).length > 3 ? (
                <Grid container>
                    <Grid item xs={6}>
                        {firstHalf.map((item, index) => (
                            Object.entries(item).map(([key, value]) => (
                                <Typography m={3} key={key}>
                                    <span style={{ fontWeight: 'bold' }}>{capitalizeFirstLetter(key)}:</span> {value}
                                </Typography>

                            ))
                        ))}
                    </Grid>
                    <Grid item xs={6}>
                        {secondHalf.map((item, index) => (
                            Object.entries(item).map(([key, value]) => (
                                <Typography m={3} key={key}>
                                    <span style={{ fontWeight: 'bold' }}>{capitalizeFirstLetter(key)}:</span> {value}
                                </Typography>
                            ))
                        ))}
                    </Grid>
                </Grid>

            ) : (
                <Stack>
                    {Object.entries(data).map(([key, value]) => (
                        <Typography m={3} key={key}>
                            <span style={{ fontWeight: 'bold' }}>{capitalizeFirstLetter(key)}:</span> {value}
                        </Typography>
                    ))}
                </Stack>
            )}


            {medicationBatches.length > 0 &&
                <CustomTable
                    title='Lotes de Medicamentos Asociados'
                    columns={[
                        { title: 'ID', field: 'medicine.id', type: 'numeric' },
                        { title: 'Nombre', field: 'medicine.name' },
                        { title: 'Unidad de Conteo', field: 'medicine.countingUnit.name' },
                        { title: 'ID Lote', field: 'id', type: 'numeric' },
                        { title: 'Fabricante', field: 'manufacturer' },
                        { title: 'Vía de Administración', field: 'administrationRoute' },
                        { title: 'Cantidad del Lote', field: 'quantity', type: 'numeric' },
                        { title: 'Fecha de Vencimiento', field: 'expirationDate' },
                        { title: 'Semaforización', field: 'status', render: rowData => t(`batch.status.${rowData.status}`) },
                        { title: 'Ubicación Actual', field: 'location' },
                        { title: 'CUM', field: 'cum' }

                    ]}
                    clickable
                    entity='lotes-medicamentos'
                    editable={false}
                    deleteable={false}
                    data={medicationBatches} />
            }

            {(medicationBatches.length > 0 && batches.length > 0) &&
                <Box my={10} />
            }


            {batches.length > 0 &&
                <CustomTable
                    title='Lotes Asociados'
                    columns={[
                        { title: 'ID', field: 'consumable.id', type: 'numeric' },
                        { title: 'Nombre', field: 'consumable.name' },
                        { title: 'ID Lote', field: 'id', type: 'numeric' },
                        { title: 'Fabricante', field: 'manufacturer' },
                        { title: 'Vía de Administración', field: 'administrationRoute' },
                        { title: 'Cantidad del Lote', field: 'quantity', type: 'numeric' },
                        { title: 'Unidad de Conteo', field: 'consumable.countingUnit.name' },
                        { title: 'Fecha de Vencimiento', field: 'expirationDate' },
                        { title: 'Semaforización', field: 'status', render: rowData => t(`batch.status.${rowData.status}`) },
                        { title: 'Ubicación Actual', field: 'location' }

                    ]}
                    clickable
                    entity='lotes'
                    editable={false}
                    deleteable={false}
                    data={batches} />
            }

            {(data.status !== 'COMPLETED') && (data.status !== 'CANCELLED') &&
                <Stack direction='row' spacing={4} justifyContent='center' alignItems='center' mt={2}>
                    <FabActionButton color='secondary' icon={<CheckIcon />} handleClick={() => changeInventoryOrderStatus(data.id, 'COMPLETED')} />
                    <FabActionButton color='error' icon={<CloseIcon />} handleClick={() => changeInventoryOrderStatus(data.id, 'CANCELLED')} />

                </Stack>

            }
        </>
    );
}

export default InventoryOrderDetailedView;