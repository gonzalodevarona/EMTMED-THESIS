import { Grid, Stack, Typography } from '@mui/material';
import React, { useEffect, useState } from 'react';
import { capitalizeFirstLetter } from '../../utils/CommonMethods';
import { Delete, Edit } from '@mui/icons-material';
import CustomTable from '../../components/CustomTable';
import FabLink from '../../components/buttons/FabLink';
import FabActionButton from '../../components/buttons/FabActionButton';
import { dateArrayToString } from '../../utils/EntityProcessingMethods'


function ConsumableDetailedView({ data, entity, handleDelete, deleteable, editable }) {
    const [firstHalf, setFirstHalf] = useState([]);
    const [secondHalf, setSecondHalf] = useState([]);
    const [batches, setBatches] = useState([]);

    useEffect(() => {

        function splitArray() {
            delete data.orders
            delete data.batches
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

        castBatchestoBatchesArray(data.batches)
        splitArray()

    }, [data]);

    useEffect(() => {
        console.log(batches)
    }, [batches])

    function castBatchestoBatchesArray(batches) {
        let batchesArray = [];
        if (batches) {
            Object.entries(batches).forEach(([_, value]) => {
                console.log(value)
                value.location = value.location.name
                value.expirationDate = dateArrayToString(value.expirationDate)
                batchesArray.push(value);
            });
            setBatches(batchesArray);
        }

    }


    async function handleDeleteMedicine(id) {
        const error = await MedicineService.deleteMedicine(id);

        { error.status === 500 ? triggerCannotDeleteAlert(entity, id, `No se pudo eliminar el ${entity} con ID ${id}`) : null }
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



            <CustomTable
                title='Lotes Asociados'
                columns={[
                    { title: 'ID', field: 'id', type: 'numeric' },
                    { title: 'Fabricante', field: 'manufacturer' },
                    { title: 'Vía de Administración', field: 'administrationRoute' },
                    { title: 'Cantidad del Lote', field: 'quantity', type: 'numeric' },
                    { title: 'Fecha de Vencimiento', field: 'expirationDate' },
                    { title: 'Estado', field: 'status' },
                    { title: 'Ubicación', field: 'location' },


                ]}
                clickable
                entity='lotes'
                editable={false}
                deleteable={false}
                data={batches} />

            <Stack justifyContent='center' direction='row' spacing={5} mt={2}>
                {editable && <FabLink to={`/${entity}/editar/${data.id}`} icon={<Edit />} color='info' />}
                {deleteable && <FabActionButton color='error' icon={<Delete />} handleClick={handleDelete} />}
            </Stack>

        </>
    );
}

export default ConsumableDetailedView;


