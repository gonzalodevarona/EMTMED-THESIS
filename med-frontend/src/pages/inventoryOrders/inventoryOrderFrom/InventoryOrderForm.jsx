import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Button, Stack, MenuItem } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import FormDateTimePicker from '../../../components/form/FormDateTimePicker';
import InventoryOrderService from "../../../services/inventoryOrderService";
import PharmacyService from "../../../services/pharmacyService";
import DisposalStationService from "../../../services/disposalStationService";
import InventoryOrderOperationService from "../../../services/inventoryOrderOperationService";
import OrderService from "../../../services/orderService";
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import { refreshPage } from "../../../utils/CommonMethods";
import dayjs from 'dayjs';



function InventoryOrderForm({ action, preloadedData, id }) {

    const [statuses, setStatuses] = useState([])
    const [locations, setLocations] = useState([])
    const [operations, setOperations] = useState([])

    useEffect(() => {
        async function fetchStatuses() {
            let data = await OrderService.getOrderStatuses();
            setStatuses(data);
        }

        async function fetchOperations() {
            let data = await InventoryOrderOperationService.getInventoryOrderOperations();
            setOperations(data);
        }

        async function fetchLocations() {
            try {
                let pharmacies = await PharmacyService.getPharmacies();
                let disposalStations = await DisposalStationService.getDisposalStations();

                const concatenatedArray = [...pharmacies, ...disposalStations];


                setLocations(concatenatedArray.map(location => ({ id: location.id, name: location.name })));


            } catch (error) {
                console.error("Error fetching locations:", error);
            }
        }


        fetchStatuses()
        fetchOperations()
        fetchLocations()
    }, [])


    async function addInventoryOrder(inventoryOrder) {
        return await InventoryOrderService.addInventoryOrder(inventoryOrder);
    }

    async function editInventoryOrder(inventoryOrder) {
        inventoryOrder.id = id;
        return await InventoryOrderService.editInventoryOrder(inventoryOrder);
    }



    const {
        handleSubmit,
        register,
        formState: { errors },
        control,
    } = useForm({
        defaultValues: preloadedData
    });

    function addedSuccessfully() {
        triggerInfoAlert('success', 'La nueva orden de inventario ha sido agregada', refreshPage)
    }
    function errorAdding() {
        triggerInfoAlert('error', 'Ha habido un error agregando la nueva orden de inventario')
    }

    function editedSuccessfully() {
        triggerInfoAlert('success', 'La orden de inventario ha sido editada', refreshPage)
    }
    function errorEditing() {
        triggerInfoAlert('error', 'Ha habido un error editando la orden de inventario')
    }

    function onSubmit(data) {
        data.type = 'inventoryOrder';
        switch (action) {
            case 'add':
                addInventoryOrder(data)
                    .then((result) => {
                        console.log(result)
                        addedSuccessfully()
                    })
                    .catch((error) => {
                        errorAdding()
                        console.error('Error en la operación:', error);
                    });
                break;
            case 'edit':
                editInventoryOrder(data)
                    .then((result) => {
                        editedSuccessfully()
                    })
                    .catch((error) => {
                        errorEditing()
                        console.error('Error en la operación:', error);
                    });
                break;
            default:
                break;
        }
    };

    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                <Stack spacing={2} width={400}>

                    <FormTextfield 
                    isRequired 
                    type='number' 
                    label='CC del Autorizador' 
                    name='practitionerId' 
                    register={register} 
                    errors={errors} />

                    <FormDateTimePicker 
                    disableFuture 
                    label='Fecha de Autorización' 
                    name='authoredOn' 
                    control={control} 
                    errors={errors} 
                    value={action === 'edit' ? preloadedData?.authoredOn : dayjs()}/>

                    {statuses.length > 0 &&
                        <FormSelect
                            disabled
                            name="status"
                            label="Estado"
                            defaultValue={action === 'edit' ? preloadedData?.status : statuses[0]}
                            register={register}
                            errors={errors}
                        >

                            {statuses.map(status => <MenuItem key={status} value={status}>
                                {status}
                            </MenuItem>)}
                        </FormSelect>
                    }

                    {operations.length > 0 &&
                        <FormSelect
                            name="operation"
                            label="Operación"
                            defaultValue={action === 'edit' ? preloadedData?.operation : operations[0]}
                            register={register}
                            errors={errors}
                        >

                            {operations.map(operation => <MenuItem key={operation} value={operation}>
                                {operation}
                            </MenuItem>)}
                        </FormSelect>
                    }

                    {locations.length > 0 &&
                        <FormSelect
                            name="source"
                            label="Origen"
                            defaultValue={action === 'edit' ? preloadedData?.source : ''}
                            register={register}
                            errors={errors}
                        >

                            {locations.map(location => <MenuItem key={location.id} value={location.name}>
                                {`${location.id} - ${location.name}`}
                            </MenuItem>)}
                        </FormSelect>
                    }

                    {locations.length > 0 &&
                        <FormSelect
                            name="destination"
                            label="Destino"
                            defaultValue={action === 'edit' ? preloadedData?.destination : ''}
                            register={register}
                            errors={errors}
                        >

                            {locations.map(location => <MenuItem key={location.id} value={location.name}>
                                {`${location.id} - ${location.name}`}
                            </MenuItem>)}
                        </FormSelect>
                    }

                    <FormTextfield multiline maxRows={4} label='Nota' name='note' register={register} errors={errors} />

                    <Button type="submit" variant="contained" color="info">
                        {action === 'add' ? 'Agregar' : 'Editar'}
                    </Button>
                </Stack>
            </form>
            <DevTool control={control} />

        </>
    )
}

export default InventoryOrderForm