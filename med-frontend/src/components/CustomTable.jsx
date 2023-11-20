import { Table, TableContainer, TableHead, TableRow, TableCell, TableBody, Button, Paper, Stack, Fab, Box, IconButton } from "@mui/material"
import { useEffect, useState } from "react"
import Swal from "sweetalert2"
import FilledIconButton from "./FilledIconButton"
// import withReactContent from "sweetalert2-react-content"

import { red } from "@mui/material/colors"

import { Badge, Delete, Edit } from "@mui/icons-material"

const CustomTable = ({ rows, handleEdit, handleDelete }) => {

  useEffect(() => {
    console.log(Object.keys(rows[0]))
  }, [])

  return (
    <>
      {rows.length > 0 ?
        <TableContainer sx={{ maxHeight: '502px' }} component={Paper}>
          <Table stickyHeader sx={{ minWidth: 650 }}>
            <TableHead>
              <TableRow>
                {Object.keys(rows[0]).map(columnName => <TableCell key={columnName} align="center">{columnName.charAt(0).toUpperCase() + columnName.slice(1)}</TableCell>)}
                <TableCell key={'actions'} align="center">Acciones</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {rows.map(row =>
                <TableRow key={row.id}>
                  {Object.values(row).map(value => <TableCell key={value} align="center">{value}</TableCell>)}
                  <TableCell align="center">
                    <Box align="center" sx={{gap:2}}>
                    <FilledIconButton pr={'2'} variant="filled">
                        <Edit />
                      </FilledIconButton>
                      <FilledIconButton variant="filled">
                        <Delete />
                      </FilledIconButton>
                    
                      {/* <TableCell sx={{ 'border-bottom': 0, p: 0 }} align="center">
                        <Fab color="info" size="medium"
                          onClick={() => handleEdit(employee)}>
                          <Edit />
                        </Fab>
                      </TableCell>
                      <TableCell sx={{ 'border-bottom': 0, p: 0, pl: 2 }} align="center">
                        <Fab color="error" size="medium"
                          onClick={() => handleDelete(employee)}>
                          <Delete />
                        </Fab>
                      </TableCell> */}
                    </Box>
                  </TableCell>
                </TableRow>
              )}
            </TableBody>
          </Table>
        </TableContainer> : s}
    </>

  )
}

export default CustomTable