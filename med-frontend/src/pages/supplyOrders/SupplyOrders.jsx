import { useEffect, useState } from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Link } from 'react-router-dom';
import { Button, Typography, Stack } from '@mui/material';
import SupplyOrderService from '../../services/supplyOrderService';
import triggerCannotDeleteAlert from '../../components/alerts/CannotDeleteAlert';
import { dateArrayToString } from '../../utils/EntityProcessingMethods';
import { useKeycloak } from '@react-keycloak/web'

function SupplyOrders() {

  const entity = 'orden'

  const { keycloak } = useKeycloak()

  const [supplyOrders, setSupplyOrders] = useState([])


  useEffect(() => {

    async function fetchData() {
      const allSupplyOrders = await SupplyOrderService.getSupplyOrders();

      setSupplyOrders(processSupplyOrders(allSupplyOrders))
    }

    fetchData()

  }, [])

  async function handleDeleteSupplyOrder(id) {
    const error = await SupplyOrderService.deleteSupplyOrder(id);

    { error.status === 500 ? triggerCannotDeleteAlert(entity, id, `No se pudo eliminar la ${entity} con ID ${id}`) : null }
  }

  function processSupplyOrders(orders) {

    for (let i = 0; i < orders.length; i++) {

      orders[i].authoredOn = dateArrayToString(orders[i].authoredOn);
      
    }
    return orders;
  }


  return (
    <>
      <Header title={"Ordenes de Pacientes"} />

      <Typography >Nota: Las ordenes de pacientes no se pueden borrar, solo cambiar de estado y se hace desde la vista detallada</Typography>
      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Fecha de expedición', field: 'authoredOn', type: 'datetime' },
          { title: 'CC Responsable', field: 'practitionerId', type: 'numeric' },
          { title: 'CC Paciente', field: 'pacientId' },
          { title: 'Estado', field: 'status' },
          { title: 'Diagnostico', field: 'diagnostic' }

        ]}
        singleEntity={entity}
        entity='ordenes'
        editable={false}
        deleteable={false}
        handleDelete={handleDeleteSupplyOrder}
        data={supplyOrders} />

    </>
  )
}

export default SupplyOrders