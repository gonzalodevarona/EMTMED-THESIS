import { useEffect, useState } from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Link } from 'react-router-dom';
import { Button, Typography, Stack } from '@mui/material';
import triggerCannotDeleteAlert from '../../components/alerts/CannotDeleteAlert';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import ConsumableService from '../../services/consumableService';

function Consumables() {

    const entity = 'consumible'

    const [consumables, setConsumables] = useState([])


    useEffect(() => {

        async function fetchData() {
            const allConsumables = await ConsumableService.getConsumablesNoOrdersNoBatches();

            setConsumables(processConsumables(allConsumables))
        }

        fetchData()

    }, [])

    async function handleDeleteConsumable(id) {
        const error = await ConsumableService.deleteConsumable(id);

        { error.status === 500 ? triggerCannotDeleteAlert(entity, id, `No se pudo eliminar el ${entity} con ID ${id}`) : null }
    }

    function processConsumables(consumables) {

        for (let i = 0; i < consumables.length; i++) {
            consumables[i].countingUnit = consumables[i].countingUnit.name;
            consumables[i].weightUnit = consumables[i].weightUnit.name;

        }
        return consumables;
    }



    return (
        <>
            <Header title={"Consumibles"} />
            <Button
                component={Link}
                to="/consumibles/agregar"
                variant="contained"
                sx={{ px: 10, py: 1, mb: 2 }}
                color={'info'}>
                Agregar {entity}

            </Button>

            <Typography >Nota: Para editar o eliminar algún {entity} se debe hacer por la vista detallada</Typography>
            <Typography >Cuidado: Si elimina algún {entity}, eliminará todos los lotes asociados a él</Typography>
            <CustomTable
                columns={[
                    { title: 'ID', field: 'id', type: 'numeric' },
                    { title: 'Nombre', field: 'name' },
                    { title: 'Cantidad', field: 'quantity', type: 'numeric' },
                    { title: 'Unidad de Conteo', field: 'countingUnit' },
                    { title: 'Peso', field: 'weight' },
                    { title: 'Unidad de Peso', field: 'weightUnit' },

                ]}
                singleEntity={entity}
                entity='consumibles'
                editable={false}
                deleteable={false}
                handleDelete={handleDeleteConsumable}
                data={consumables} />

        </>
    )
}

export default Consumables