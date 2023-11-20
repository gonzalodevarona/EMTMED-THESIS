import MaterialTable from 'material-table';
import { forwardRef } from "react";
import { ThemeProvider } from '@mui/material/styles';
import AddBox from '@material-ui/icons/AddBox';
import ArrowDownward from '@material-ui/icons/ArrowDownward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import DeleteIcon from '@mui/icons-material/Delete';
import ConfirmationNumberIcon from '@mui/icons-material/ConfirmationNumber';
import EditRoundedIcon from '@mui/icons-material/EditRounded';
import GetAppIcon from '@mui/icons-material/GetApp';
import DeleteSweepIcon from '@mui/icons-material/DeleteSweep';
import ShoppingCartCheckoutIcon from '@mui/icons-material/ShoppingCartCheckout';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import { Box } from "@mui/material";
import { useNavigate } from 'react-router-dom';

import { Typography } from '@mui/material';
import { Delete } from '@mui/icons-material';





function CustomTable({ title, sx, columns, data, handleEdit, handleDelete, deleteable, editable }) {


    const navigate = useNavigate();

    const handleRedirectClick = (route) => {
        navigate('/isc/'+route)
    };

    const tableIcons = {

        Add: forwardRef((props, ref) => <AddBox {...props} ref={ref} />),
        Check: forwardRef((props, ref) => <Check {...props} ref={ref} />),
        Clear: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
        Delete: forwardRef((props, ref) => <DeleteOutline {...props} ref={ref} color='action' />),
        DetailPanel: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
        Edit: forwardRef((props, ref) => <Edit {...props} ref={ref} />),
        Export: forwardRef((props, ref) => <SaveAlt {...props} ref={ref} />),
        Filter: forwardRef((props, ref) => <FilterList {...props} ref={ref} />),
        FirstPage: forwardRef((props, ref) => <FirstPage {...props} ref={ref} />),
        LastPage: forwardRef((props, ref) => <LastPage {...props} ref={ref} />),
        NextPage: forwardRef((props, ref) => <ChevronRight {...props} ref={ref} />),
        PreviousPage: forwardRef((props, ref) => <ChevronLeft {...props} ref={ref} />),
        ResetSearch: forwardRef((props, ref) => <Clear {...props} ref={ref} />),
        Search: forwardRef((props, ref) => <Search {...props} ref={ref} />),
        SortArrow: forwardRef((props, ref) => <ArrowDownward {...props} ref={ref} />),
        ThirdStateCheck: forwardRef((props, ref) => <Remove {...props} ref={ref} />),
        ViewColumn: forwardRef((props, ref) => <ViewColumn {...props} ref={ref} />)
    };


    

    const editAction = {
        icon: () => <Edit color="primary" fontSize='medium' />,
        tooltip: 'Editar',
        onClick: (event, rowData) => handleRedirectClick('ticket/edit')
    }

    const deleteAction = {
        icon: () => <Delete color="error" fontSize='medium' />,
        tooltip: 'Eliminar',
        onClick: (event, rowData) => TicketService.getTicketsByRaffleIdAndStatus(rowData.id, 'available')
    }





    const setActions = () => {
        let actions = []

        editable == false ? null : actions.push(editAction);
        deleteable == false ? null : actions.push(deleteAction);
        

        return actions;
    }

    return (
        
            <Box sx={sx}>
                <MaterialTable
                    icons={tableIcons}
                    title={<Typography sx={{ m: 0 }} variant='h1'>{title}</Typography>}
                    columns={columns}
                    data={data}
                    options={{
                        headerStyle: {
                            
                            fontSize: 16,
                            fontWeight: 'bold',
                            position: 'sticky', 
                            top: 0
                        },
                        maxBodyHeight: '450px', 
                        rowStyle: {
                            fontSize: 16,
                        },
                        actionsColumnIndex: -1
                    }}
                    localization={{
                        toolbar: {
                            searchPlaceholder: "Buscar"
                        },
                        header: {
                            actions: 'Acciones'
                        },
                        pagination: {
                            labelDisplayedRows: '{from}-{to} de {count}',
                            labelRowsSelect: 'filas',
                            labelRowsPerPage: 'Filas por página:',
                            firstAriaLabel: 'Primera página',
                            firstTooltip: 'Primera página',
                            previousAriaLabel: 'Página anterior',
                            previousTooltip: 'Página anterior',
                            nextAriaLabel: 'Página siguiente',
                            nextTooltip: 'Página siguiente',
                            lastAriaLabel: 'Última página',
                            lastTooltip: 'Última página'
                        },
                        body:{
                            emptyDataSourceMessage: (<Typography variant='p'>No hay datos para mostrar</Typography>)
                        },
                        

                    }}
                    onRowClick={(event, rowData) => {
                        
                        window.open(`mysite.com/product/${rowData.id}`, "_blank")
                        event.stopPropagation();
                      }}
                    
                     actions={setActions()}
                />
            </Box>
            
        

    )
}

export default CustomTable;