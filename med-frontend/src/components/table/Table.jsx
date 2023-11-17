import { Table, TableContainer, TableHead, TableRow, TableCell, TableBody, Button, Paper, Stack } from "@mui/material"
import axios from "../../util/axios"
import { useEffect, useState } from "react"
import Swal from "sweetalert2"
import withReactContent from "sweetalert2-react-content"
import Header from "../../components/Header"
import { red } from "@mui/material/colors"
import Spinner from "../../components/Spinner"
import { Badge } from "@mui/icons-material"

const Table = ({ rows }) => {


    return (
        <>
            <TableContainer component={Paper}>
                <Table sx={{ minWidth: 650 }}>
                    <TableHead>
                        <TableRow>
                        {rows.map((row) => (
                            <TableCell>{row}</TableCell>
                        ))}
                        </TableRow>
                        
                    </TableHead>
                    <TableBody>
                        {employees.map((employee) => (
                            <TableRow key={employee.id}>
                                <TableCell>{employee.fullName}</TableCell>
                                <TableCell>{employee.idCard}</TableCell>
                                <TableCell>{employee.restaurant.name}</TableCell>
                                <TableCell>{employee.restaurant.location}</TableCell>
                                <TableCell>
                                    <Stack direction="row" spacing={1}>
                                        <Button
                                            variant="contained"
                                            color="primary"
                                            fullWidth
                                            onClick={() => handleEdit(employee)}
                                        >
                                            Editar
                                        </Button>
                                        <Button
                                            variant="contained"
                                            color="error"
                                            fullWidth
                                            onClick={() => handleDelete(employee)}
                                        >
                                            Eliminar
                                        </Button>
                                    </Stack>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </>
    )
}

export default Table