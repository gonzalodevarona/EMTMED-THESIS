import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Stack, MenuItem, Typography, Box } from "@mui/material";
import CheckIcon from '@mui/icons-material/Check';
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import Header from '../../../components/Header';
import FormDatePicker from '../../../components/form/FormDatePicker';
import { Delete } from '@mui/icons-material';
import BatchStatusService from '../../../services/batchStatusService';
import MedicationBatchService from '../../../services/medicationBatchService';
import PharmacyService from '../../../services/pharmacyService';
import { calculateBatchStatus } from "../../../utils/EntityProcessingMethods";
import { formatDateToYYYYMMDD, convertToLocalTimeZone } from "../../../utils/EntityProcessingMethods"
import dayjs from 'dayjs';
import FabSubmitButton from "../../../components/buttons/FabSubmitButton";
import FabActionButton from "../../../components/buttons/FabActionButton";
import { useTranslation } from 'react-i18next';

function BatchEmbeddedForm({ action, addBatch, deleteBatch, preloadedData, id }) {
    const { t } = useTranslation();
    let statuses;
    const [currentStatus, setCurrentStatus] = useState('')
    const [pharmacies, setPharmacies] = useState([])
    const [submitted, setSubmitted] = useState(false)

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

    useEffect(() => {
        console.log(preloadedData)
    }, [preloadedData,id,action])



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
            console.log(expirationDateWatch)
            if (expirationDateWatch) {
                let arrayDate = Object.values(expirationDateWatch)
                let dateObj = new Date(arrayDate[0], arrayDate[1] - 1, arrayDate[2])
                setCurrentStatus(calculateBatchStatus(dateObj))
            }

        }
    }, [expirationDateWatch])

    async function onSubmit(data) {

        data.status = currentStatus;
        if (action === 'add') {
            data.expirationDate = formatDateToYYYYMMDD(convertToLocalTimeZone(data.expirationDate.toISOString()))
        } else {
            data.quantity = Number(data.quantity)
            data.expirationDate = `${data.expirationDate[0]}-${data.expirationDate[1]}-${data.expirationDate[2]}`
        }

        addBatch(id, data)
        setSubmitted(true)

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
            <form onChange={() => setSubmitted(false)} onSubmit={handleSubmit(onSubmit)} noValidate>
                <Stack spacing={2} width={400} >
                    <Header title={`Lote ${id}`} />

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

                    {currentStatus !== '' && < Typography sx={{ backgroundColor: 'black', color: currentStatus.toLowerCase() }} > Semaforización: {t(`batch.status.${currentStatus}`)}</Typography>}
                    {pharmacies.length > 0 && action === 'edit' &&
                        <FormSelect
                            name="location"
                            required
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

                    <Stack direction="row" spacing={2} justifyContent='center' alignItems='center'>
                        {submitted && <CheckIcon color="secondary" />}
                        <FabSubmitButton color='info' />
                        {deleteBatch && <FabActionButton handleClick={() => deleteBatch(id)} color="error" icon={<Delete />} />}
                    </Stack>


                </Stack>
            </form >
            <DevTool control={control} />

        </>
    )
}

export default BatchEmbeddedForm