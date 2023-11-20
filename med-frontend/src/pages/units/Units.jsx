import React from 'react'
// import CustomTable from '../../components/CustomTable'
import CustomTable from '../../components/CustomTable2'
import Header from '../../components/Header'
import { Box, Divider } from '@mui/material'

function Units() {
  const test = [
    {
      id: 1,
      nombre: 'gr',
      x: 'gr',
      y: 'gr',
    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    }, {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },
    {
      id: 2,
      nombre: 'kg',
      x: 'gr',
      y: 'gr',

    },

  ]
  return (
    <>
      {/* <Header title={"Unidad de conteo"} />
      <CustomTable rows={test}></CustomTable>
      
      <Box my={3}></Box>
      
      
      <Header title={"Unidad de peso"} />
      <CustomTable rows={test}></CustomTable> */}

      <Header title={"Unidad de conteo"} />
      <CustomTable
        columns={[
          { title: 'Tipo de documento', field: 'id', type: 'numeric' },
          { title: 'Número de documento', field: 'nombre' },
          { title: 'Nombre', field: 'x' },
          { title: 'Apellido', field: 'y' }
        ]}
        page="dashboard"
        data={test} />
    </>
  )
}

export default Units