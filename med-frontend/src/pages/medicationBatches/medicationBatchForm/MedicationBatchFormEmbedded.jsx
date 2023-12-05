import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Button, Stack, MenuItem, Typography } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import FormDatePicker from '../../../components/form/FormDatePicker';
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import BatchStatusService from '../../../services/batchStatusService';
import MedicationBatchService from '../../../services/medicationBatchService';
import PharmacyService from '../../../services/pharmacyService';
import { refreshPage } from "../../../utils/CommonMethods";
import { calculateBatchStatus } from "../../../utils/EntityProcessingMethods";
import { formatDateToYYYYMMDD, convertToLocalTimeZone, dateArrayToString } from "../../../utils/EntityProcessingMethods"
import dayjs from 'dayjs';

function MedicationBatchEmbeddedForm({ action, addMedicationBatch, deleteMedicationBatch, preloadedData, id }) {

    let statuses;
    const [currentStatus, setCurrentStatus] = useState('')
    const [pharmacies, setPharmacies] = useState([])

    useEffect(() => {
        async function fetchStatuses() {
            let data = await BatchStatusService.getBatchStatuses();
            statuses = data;
        }

        async function fetchPharmacies() {
            try {
                let pharmacies = await PharmacyService.getPharmacies();
                setPharmacies(pharmacies.map(pharmacy => ({ id: pharmacy.id, name: pharmacy.name, type: pharmacy.type })));


            } catch (error) {
                console.error("Error fetching pharmacies:", error);
            }
        }


        fetchStatuses()
        fetchPharmacies()
    }, [])

    async function editMedicationBatch(medicationBatch) {
        medicationBatch.id = id;
        return await MedicationBatchService.editMedicationBatch(medicationBatch);
    }

    useEffect(() => {
        console.log(preloadedData)
    }, [preloadedData])


    const {
        handleSubmit,
        register,
        formState: { errors },
        control,
        watch
    } = useForm({
        defaultValues: preloadedData
    });

    const expirationDateWatch = watch('expirationDate')

    useEffect(() => {

        if (action === 'add') {
            if (expirationDateWatch) {
                setCurrentStatus(calculateBatchStatus(expirationDateWatch.toDate()))
            }
        } else {
            if (expirationDateWatch) {
                let arrayDate = Object.values(expirationDateWatch)
                let dateObj = new Date(arrayDate[0], arrayDate[1] - 1, arrayDate[2])
                setCurrentStatus(calculateBatchStatus(dateObj))
            }

        }
    }, [expirationDateWatch])



    function addedSuccessfully() {
        triggerInfoAlert('success', 'El nuevo medicamento ha sido agregado', refreshPage)
    }
    function errorAdding() {
        triggerInfoAlert('error', 'Ha habido un error agregando el nuevo medicamento')
    }

    function editedSuccessfully() {
        triggerInfoAlert('success', 'El medicamento ha sido editada', refreshPage)
    }
    function errorEditing() {
        triggerInfoAlert('error', 'Ha habido un error editando el medicamento')
    }

    async function onSubmit(data) {
        
        data.status = currentStatus;
        if (action === 'add') {
            data.location = findPreloadedLocation(data.location, 'object');
            data.expirationDate = formatDateToYYYYMMDD(convertToLocalTimeZone(data.expirationDate.toISOString()))
        } else{
            data.expirationDate = `${data.expirationDate[0]}-${data.expirationDate[1]}-${data.expirationDate[2]}`
        }
        
        addMedicationBatch(id, data)

    };

    function findPreloadedLocation(preloadedLocationId, objectOrString) {
        if (typeof preloadedLocationId !== 'number') {
            let parts = preloadedLocationId.split(' ');
            preloadedLocationId = Number(parts[0]);
        }


        let foundLocation = pharmacies.find(pharmacy => pharmacy.id === preloadedLocationId)
        let foundLocationCopy = { id: foundLocation.id, type: foundLocation.type };

        if (objectOrString === 'object') {
            return foundLocationCopy
        } else {
            return `${foundLocationCopy.id} ${foundLocationCopy.type}`
        }


    }

    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                <Stack spacing={2} width={400}>

                    <FormTextfield
                        isRequired
                        label='Fabricante'
                        name='manufacturer'
                        register={register}
                        errors={errors} />

                    <FormTextfield
                        isRequired
                        disabled={action === 'edit' ? true : false}
                        label='Vía de Administración'
                        name='administrationRoute'
                        register={register}
                        errors={errors} />

                    <FormTextfield
                        isRequired
                        type='number'
                        label='Cantidad'
                        min={0}
                        name='quantity'
                        register={register}
                        errors={errors} />

                    <FormDatePicker
                        disablePast
                        label='Fecha de Expiración'
                        disabled={action === 'edit' ? true : false}
                        name='expirationDate'
                        control={control}
                        errors={errors}
                        minDate={dayjs().add(1, 'day')}
                        value={action === 'edit' ? preloadedData?.expirationDate : dayjs().add(1, 'day')} />

                    {currentStatus !== '' && < Typography sx={{ backgroundColor: 'black', color: currentStatus.toLowerCase() }} > Semaforización: {currentStatus}</Typography>}


                    {pharmacies.length > 0 &&
                        <FormSelect
                            name="location"
                            label="Ubicación"
                            disabled={action === 'edit' ? true : false}
                            defaultValue={action === 'edit' ? findPreloadedLocation(preloadedData?.location.id, '') : `${pharmacies[0].id} ${pharmacies[0].type}`}
                            register={register}
                            errors={errors}
                        >

                            {pharmacies.map(pharmacy => <MenuItem key={pharmacy.id} value={`${pharmacy.id} ${pharmacy.type}`}>
                                {`${pharmacy.id} - ${pharmacy.name}`}
                            </MenuItem>)}
                        </FormSelect>
                    }

                    <FormTextfield
                        isRequired
                        label='Código Único de Medicamento (CUM)'
                        name='cum'
                        register={register}
                        errors={errors} />



                    <Button type="submit" variant="contained" color="info">
                        {action === 'add' ? 'Agregar' : 'Editar'}
                    </Button>
                    {deleteMedicationBatch && <Button onClick={() => deleteMedicationBatch(id)} variant="contained" color="error">
                        Eliminar
                    </Button>}
                </Stack>
            </form >
            <DevTool control={control} />

        </>
    )
}

export default MedicationBatchEmbeddedForm