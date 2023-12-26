import InventoryOrderDetailedView from "./InventoryOrderDetailedView"
import Header from "../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties, capitalizeFirstLetter } from '../../utils/CommonMethods';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import InventoryOrderService from "../../services/inventoryOrderService";
import MedicationBatchService from "../../services/medicationBatchService";
import BatchService from "../../services/batchService";
import { useNavigate } from "react-router-dom";
import triggerConfirmationAlert from "../../components/alerts/ConfirmationAlert";

function InventoryOrderDetailed() {

    let { id } = useParams();
    const [inventoryOrder, setInventoryOrder] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    const entity = 'orden de inventario';

    async function handleDelete() {
        triggerConfirmationAlert({
            title: `Eliminar ${entity} con ID ${id}`,
            text: "¿Estas seguro que quieres eliminarla?",
            type: "warning",
            confirmText: "Borrar",
            action: async () => await InventoryOrderService.deleteInventoryOrder(id),
            successTitle: `${capitalizeFirstLetter(entity)} con ID ${id} eliminada con éxito.`,
            successType: "success",
            successAction: redirect(-1),
            errorTitle: `${capitalizeFirstLetter(entity)} con ID ${id} NO fue eliminada.`,
            errorType: "error"
        })
    }

    useEffect(() => {
        async function fetchData() {
            const entityData = await InventoryOrderService.getInventoryOrderById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {


                entityData.medicationBatches = Object.values(entityData.medicationBatches);
                entityData.batches = Object.values(entityData.batches);
                if (entityData.medicationBatches && entityData.medicationBatches.length > 0) {
                    entityData.medicationBatches = await Promise.all(entityData.medicationBatches.map(async (medicationBatch, _) => {
                        medicationBatch['medicine'] = await MedicationBatchService.getMedicineByMedicationBatchId(medicationBatch.id);
                        medicationBatch['location'] = await MedicationBatchService.getLocationByMedicationBatchId(medicationBatch.id);
                        return medicationBatch;
                    }));
                }

                if (entityData.batches && entityData.batches.length > 0) {
                    entityData.batches = await Promise.all(entityData.batches.map(async (batch, _) => {
                        batch['consumable'] = await BatchService.getConsumableByBatchId(batch.id);
                        batch['location'] = await BatchService.getLocationByBatchId(batch.id);
                        return batch;
                    }));
                }


                
                entityData.destination = `${entityData.destination.id} - ${entityData.destination.name}`
                entityData.authoredOn = dateArrayToString(entityData.authoredOn);
                setInventoryOrder(removeNullProperties(entityData))
            }

        }

        fetchData()
    }, [])

    useEffect(() => {
        console.log(inventoryOrder)
    }, [inventoryOrder])



    return (
        <>
            <Header title={`${entity} ${id}`} />
            <InventoryOrderDetailedView 
            deleteable={false} 
            editable={inventoryOrder.status === 'COMPLETED'? false : true} 
            entity={entity} data={inventoryOrder} 
            handleDelete={handleDelete} />
        </>
    )
}

export default InventoryOrderDetailed