import { useForm } from "react-hook-form";
import { Button, Stack } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import Swal from 'sweetalert2'
import FormTextfield from '../../../components/form/FormTextfield';
import CountingUnitService from "../../../services/countingUnitService";
import WeightUnitService from "../../../services/weightUnitService";


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
        Swal.fire({
            icon: 'success',
            title: `La nueva unidad de ${type} ha sido agregada`,
            showConfirmButton: true,
        }).then((result) => {
            location.reload();
        });
    }
    function errorAdding() {
        Swal.fire({
            icon: 'error',
            title: `Ha habido un error agregando la nueva unidad de ${type}`,
            showConfirmButton: true,
        })
    }
    function editedSuccessfully() {
        Swal.fire({
            icon: 'success',
            title: `La unidad de ${type} ha sido editada`,
            showConfirmButton: true,
        }).then((result) => {
            location.reload();
        });
    }
    function errorEditing() {
        Swal.fire({
            icon: 'error',
            title: `Ha habido un error editando la unidad de ${type}`,
            showConfirmButton: true,
        })
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