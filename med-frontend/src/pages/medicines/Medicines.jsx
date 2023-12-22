import { useEffect, useState } from 'react'
import CustomTable from '../../components/tables/CustomTable'
import Header from '../../components/Header'
import FabLink from '../../components/buttons/FabLink'
import AddIcon from '@mui/icons-material/Add';
import { Button, Typography, Stack } from '@mui/material';
import MedicineService from '../../services/medicineService';
import triggerCannotDeleteAlert from '../../components/alerts/CannotDeleteAlert';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';

function Medicines() {

    const entity = 'medicamento'

    const [medicines, setMedicines] = useState([])


    useEffect(() => {

        async function fetchData() {
            const allMedicines = await MedicineService.getMedicinesNoBatches();

            setMedicines(processMedicines(allMedicines))
        }

        fetchData()

    }, [])

    async function handleDeleteMedicine(id) {
        const error = await MedicineService.deleteMedicine(id);

        { error.status === 500 ? triggerCannotDeleteAlert(entity, id, `No se pudo eliminar el ${entity} con ID ${id}`) : null }
    }

    function processMedicines(medicines) {

        for (let i = 0; i < medicines.length; i++) {

            
            medicines[i].countingUnit = medicines[i].countingUnit.name;
            medicines[i].weightUnit = medicines[i].weightUnit.name;
            delete medicines[i].batches
            delete medicines[i].orders

        }
        return medicines;
    }



    return (
        <>
            <Header title={"Medicamentos"} />

            <FabLink to="/medicamentos/agregar" icon={<AddIcon/>} color='secondary'/ >


            <Typography >Nota: Para editar o eliminar algún {entity} se debe hacer por la vista detallada.</Typography>
            <Typography >Cuidado: Si elimina algún {entity}, eliminará todos los lotes asociados a él.</Typography>
            <CustomTable
                columns={[
                    { title: 'ID', field: 'id', type: 'numeric' },
                    { title: 'Nombre', field: 'name' },
                    { title: 'Compuesto Activo', field: 'activePharmaceuticalIngredient' },
                    { title: 'Concentración', field: 'concentration' },
                    { title: 'Cantidad', field: 'quantity', type: 'numeric' },
                    { title: 'Unidad de Conteo', field: 'countingUnit' },
                    { title: 'Peso', field: 'weight' },
                    { title: 'Unidad de Peso', field: 'weightUnit' },

                ]}
                clickable
                singleEntity={entity}
                entity='medicamentos'
                editable={false}
                deleteable={false}
                handleDelete={handleDeleteMedicine}
                data={medicines} />

        </>
    )
}

export default Medicines