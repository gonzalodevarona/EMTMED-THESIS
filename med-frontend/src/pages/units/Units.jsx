import { useEffect, useState } from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Box, Button } from '@mui/material'
import AddIcon from '@mui/icons-material/Add';
import FabLink from '../../components/buttons/FabLink';
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
    await CountingUnitService.deleteCountingUnit(id)
  }

  async function handleDeleteWeightUnit(id) {
    await WeightUnitService.deleteWeightUnit(id)
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