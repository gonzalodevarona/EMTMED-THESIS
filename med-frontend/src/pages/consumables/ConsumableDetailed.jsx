import Header from "../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties, capitalizeFirstLetter } from '../../utils/CommonMethods';
import ConsumableService from "../../services/consumableService";
import { useNavigate } from "react-router-dom";
import triggerConfrirmationAlert from "../../components/alerts/ConfirmationAlert";
import ConsumableDetailedView from "./ConsumableDetailedView";

function ConsumableDetailed() {

    let { id } = useParams();
    const [consumable, setConsumable] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    const entity = 'consumible';

    async function handleDelete() {
        triggerConfrirmationAlert({
            title: `Eliminar ${entity} con ID ${id}`,
            text: "¿Estas seguro que quieres eliminarlo?",
            type: "warning",
            confirmText: "Borrar",
            action: async () => await ConsumableService.deleteConsumable(id),
            successTitle: `${capitalizeFirstLetter(entity)} con ID ${id} eliminado con éxito.`,
            successType: "success",
            successAction: redirect(-1),
            errorTitle: `${capitalizeFirstLetter(entity)} con ID ${id} NO fue eliminado.`,
            errorType: "error"
        })
    }

    useEffect(() => {
        async function fetchData() {
            const entityData = await ConsumableService.getConsumableById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                
                entityData.countingUnit = `${entityData.countingUnit.id} - ${entityData.countingUnit.name}`
                entityData.weightUnit = `${entityData.weightUnit.id} - ${entityData.weightUnit.name}`
                processBatches(entityData.batches)
                console.log(entityData)
                setConsumable(removeNullProperties(entityData))
            }

        }

        fetchData()
    }, [])


    function processBatches(batches) {

        for (let i = 0; i < batches.length; i++) {
            batches[i] = removeNullProperties(batches[i])
        }
        return batches;
    }

    useEffect(() => {
      console.log(consumable)
    }, [consumable])
    

    return (
        <>
            <Header title={`${entity} ${id}`} />
            <ConsumableDetailedView
                editable
                deleteable
                entity='consumibles' data={consumable}
                handleDelete={handleDelete} />
        </>
    )
}

export default ConsumableDetailed