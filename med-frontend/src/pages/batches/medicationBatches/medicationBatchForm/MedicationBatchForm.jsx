import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Button, Stack, MenuItem } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../../components/form/FormTextfield';
import FormSelect from '../../../../components/form/FormSelect';
import FormDatePicker from '../../../../components/form/FormDatePicker';
import triggerInfoAlert from "../../../../components/alerts/InfoAlert";
import BatchStatusService from '../../../../services/batchStatusService';
import MedicationBatchService from '../../../../services/medicationBatchService';
import { refreshPage } from "../../../../utils/CommonMethods";
import { calculateBatchStatus } from "../../../../utils/EntityProcessingMethods";
import dayjs from 'dayjs';

function MedicationBatchForm({ action, preloadedData, id }) {

    const [statuses, setStatuses] = useState([])
    const [locations, setLocations] = useState([])

    useEffect(() => {
        async function fetchStatuses() {
            let data = await BatchStatusService.getBatchStatuses();
            setStatuses(data);
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
        fetchLocations()
    }, [])


    async function addMedicationBatch(medicationBatch) {
        return await MedicationBatchService.addMedicationBatch(medicationBatch);
    }

    async function editMedicationBatch(medicationBatch) {
        medicationBatch.id = id;
        return await MedicationBatchService.editMedicationBatch(medicationBatch);
    }



    const {
        handleSubmit,
        register,
        formState: { errors },
        control,
        watch,
    } = useForm({
        defaultValues: preloadedData
    });

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
        console.log(data)

        // switch (action) {
        //   case 'add':
        //     addMedicine(data)
        //       .then((result) => {
        //         console.log(result)
        //         addedSuccessfully()
        //       })
        //       .catch((error) => {
        //         errorAdding()
        //         console.error('Error en la operación:', error);
        //       });
        //     break;
        //   case 'edit':

        //     editMedicine(data)
        //       .then((result) => {
        //         if (result.status === 500) {
        //           errorEditing()
        //           console.error('Error en la operación:', result);
        //         } else {
        //           editedSuccessfully()
        //         }
        //       })
        //     break;
        //   default:
        //     break;
        // }
    };


    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                <Stack spacing={2} width={400}>

                    <FormTextfield
                        isRequired
                        disabled={action === 'edit' ? true : false}
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
                        disabled={action === 'edit' ? true : false}
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
                        value={action === 'edit' ? preloadedData?.authoredOn : dayjs().add(1, 'day')} />

                    {statuses.length > 0 &&
                        <FormSelect
                            name="status"
                            label="Estado"
                            disabled
                            defaultValue={action === 'edit' ? preloadedData?.status : calculateBatchStatus()}
                            register={register}
                            errors={errors}
                        >

                            {statuses.map(status => <MenuItem key={status} value={status}>
                                {status}
                            </MenuItem>)}
                        </FormSelect>
                    }


                    {locations.length > 0 &&
                        <FormSelect
                            name="location"
                            label="Ubicación"
                            disabled={action === 'edit' ? true : false}
                            defaultValue={action === 'edit' ? findPreloadedLocation(preloadedData?.location.id) : ''}
                            register={register}
                            errors={errors}
                        >

                            {locations.map(location => <MenuItem key={location.id} value={`${location.id} ${location.type}`}>
                                {`${location.id} - ${location.name}`}
                            </MenuItem>)}
                        </FormSelect>
                    }

                    <FormTextfield
                        isRequired
                        disabled={action === 'edit' ? true : false}
                        label='Código Único de Medicamento (CUM)'
                        name='cum'
                        register={register}
                        errors={errors} />



                    <Button type="submit" variant="contained" color="info">
                        {action === 'add' ? 'Agregar' : 'Editar'}
                    </Button>
                </Stack>
            </form>
            <DevTool control={control} />

        </>
    )
}

export default MedicationBatchForm