import React from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Box, Button } from '@mui/material'

function Units() {
  const test = [{ id: 1, name: 'ola' }, { id: 2, name: 'soy yo' }]
  return (
    <>
      <Header title={"Unidad de conteo"} />
      <Button variant="contained" sx={{px:10, py:1, mb:2}} color={'info'}>Agregar unidad de conteo</Button>
      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' }
        ]}
        data={test} />
      <Box my={5}></Box>
      <Header title={"Unidad de peso"} />
      <Button variant="contained" sx={{px:10, py:1, mb:2}} color={'info'}>Agregar unidad de peso</Button>
      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' }
        ]}
        data={test} />
    </>
  )
}

export default Units