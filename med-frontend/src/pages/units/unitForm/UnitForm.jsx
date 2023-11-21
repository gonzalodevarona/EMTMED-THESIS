import { useForm } from "react-hook-form";
import { Button, Stack } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../components/form/FormTextfield';
import CountingUnitService from "../../../services/countingUnitService";
import WeightUnitService from "../../../services/weightUnitService";
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import { refreshPage } from "../../../utils/CommonMethods";


function UnitForm({ type, action, preloadedData, id }) {

    async function addCountingUnit(countingUnit) {
        await CountingUnitService.addCountingUnit(countingUnit);
    }

    async function addWeightUnit(weightUnit) {
        await WeightUnitService.addWeightUnit(weightUnit);
    }

    async function editCountingUnit(countingUnit) {
        countingUnit.id = id;
        await CountingUnitService.editCountingUnit(countingUnit);
    }

    async function editWeightUnit(weightUnit) {
        weightUnit.id = id;
        await WeightUnitService.editWeightUnit(weightUnit);
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
        triggerInfoAlert('success', `La nueva unidad de ${type} ha sido agregada`, refreshPage)
    }
    function errorAdding() {
        triggerInfoAlert('error', `Ha habido un error agregando la nueva unidad de ${type}`)
    }

    function editedSuccessfully() { 
        triggerInfoAlert('success', `La unidad de ${type} ha sido editada`, refreshPage)
    }
    function errorEditing() {
        triggerInfoAlert('error', `Ha habido un error editando la unidad de ${type}`)
    }

    function onSubmit(data) {
        if (type === 'conteo' && action === 'add') {
            addCountingUnit(data)
                .then((result) => {
                    addedSuccessfully()
                })
                .catch((error) => {
                    errorAdding()
                    console.error('Error en la operación:', error);
                });

        }

        if (type === 'peso' && action === 'add') {
            addWeightUnit(data)
                .then((result) => {
                    addedSuccessfully()
                })
                .catch((error) => {
                    errorAdding()
                    console.error('Error en la operación:', error);
                });
        }
        if (type === 'conteo' && action === 'edit') {
            editCountingUnit(data)
                .then((result) => {
                    editedSuccessfully()
                })
                .catch((error) => {
                    errorEditing()
                    console.error('Error en la operación:', error);
                });

        }

        if (type === 'peso' && action === 'edit') {
            editWeightUnit(data)
                .then((result) => {
                    editedSuccessfully()
                })
                .catch((error) => {
                    errorEditing()
                    console.error('Error en la operación:', error);
                });
        }
    };

    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                <Stack spacing={2} width={400}>

                    <FormTextfield label='Nombre' type='name' register={register} errors={errors} />

                    <Button type="submit" variant="contained" color="info">
                        {action === 'add' ? 'Agregar' : 'Editar'}
                    </Button>
                </Stack>
            </form>
            <DevTool control={control} />

        </>
    )
}

export default UnitForm