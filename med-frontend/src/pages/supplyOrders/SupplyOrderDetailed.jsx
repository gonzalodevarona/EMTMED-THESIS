import Header from "../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties } from '../../utils/CommonMethods';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import SupplyOrderService from "../../services/supplyOrderService";
import MedicationBatchService from "../../services/medicationBatchService";
import BatchService from "../../services/batchService";
import { useNavigate } from "react-router-dom";
import SupplyOrderDetailedView from "./SupplyOrderDetailedView";


function SupplyOrderDetailed() {

    let { id } = useParams();
    const [supplyOrder, setSupplyOrder] = useState({})

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    const entity = 'orden de paciente';

    useEffect(() => {
        async function fetchData() {
            let entityData = await SupplyOrderService.getSupplyOrderById(id);

            if (entityData.status == 500 && entityData.error) {
                redirect('/404')
            } else {
                entityData = removeNullProperties(entityData)
                await proccessSupplyOrder(entityData)
                setSupplyOrder(entityData)
            }

        }

        fetchData()
    }, [])

    async function proccessSupplyOrder(entityData) {
        entityData.authoredOn = dateArrayToString(Object.values(entityData.authoredOn))

        if (entityData.medicationBatchRequests) {
            entityData.medicationBatchRequests = Object.values(entityData.medicationBatchRequests)

            entityData.medicationBatchRequests = await Promise.all(entityData.medicationBatchRequests.map(async (medicationBatchRequest) => {
                medicationBatchRequest.medicationBatch.supply = await MedicationBatchService.getMedicineByMedicationBatchId(medicationBatchRequest.medicationBatch.id);
                return medicationBatchRequest;
            }));

            if (entityData.medicationBatchRequests && entityData.medicationBatchRequests.length > 0) {
                entityData.medicationBatchRequests.forEach(function (medicationBatchRequest) {
                    medicationBatchRequest.medicationBatch.expirationDate = dateArrayToString(Object.values(medicationBatchRequest.medicationBatch.expirationDate))
                });
            }
        }

        if (entityData.batchRequests) {

            entityData.batchRequests = Object.values(entityData.batchRequests)

            entityData.batchRequests = await Promise.all(entityData.batchRequests.map(async (batchRequest) => {
                batchRequest.batch.supply = await BatchService.getConsumableByBatchId(batchRequest.batch.id);
                return batchRequest;
            }));


            if (entityData.batchRequests && entityData.batchRequests.length > 0) {
                entityData.batchRequests.forEach(function (batchRequest) {
                    batchRequest.batch.expirationDate = dateArrayToString(Object.values(batchRequest.batch.expirationDate))
                });
            }

        }

    }

    return (
        <>
            <Header title={`${entity} ${id}`} />
            <SupplyOrderDetailedView
                data={supplyOrder}
            />
        </>
    )
}

export default SupplyOrderDetailed