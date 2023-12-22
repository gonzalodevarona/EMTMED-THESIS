import { useEffect, useState } from 'react'
import CustomTable from '../../components/tables/CustomTable'
import Header from '../../components/Header'
import FabLink from '../../components/buttons/FabLink'
import { Typography } from '@mui/material';
import triggerCannotDeleteAlert from '../../components/alerts/CannotDeleteAlert';
import AddIcon from '@mui/icons-material/Add';
import ConsumableService from '../../services/consumableService';

function Consumables() {

    const entity = 'consumible'

    const [consumables, setConsumables] = useState([])


    useEffect(() => {

        async function fetchData() {
            const allConsumables = await ConsumableService.getConsumablesNoBatches();

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
           
            <FabLink to="/consumibles/agregar" icon={<AddIcon/>} color='secondary'/ >

            <Typography >Nota: Para editar o eliminar algún {entity} se debe hacer por la vista detallada.</Typography>
            <Typography >Cuidado: Si elimina algún {entity}, eliminará todos los lotes asociados a él.</Typography>
            <CustomTable
                columns={[
                    { title: 'ID', field: 'id', type: 'numeric' },
                    { title: 'Nombre', field: 'name' },
                    { title: 'Cantidad', field: 'quantity', type: 'numeric' },
                    { title: 'Unidad de Conteo', field: 'countingUnit' },
                    { title: 'Peso', field: 'weight' },
                    { title: 'Unidad de Peso', field: 'weightUnit' },

                ]}
                clickable
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