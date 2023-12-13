import DetailedView from "../../components/DetailedView"
import Header from "../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties, capitalizeFirstLetter } from '../../utils/CommonMethods';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import MedicationBatchService from "../../services/medicationBatchService";
import { useNavigate } from "react-router-dom";

function MedicationBatchDetailed() {

  let { id } = useParams();
  const [medicationBatch, setMedicationBatch] = useState({})

  const navigate = useNavigate();

  const redirect = (path) => {
    navigate(path);
  };

  const entity = 'lote de medicamento';


  useEffect(() => {
    async function fetchData() {
      const entityData = await MedicationBatchService.getMedicationBatchById(id);
      entityData.medicine = await MedicationBatchService.getMedicineByMedicationBatchId(id);
      if (entityData.status == 500 && entityData.error) {
        redirect('/404')
      } else {
        entityData.location = `${entityData.location.id} - ${entityData.location.name}`
        entityData.expirationDate = dateArrayToString(entityData.expirationDate);

        let medicine = entityData.medicine;
        entityData['medicine-countBatches'] = medicine.batches.length;
        // entityData['medicine-countOrders'] = medicine.orders.length;

        // Añadir cada propiedad de medicine al objeto principal con el prefijo 'medicine-'
        for (let key in medicine) {
          entityData['medicine-' + key] = medicine[key];
        }

        entityData['medicine-countingUnit'] = `${entityData['medicine-countingUnit'].id} - ${entityData['medicine-countingUnit'].name}`
        entityData['medicine-weightUnit'] = `${entityData['medicine-weightUnit'].id} - ${entityData['medicine-weightUnit'].name}`

        if(entityData['medicine-orders'] !== null){
          entityData['medicine-countOrders'] = entityData['medicine-orders'].length;
        }

        delete entityData.medicine;
        delete entityData['medicine-orders'];
        delete entityData['medicine-batches'];
        delete entityData.medicine;

        setMedicationBatch(removeNullProperties(entityData))
      }

    }

    fetchData()
  }, [])


  return (
    <>
      <Header title={`${entity} #${id}`} />
      <DetailedView
        deleteable={false}
        editable={false}
        entity='lotes-medicamentos' data={medicationBatch}
      />
    </>
  )
}

export default MedicationBatchDetailed