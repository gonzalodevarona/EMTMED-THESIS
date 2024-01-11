import { useState, useEffect } from 'react';
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
import MedicineService from '../../services/medicineService';
import MedicationBatchService from '../../services/medicationBatchService';
import BatchService from '../../services/batchService';
import { Delete } from '@mui/icons-material';
import ConsumableService from '../../services/consumableService';
import { Typography } from '@mui/material';
import { removeNullProperties } from '../../utils/CommonMethods'
import { dateArrayToString } from '../../utils/EntityProcessingMethods'



function SupplyTable({ addOrUpdateBatch, removeBatch, userRole  }) {

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





    const [supplies, setSupplies] = useState([])


    useEffect(() => {
        async function fetchSupplies() {
            let allMedicines ;
            let allConsumables ;

            let allSupplies ;

            console.log(userRole)
            if(userRole === 'ROLE_NURSE'){
                allConsumables = await ConsumableService.getConsumablesInStock();
    
                allSupplies = [...allConsumables];
            } else{
                allMedicines = await MedicineService.getMedicinesInStock();
                allConsumables = await ConsumableService.getConsumablesInStock();
    
                allSupplies = [...allMedicines, ...allConsumables];
            }
            

            let allBatches = []
            for (let supply of allSupplies) {
                if (supply.batches) {
                    for (let batchKey in supply.batches) {
                        let batch = supply.batches[batchKey];
                        console.log(batch)
                        if(batch.cum != undefined){
                            batch.location = await MedicationBatchService.getLocationByMedicationBatchId(batch.id);
                        }else{
                            batch.location = await BatchService.getLocationByBatchId(batch.id);
                        }
                        batch.expirationDate = dateArrayToString(batch.expirationDate)
                        batch.assignedQuantity = 0
                        batch.supply = { ...supply }; // Hacemos una copia del objeto
                        delete batch.supply.batches; // Eliminamos la propiedad batches de la copia
                        allBatches.push(batch);
                    }
                }
            }
            

            setSupplies(Object.values(removeNullProperties(allBatches)));
        }

        fetchSupplies()
    }, [])

    useEffect(() => {
      console.log(supplies)
    }, [supplies])
    
    const columns = [
        { title: 'ID Recurso', field: 'supply.id', editable: 'never' },
        { title: 'Nombre Recurso', field: 'supply.name', editable: 'never' },
        { title: 'Concentración (sí aplica)', field: 'supply.concentration', editable: 'never' },
        { title: 'Vía de Administración', field: 'administrationRoute', editable: 'never' },
        { title: 'Cantidad Disponible', field: 'quantity', type: 'numeric', editable: 'never' },
        { title: 'Unidad de Conteo', field: 'supply.countingUnit.name', editable: 'never' },
        { title: 'Ubicación', field: 'location.name', editable: 'never' },
        { title: 'Cantidad a Asignar', field: 'assignedQuantity', type: 'numeric' },

    ];

    return (
        <MaterialTable
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
            title={userRole === 'ROLE_NURSE' ? 'Insumos Disponibles' : 'Medicamentos e Insumos Disponibles'}
            icons={tableIcons}
            columns={columns}
            data={supplies}
            actions={[
                {
                    icon: () => <Delete color="error" fontSize='medium' />,
                    tooltip: 'Borrar recurso de la orden',
                    onClick: (event, rowData) => { removeBatch(rowData.id); rowData.assignedQuantity = 0; }
                }
            ]}
            cellEditable={{
                onCellEditApproved: (newValue, oldValue, rowData, columnDef) => {
                    
                    if (newValue > 0 && newValue <= rowData.quantity) {
                        rowData.assignedQuantity = newValue;
                        addOrUpdateBatch(rowData)
                    } else if (newValue === 0) {
                        rowData.assignedQuantity = newValue;
                        removeBatch(rowData.id)
                    }

                    return new Promise((resolve, reject) => {
                        
                        setTimeout(resolve, 500);
                    });
                }
            }}
        />
    )
}

export default SupplyTable