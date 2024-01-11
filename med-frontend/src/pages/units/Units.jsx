import { useEffect, useState, useTransition } from 'react'
import CustomTable from '../../components/tables/CustomTable'
import Header from '../../components/Header'
import { Box } from '@mui/material'
import AddIcon from '@mui/icons-material/Add';
import FabLink from '../../components/buttons/FabLink';
import triggerCannotDeleteAlert from '../../components/alerts/CannotDeleteAlert';
import CountingUnitService from '../../services/countingUnitService';

function Units() {

  const [countingUnits, setCountingUnits] = useState([])

  useEffect(() => {

    async function fetchData() {
      const allCountingUnits = await CountingUnitService.getCountingUnits();
      setCountingUnits(allCountingUnits)

    }

    fetchData()

  }, [])


  async function handleDeleteCountingUnit(id) {
    const error = await CountingUnitService.deleteCountingUnit(id)
    { error.status === 500 ? triggerCannotDeleteAlert('unidad de conteo', id, 'No se puede eliminar esta unidad de conteo, puesto que está ligada a algún lote') : null }
  }


  return (
    <>
      <Header title={"Unidad de Conteo"} />

      <FabLink to="/unidades-conteo/agregar" icon={<AddIcon />} color='secondary' />


      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' }
        ]}
        singleEntity='unidad de conteo'
        entity='unidades-conteo'
        handleDelete={handleDeleteCountingUnit}
        data={countingUnits} />

      <Box my={5}></Box>
    </>
  )
}

export default Units