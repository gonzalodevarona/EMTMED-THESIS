import React from 'react'
import CustomTable from '../../components/CustomTable'
import Header from '../../components/Header'
import { Box, Button, Divider } from '@mui/material'

function Orders() {
  const test = [
    {
      id:1,
      name:'gr',
     
    },
    {
      id:2,
      name:'kg',
     

    },
    {
      id:2,
      name:'kg',
      
      
    },
    {
        id:2,
        name:'kg',
        
        
      },
      {
        id:2,
        name:'kg',
        
        
      },
      {
        id:2,
        name:'kg',
        
        
      },
      {
        id:2,
        name:'kg',
        
        
      },
      {
        id:2,
        name:'kg',
        
        
      },
      {
        id:2,
        name:'kg',
        
        
      },
      {
        id:2,
        name:'kg',
        
        
      },
      {
        id:2,
        name:'kg',
        
        
      },
      {
        id:2,
        name:'kg',
        
        
      },
      

  ]
  return (
    <>
      <Header title={"Ordenes"} />
      <Button variant="contained" sx={{px:10, py:1, mb:2}} color={'info'}>Agregar orden</Button>
      <CustomTable
        columns={[
          { title: 'ID', field: 'id', type: 'numeric' },
          { title: 'Nombre', field: 'name' }
        ]}
        data={test} />
      
    </>
  )
}

export default Orders