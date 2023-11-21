import {useEffect, useState} from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Box, Button } from '@mui/material'
import { Link } from 'react-router-dom';
import CountingUnitService from '../../services/countingUnitService';
import WeightUnitService from '../../services/weightUnitService';

function Units() {
  
  const [countingUnits, setCountingUnits] = useState([])
  const [weightUnits, setWeightUnits] = useState([])

  useEffect(() => {

    async function fetchData(){
      const allCountingUnits = await CountingUnitService.getCountingUnits();
      const allWeightUnits = await WeightUnitService.getWeightUnits();

      setCountingUnits(allCountingUnits)
      setWeightUnits(allWeightUnits)

    }

    fetchData()
    
  }, [])

  async function handleDeleteCountingUnit(id){
    await CountingUnitService.deleteCountingUnit(id)
  }

  async function handleDeleteWeightUnit(id){
    await WeightUnitService.deleteWeightUnit(id)
  }
  
  return (
    <>
      <Header title={"Unidad de conteo"} />

      <Button
        component={Link}
        to="/unidadesconteo/agregar"
        variant="contained"
        sx={{ px: 10, py: 1, mb: 2 }}
        color={'info'}>
        Agregar unidad de conteo
      </Button>

      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' }
        ]}
        singleEntity='unidad de conteo'
        entity='unidadesconteo'
        handleDelete={handleDeleteCountingUnit}
        data={countingUnits} />

      <Box my={5}></Box>

      

      <Header title={"Unidad de peso"} />

      <Button
        component={Link}
        to="/unidadespeso/agregar"
        variant="contained"
        sx={{ px: 10, py: 1, mb: 2 }}
        color={'info'}>
        Agregar unidad de peso
      </Button>

      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' }
        ]}
        singleEntity='unidad de peso'
        entity='unidadespeso'
        handleDelete={handleDeleteWeightUnit}
        data={weightUnits} />
    </>
  )
}

export default Units