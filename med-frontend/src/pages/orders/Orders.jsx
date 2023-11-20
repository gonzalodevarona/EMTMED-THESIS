import React from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Box, Button, Divider } from '@mui/material'

function Orders() {
  const test = [
    {
      id:1,
      nombre:'gr',
      x:'gr',
      y:'gr',
    },
    {
      id:2,
      nombre:'kg',
      x:'gr',
      y:'gr',

    },
    {
      id:2,
      nombre:'kg',
      x:'gr',
      y:'gr',
      
    },
    {
        id:2,
        nombre:'kg',
        x:'gr',
        y:'gr',
        
      },
      {
        id:2,
        nombre:'kg',
        x:'gr',
        y:'gr',
        
      },
      {
        id:2,
        nombre:'kg',
        x:'gr',
        y:'gr',
        
      },
      {
        id:2,
        nombre:'kg',
        x:'gr',
        y:'gr',
        
      },
      {
        id:2,
        nombre:'kg',
        x:'gr',
        y:'gr',
        
      },
      {
        id:2,
        nombre:'kg',
        x:'gr',
        y:'gr',
        
      },
      {
        id:2,
        nombre:'kg',
        x:'gr',
        y:'gr',
        
      },
      {
        id:2,
        nombre:'kg',
        x:'gr',
        y:'gr',
        
      },
      {
        id:2,
        nombre:'kg',
        x:'gr',
        y:'gr',
        
      },
      

  ]
  return (
    <>
      <Header title={"Ordenes"} />
      <Button variant="contained" sx={{px:10, py:1, mb:2}} color={'info'}>Agregar orden</Button>
      <CustomTable rows={test}></CustomTable>
      
    </>
  )
}

export default Orders