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
import { convertToLocalTimeZone, convertDateObjectToDayjs } from "../../../utils/EntityProcessingMethods";
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


                setLocations(concatenatedArray.map(location => ({ id: location.id, name: location.name, type: location.type })));


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

    async function onSubmit(data) {
        data = await processInventoryOrder(data)

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
                        if(result.status === 500){
                            errorEditing()
                            console.error('Error en la operación:', result);
                        } else{
                            editedSuccessfully()
                        }
                        
                    })
                break;
            default:
                break;
        }
    };

    async function processInventoryOrder(data) {
        data.type = 'inventoryOrder';

        switch (action) {
            case 'add':
                data.authoredOn = convertToLocalTimeZone(data.authoredOn.toISOString());
                data.source = await fetchLocationAdd(data.source)
                data.destination = await fetchLocationAdd(data.destination)
                break;
            case 'edit':
                data.authoredOn = convertDateObjectToDayjs(data.authoredOn).toISOString();
                data.source = await fetchLocationEdit(data.source)
                data.source = {id: data.source.id, type: data.source.type}

                data.destination = await fetchLocationEdit(data.destination)
                data.destination = {id: data.destination.id, type: data.destination.type}
                break;

            default:
                break;
        }

        return data;
    }
    async function fetchLocationAdd(locationString) {

        let parts = locationString.split(' ');
        let secondPosition = parts[1];

        let locationEntity;
        switch (secondPosition) {
            case 'pharmacy':
                locationEntity = await PharmacyService.getPharmacyById(Number(parts[0]))
                break;
            case 'disposalStation':
                locationEntity = await DisposalStationService.getDisposalStationById(Number(parts[0]))
                break;
            default:
                break;
        }

        return locationEntity;
    }

    async function fetchLocationEdit(locationObject) {
        let foundLocation = locations.find(item => item.id === locationObject.id);
        let locationEntity;
        switch (foundLocation.type) {
            case 'pharmacy':
                locationEntity = await PharmacyService.getPharmacyById(foundLocation.id)
                break;
            case 'disposalStation':
                locationEntity = await DisposalStationService.getDisposalStationById(foundLocation.id)
                break;
            default:
                break;
        }

        return locationEntity;
    }

    function findPreloadedLocation(preloadedLocationId) {
        let foundLocation = locations.find(location => location.id === preloadedLocationId) || null;
        if (foundLocation !== null) {
            return `${foundLocation.id} ${foundLocation.type}`
        } else {
            return null;
        }

    }




    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                <Stack spacing={2} width={400}>

                    <FormTextfield
                        isRequired
                        type='number'
                        disabled={action === 'edit' ? true : false}
                        label='CC del Autorizador'
                        name='practitionerId'
                        register={register}
                        errors={errors} />

                    <FormDateTimePicker
                        disableFuture
                        label='Fecha de Autorización'
                        disabled={action === 'edit' ? true : false}
                        name='authoredOn'
                        control={control}
                        errors={errors}
                        value={action === 'edit' ? preloadedData?.authoredOn : dayjs()} />

                    {statuses.length > 0 &&
                        <FormSelect
                            disabled={action === 'add' ? true : false}
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
                            disabled={action === 'edit' ? true : false}
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
                            disabled={action === 'edit' ? true : false}
                            defaultValue={action === 'edit' ? findPreloadedLocation(preloadedData?.source.id) : ''}
                            register={register}
                            errors={errors}
                        >

                            {locations.map(location => <MenuItem key={location.id} value={`${location.id} ${location.type}`}>
                                {`${location.id} - ${location.name}`}
                            </MenuItem>)}
                        </FormSelect>
                    }

                    {locations.length > 0 &&
                        <FormSelect
                            name="destination"
                            label="Destino"
                            disabled={action === 'edit' ? true : false}
                            defaultValue={action === 'edit' ? findPreloadedLocation(preloadedData?.destination.id) : ''}
                            register={register}
                            errors={errors}
                        >

                            {locations.map(location => <MenuItem key={location.id} value={`${location.id} ${location.type}`}>
                                {`${location.id} - ${location.name}`}
                            </MenuItem>)}
                        </FormSelect>
                    }

                    <FormTextfield inputProps={{ maxLength: 255 }} multiline maxRows={4} label='Nota (máximo 255 caractéres)' name='note' register={register} errors={errors} />

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