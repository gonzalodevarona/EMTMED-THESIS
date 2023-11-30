import Header from "../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties, previousPage, capitalizeFirstLetter } from '../../utils/CommonMethods';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import MedicineService from "../../services/medicineService";
import { useNavigate } from "react-router-dom";
import triggerConfrirmationAlert from "../../components/alerts/ConfirmationAlert";
import MedicineDetailedView from "./MedicineDetailedView";

function MedicineDetailed() {

    let { id } = useParams();
    const [medicine, setMedicine] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    const entity = 'medicamento';

    async function handleDelete() {
        triggerConfrirmationAlert({
            title: `Eliminar ${entity} con ID ${id}`,
            text: "¿Estas seguro que quieres eliminarlo?",
            type: "warning",
            confirmText: "Borrar",
            action: async () => await MedicineService.deleteMedicine(id),
            successTitle: `${capitalizeFirstLetter(entity)} con ID ${id} eliminado con éxito.`,
            successType: "success",
            successAction: previousPage,
            errorTitle: `${capitalizeFirstLetter(entity)} con ID ${id} NO fue eliminado.`,
            errorType: "error"
        })
    }

    useEffect(() => {
        async function fetchData() {
            const entityData = await MedicineService.getMedicineById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                
                entityData.countingUnit = `${entityData.countingUnit.id} - ${entityData.countingUnit.name}`
                entityData.weightUnit = `${entityData.weightUnit.id} - ${entityData.weightUnit.name}`
                processMedicineBatches(entityData.batches)
                console.log(entityData)
                setMedicine(removeNullProperties(entityData))
            }

        }

        fetchData()
    }, [])


    function processMedicineBatches(medicineBatches) {

        for (let i = 0; i < medicineBatches.length; i++) {
            medicineBatches[i] = removeNullProperties(medicineBatches[i])
        }
        return medicineBatches;
    }


    return (
        <>
            <Header title={`${entity} #${id}`} />
            <MedicineDetailedView
                editable
                deleteable
                entity='medicamentos' data={medicine}
                handleDelete={handleDelete} />
        </>
    )
}

export default MedicineDetailed