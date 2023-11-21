import React from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Box, Button } from '@mui/material'
import { Link } from 'react-router-dom';

function Units() {
  const test = [{ id: 1, name: 'ola' }, { id: 2, name: 'soy yo' }]
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
        data={test} />

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
        data={test} />
    </>
  )
}

export default Units