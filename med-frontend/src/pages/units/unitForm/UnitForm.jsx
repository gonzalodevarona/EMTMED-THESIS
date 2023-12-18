import { useForm } from "react-hook-form";
import { Stack } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FormTextfield from '../../../components/form/FormTextfield';
import CountingUnitService from "../../../services/countingUnitService";
import WeightUnitService from "../../../services/weightUnitService";
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import { useNavigate } from "react-router-dom";
import FabSubmitButton from "../../../components/buttons/FabSubmitButton";


function UnitForm({ type, action, preloadedData, id }) {

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

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
        reset
    } = useForm({
        defaultValues: preloadedData
    });

    function addedSuccessfully() {
        triggerInfoAlert('success', `La nueva unidad de ${type} ha sido agregada`, reset)
    }
    function errorAdding() {
        triggerInfoAlert('error', `Ha habido un error agregando la nueva unidad de ${type}`)
    }

    function editedSuccessfully() {
        triggerInfoAlert('success', `La unidad de ${type} ha sido editada`, () => redirect(-1))
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
                <Stack spacing={2} width={400} alignItems="center">

                    <FormTextfield isRequired label='Nombre' name='name' register={register} errors={errors} />

                    <FabSubmitButton color='info'/>
                </Stack>
            </form>
            <DevTool control={control} />

        </>
    )
}

export default UnitForm