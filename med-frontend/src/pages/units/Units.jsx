import { useEffect, useState } from 'react'
import CustomTable from '../../components/tables/CustomTable'
import Header from '../../components/Header'
import { Box } from '@mui/material'
import AddIcon from '@mui/icons-material/Add';
import FabLink from '../../components/buttons/FabLink';
import triggerCannotDeleteAlert from '../../components/alerts/CannotDeleteAlert';
import CountingUnitService from '../../services/countingUnitService';
import WeightUnitService from '../../services/weightUnitService';

function Units() {

  const [countingUnits, setCountingUnits] = useState([])
  const [weightUnits, setWeightUnits] = useState([])

  useEffect(() => {

    async function fetchData() {
      const allCountingUnits = await CountingUnitService.getCountingUnits();
      const allWeightUnits = await WeightUnitService.getWeightUnits();

      setCountingUnits(allCountingUnits)
      setWeightUnits(allWeightUnits)

    }

    fetchData()

  }, [])

  async function handleDeleteCountingUnit(id) {
    const error = await CountingUnitService.deleteCountingUnit(id)
    {error.status === 500? triggerCannotDeleteAlert('unidad de conteo', id, 'No se puede eliminar esta unidad de conteo, puesto que está ligada a algún lote'): null}
  }

  async function handleDeleteWeightUnit(id) {
    const error = await WeightUnitService.deleteWeightUnit(id)
    {error.status === 500? triggerCannotDeleteAlert('unidad de peso', id, 'No se puede eliminar esta unidad de peso, puesto que está ligada a algún lote'): null}
  }

  return (
    <>
      <Header title={"Unidad de Conteo"} />

      <FabLink to="/unidades-conteo/agregar" icon={<AddIcon/>} color='secondary'/ >


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



      <Header title={"Unidad de Peso"} />

      <FabLink to="/unidades-peso/agregar" icon={<AddIcon/>} color='secondary'/ >

      

      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' }
        ]}
        singleEntity='unidad de peso'
        entity='unidades-peso'
        handleDelete={handleDeleteWeightUnit}
        data={weightUnits} />
    </>
  )
}

export default Units