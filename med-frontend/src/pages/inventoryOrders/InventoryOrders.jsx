import { useEffect, useState } from 'react'
import CustomTable from '../../components/tables/CustomTable'
import Header from '../../components/Header'
import AddIcon from '@mui/icons-material/Add';
import { Typography } from '@mui/material';
import InventoryOrderService from '../../services/inventoryOrderService';
import MedicationBatchService from '../../services/medicationBatchService';
import BatchService from '../../services/batchService';
import triggerCannotDeleteAlert from '../../components/alerts/CannotDeleteAlert';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import FabLink from '../../components/buttons/FabLink';

function InventoryOrders() {

  const entity = 'orden de inventario'

  const [inventoryOrders, setInventoryOrders] = useState([])


  useEffect(() => {

    async function fetchData() {
      const allInventoryOrders = await InventoryOrderService.getInventoryOrders();
  
      setInventoryOrders(processInventoryOrders(allInventoryOrders))
    }

    fetchData()

  }, [])
  
  async function handleDeleteInventoryOrder(id) {
    const error = await InventoryOrderService.deleteInventoryOrder(id);

    { error.status === 500 ? triggerCannotDeleteAlert(entity, id, `No se pudo eliminar la ${entity} con ID ${id}`) : null }
  }

  function processInventoryOrders(orders) {

    for (let i = 0; i < orders.length; i++) {

      orders[i].destination = `${orders[i].destination.id} - ${orders[i].destination.name}`;
      orders[i].source = `${orders[i].source.id} - ${orders[i].source.name}`;
      orders[i].authoredOn = dateArrayToString(orders[i].authoredOn);

    }
    return orders;
  }


  return (
    <>
      <Header title={"Ordenes de Inventario"} />

      <FabLink to="/ordenes-inventario/agregar" icon={<AddIcon />} color='secondary' />

      <Typography >Nota: Para editar o eliminar alguna {entity} se debe hacer por la vista detallada.</Typography>
      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Fecha de expedición', field: 'authoredOn', type: 'datetime' },
          { title: 'CC Responsable', field: 'practitionerId', type: 'numeric' },
          { title: 'Estado', field: 'status' },
          { title: 'Operación', field: 'operation' },
          { title: 'Origen', field: 'source' },
          { title: 'Destino', field: 'destination' },


        ]}
        clickable
        singleEntity={entity}
        entity='ordenes-inventario'
        editable={false}
        deleteable={false}
        handleDelete={handleDeleteInventoryOrder}
        data={inventoryOrders} />

    </>
  )
}

export default InventoryOrders