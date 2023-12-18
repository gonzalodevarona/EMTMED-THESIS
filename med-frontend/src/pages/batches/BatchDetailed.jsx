import DetailedView from "../../components/DetailedView"
import Header from "../../components/Header"
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { removeNullProperties } from '../../utils/CommonMethods';
import { useNavigate } from 'react-router-dom';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import BatchService from "../../services/batchService";

function BatchDetailed() {

  let { id } = useParams();
  const [batch, setBatch] = useState({})

  const navigate = useNavigate();

  const redirect = (path) => {
    navigate(path);
  };

  const entity = 'lote';


  useEffect(() => {
    async function fetchData() {
      const entityData = await BatchService.getBatchById(id);
      entityData.consumable = await BatchService.getConsumableByBatchId(id);
      if (entityData.status == 500 && entityData.error) {
        redirect('/404')
      } else {
        entityData.location = `${entityData.location.id} - ${entityData.location.name}`
        entityData.expirationDate = dateArrayToString(entityData.expirationDate);

        let consumable = entityData.consumable;
        entityData['consumable-countBatches'] = consumable.batches.length;
        // entityData['consumable-countOrders'] = consumable.orders.length;

        // Añadir cada propiedad de medicine al objeto principal con el prefijo 'consumable-'
        for (let key in consumable) {
          entityData['consumable-' + key] = consumable[key];
        }

        entityData['consumable-countingUnit'] = `${entityData['consumable-countingUnit'].id} - ${entityData['consumable-countingUnit'].name}`
        entityData['consumable-weightUnit'] = `${entityData['consumable-weightUnit'].id} - ${entityData['consumable-weightUnit'].name}`


        delete entityData.consumable;
        delete entityData['consumable-batches'];
        delete entityData.consumable;

        setBatch(removeNullProperties(entityData))
      }

    }

    fetchData()
  }, [])


  return (
    <>
      <Header title={`${entity} ${id}`} />
      <DetailedView
        deleteable={false}
        editable={false}
        entity='lotes' data={batch}
      />
    </>
  )
}

export default BatchDetailed