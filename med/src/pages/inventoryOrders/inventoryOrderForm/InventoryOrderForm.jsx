import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from 'react-i18next';
import { useKeycloak } from '@react-keycloak/web'
import { useNavigate } from "react-router-dom";
import { Box, Stack, MenuItem } from "@mui/material";
import FabSubmitButton from '../../../components/buttons/FabSubmitButton';
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import FormDateTimePicker from '../../../components/form/FormDateTimePicker';
import SupplyTable from '../../../components/tables/SupplyTable';
import InventoryOrderService from "../../../services/inventoryOrderService";
import PharmacyService from "../../../services/pharmacyService";
import DisposalStationService from "../../../services/disposalStationService";
import InventoryOrderOperationService from "../../../services/inventoryOrderOperationService";
import OrderService from "../../../services/orderService";
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import { convertToLocalTimeZone, convertDateObjectToDayjs, convertDateToISO } from "../../../utils/EntityProcessingMethods";
import dayjs from 'dayjs';

function InventoryOrderForm({ action, preloadedData, id }) {

    const { t } = useTranslation();


    const navigate = useNavigate();

    const { keycloak } = useKeycloak()

    const redirect = (path) => {
        navigate(path);
    };

    const [statuses, setStatuses] = useState([])
    const [locations, setLocations] = useState([])
    const [pharmacies, setPharmacies] = useState([])
    const [disposalStations, setDisposalStations] = useState([])
    const [operations, setOperations] = useState([])
    const [chosenBatches, setChosenBatches] = useState([])
    const [disableDestination, setDisableDestination] = useState(false)

    function fetchUser() {
        keycloak.loadUserProfile().then((profile) => {
            setValue('practitionerId', profile.attributes.idNumber[0])
        })
    }

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

                setPharmacies(pharmacies.map(pharmacy => ({ id: pharmacy.id, name: pharmacy.name, type: pharmacy.type, category: pharmacy.category })))
                setDisposalStations(disposalStations.map(disposalStation => ({ id: disposalStation.id, name: disposalStation.name, type: disposalStation.type })))
                setLocations(concatenatedArray.map(location => ({ id: location.id, name: location.name, type: location.type })));


            } catch (error) {
                console.error("Error fetching locations:", error);
            }
        }

        fetchUser()
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

    const addOrUpdateBatch = (batch) => {
        setChosenBatches(prevBatches => {
            const existingBatchIndex = prevBatches.findIndex(b => b.id === batch.id);

            if (existingBatchIndex !== -1) {
                // El objeto ya existe en el arreglo, actualizamos la propiedad assignedQuantity
                const updatedBatches = [...prevBatches];
                updatedBatches[existingBatchIndex].assignedQuantity = batch.assignedQuantity;
                return updatedBatches;
            } else {
                // El objeto no existe en el arreglo, lo agregamos
                return [...prevBatches, batch];
            }
        });
    };

    const removeBatch = (id) => {
        setChosenBatches(prevBatches => prevBatches.filter(batch => batch.id !== id));
    };


    const {
        handleSubmit,
        register,
        formState: { errors },
        reset,
        watch,
        control,
        setValue
    } = useForm({
        defaultValues: preloadedData
    });

    const operationWatch = watch('operation')

    useEffect(() => {
        if (operationWatch === 'ENTRY') {
            setDisableDestination(true)
        } else if (operationWatch === 'TRANSFER') {
            let pharmaciesWithoutWarehouse = pharmacies.filter(pharmacy => pharmacy.category !== 'WAREHOUSE');
            setLocations(pharmaciesWithoutWarehouse)
            setDisableDestination(false)
        } else if (operationWatch === 'EXIT') {
            let warehousePharmacy = pharmacies.find(pharmacy => pharmacy.category === 'WAREHOUSE');
            let exits = [...disposalStations];
            exits.push(warehousePharmacy);
            setLocations(exits)
            setDisableDestination(false)
        } else {
            setDisableDestination(false)
        }
    }, [operationWatch])

    function resetForm() {
        reset()

        fetchUser()

    }


    function addedSuccessfully() {
        triggerInfoAlert('success', 'La nueva orden de inventario ha sido agregada', resetForm)
    }
    function errorAdding() {
        triggerInfoAlert('error', 'Ha habido un error agregando la nueva orden de inventario')
    }

    function editedSuccessfully() {
        triggerInfoAlert('success', 'La orden de inventario ha sido editada', () => redirect(-1))
    }
    function errorEditing() {
        triggerInfoAlert('error', 'Ha habido un error editando la orden de inventario')
    }

    async function onSubmit(data) {
        data = await processInventoryOrder(data)
        data.batches = [];
        data.medicationBatches = [];
        console.log(chosenBatches)

        let chosenBatchesCopy = [...chosenBatches]
        console.log(chosenBatchesCopy)
        chosenBatchesCopy.forEach(obj => {
            obj.expirationDate = convertDateToISO(obj.expirationDate)
            obj.quantity = obj.assignedQuantity;
            if (obj.supply.type === 'medicine') {
                data.medicationBatches.push(obj);
            } else {
                data.batches.push(obj);
            }
        });
        console.log(data)
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
                        if (result.status === 500) {
                            errorEditing()
                            console.error('Error en la operación:', result);
                        } else {
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
                data.destination = await fetchLocationAdd(data.destination)
                break;
            case 'edit':
                data.authoredOn = convertDateObjectToDayjs(data.authoredOn).toISOString();

                data.destination = await fetchLocationEdit(data.destination)
                data.destination = { id: data.destination.id, type: data.destination.type }
                break;

            default:
                break;
        }

        return data;
    }
    async function fetchLocationAdd(locationString) {
        console.log(locationString)
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
                        disabled
                        type='number'
                        InputLabelProps={{
                            shrink: true,
                        }}
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
                                {t(`order.status.${status}`)}
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
                                {t(`inventoryOrder.operation.${operation}`)}
                            </MenuItem>)}
                        </FormSelect>
                    }



                    {locations.length > 0 && !disableDestination &&
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


                    <Box>

                        <FabSubmitButton color='info' />
                    </Box>
                </Stack>
            </form>
            <SupplyTable addOrUpdateBatch={addOrUpdateBatch} removeBatch={removeBatch}></SupplyTable>
        </>
    )
}

export default InventoryOrderForm