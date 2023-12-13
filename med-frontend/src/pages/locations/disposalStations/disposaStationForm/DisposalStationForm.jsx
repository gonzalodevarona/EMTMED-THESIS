import { useForm } from "react-hook-form";
import { Button, Stack } from "@mui/material";
import { DevTool } from "@hookform/devtools";

import { useNavigate } from "react-router-dom";
import FormTextfield from '../../../../components/form/FormTextfield';
import DisposalStationService from '../../../../services/disposalStationService'
import triggerInfoAlert from "../../../../components/alerts/InfoAlert";



function DisposalStationForm({ action, preloadedData, id }) {

    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    async function addDisposalStation(disposalStation) {
        return await DisposalStationService.addDisposalStation(disposalStation);
    }

    async function editDisposalStation(disposalStation) {
        disposalStation.id = id;
        return await DisposalStationService.editDisposalStation(disposalStation);
    }

    const entity = 'estación de desechos';

    const {
        handleSubmit,
        register,
        formState: { errors },
        control,
        reset,
    } = useForm({
        defaultValues: preloadedData
    });

    function addedSuccessfully() {
        triggerInfoAlert('success', `La nueva ${entity} ha sido agregada`, reset)
    }
    function errorAdding() {
        triggerInfoAlert('error', `Ha habido un error agregando la nueva ${entity}`)
    }

    function editedSuccessfully() {
        triggerInfoAlert('success', `La ${entity} ha sido editada`, () => redirect(-1))
    }
    function errorEditing() {
        triggerInfoAlert('error', `Ha habido un error editando la ${entity}`)
    }

    function onSubmit(data) {
        data.type = 'disposalStation';
        switch (action) {
            case 'add':
                addDisposalStation(data)
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
                editDisposalStation(data)
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

                    <FormTextfield isRequired label='Nombre' name='name' register={register} errors={errors} />

                    <Button type="submit" variant="contained" color="info">
                        {action === 'add' ? 'Agregar' : 'Editar'}
                    </Button>
                </Stack>
            </form>
            <DevTool control={control} />

        </>
    )
}

export default DisposalStationForm