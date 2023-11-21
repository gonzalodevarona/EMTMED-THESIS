import MaterialTable from 'material-table';
import { forwardRef } from "react";
import AddBox from '@material-ui/icons/AddBox';
import ArrowDownward from '@material-ui/icons/ArrowDownward';
import Check from '@material-ui/icons/Check';
import ChevronLeft from '@material-ui/icons/ChevronLeft';
import ChevronRight from '@material-ui/icons/ChevronRight';
import Clear from '@material-ui/icons/Clear';
import DeleteOutline from '@material-ui/icons/DeleteOutline';
import Edit from '@material-ui/icons/Edit';
import FilterList from '@material-ui/icons/FilterList';
import FirstPage from '@material-ui/icons/FirstPage';
import LastPage from '@material-ui/icons/LastPage';
import Remove from '@material-ui/icons/Remove';
import SaveAlt from '@material-ui/icons/SaveAlt';
import Search from '@material-ui/icons/Search';
import ViewColumn from '@material-ui/icons/ViewColumn';
import { Box } from "@mui/material";
import { Typography } from '@mui/material';
import { Delete } from '@mui/icons-material';
import { useNavigate } from "react-router-dom";
import Swal from 'sweetalert2';
import triggerConfrirmationAlert from './alerts/ConfirmationAlert';
import { capitalizeFirstLetter, refreshPage } from '../utils/CommonMethods';


function CustomTable({ entity, sx, columns, data, singleEntity, handleDelete, deleteable, editable }) {
    const navigate = useNavigate();



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
        onClick: (event, rowData) => navigate(`/${entity}/editar/${rowData.id}`)
    }

    const deleteAction = {
        icon: () => <Delete color="error" fontSize='medium' />,
        tooltip: 'Eliminar',
        onClick: (event, rowData) =>
            triggerConfrirmationAlert(
                `Eliminar ${singleEntity} con ID ${rowData.id}`,
                "¿Estas seguro que quieres eliminarlo?",
                "warning",
                "Borrar",
                () => handleDelete(rowData.id),
                `${capitalizeFirstLetter(singleEntity)} con ID ${rowData.id} eliminada con éxito.`,
                "success",
                refreshPage,
                `${capitalizeFirstLetter(singleEntity)} con ID ${rowData.id} NO fue eliminada.`,
                "error"
            )

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
                title=''
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
                    body: {
                        emptyDataSourceMessage: (<Typography variant='p'>No hay datos para mostrar</Typography>)
                    },


                }}

                onRowClick={(event, rowData) => {
                    window.open(`/${entity}/${rowData.id}`, "_self")
                    event.stopPropagation();
                }}
                actions={setActions()}
            />
        </Box>



    )
}

export default CustomTable;