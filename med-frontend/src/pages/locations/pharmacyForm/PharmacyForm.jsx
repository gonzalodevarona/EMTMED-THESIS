import { useState, useEffect } from "react";
import { useForm } from "react-hook-form";
import { Button, Stack, MenuItem } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FabSubmitButton from '../../../components/buttons/FabSubmitButton';
import FormTextfield from '../../../components/form/FormTextfield';
import FormSelect from '../../../components/form/FormSelect';
import PharmacyService from "../../../services/pharmacyService";
import triggerInfoAlert from "../../../components/alerts/InfoAlert";
import { useNavigate } from "react-router-dom";
import { useTranslation } from 'react-i18next';

function PharmacyForm({ action, preloadedData, id }) {

    const { t } = useTranslation();
    const navigate = useNavigate();

    const redirect = (path) => {
        navigate(path);
    };

    const [categories, setCategories] = useState([])

    useEffect(() => {
        async function fetchCategories() {
            let data = await PharmacyService.getPharmacyCategories();

            setCategories(data.filter(item => item !== "WAREHOUSE" && item !== "PRINCIPAL"));
        }

        fetchCategories()
    }, [])


    async function addPharmacy(pharmacy) {
        return await PharmacyService.addPharmacy(pharmacy);
    }

    async function editPharmacy(pharmacy) {
        pharmacy.id = id;
        return await PharmacyService.editPharmacy(pharmacy);
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
        triggerInfoAlert('success', 'La nueva farmacia ha sido agregada', reset)
    }
    function errorAdding() {
        triggerInfoAlert('error', 'Ha habido un error agregando la nueva farmacia')
    }

    function editedSuccessfully() {
        triggerInfoAlert('success', 'La farmacia ha sido editada', () => redirect(-1))
    }
    function errorEditing() {
        triggerInfoAlert('error', 'Ha habido un error editando la farmacia')
    }

    function onSubmit(data) {
        data.type = 'pharmacy';
        switch (action) {
            case 'add':
                addPharmacy(data)
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
                editPharmacy(data)
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
                <Stack spacing={2} width={400} alignItems='center'>

                    <FormTextfield isRequired label='Nombre' name='name' register={register} errors={errors} />

                    {categories.length > 0 &&
                        <FormSelect
                            name="category"
                            label="Categoría"
                            defaultValue={action === 'edit' ? preloadedData?.category : categories[1]}
                            register={register}
                            errors={errors}
                        >

                            {categories.map(category => <MenuItem key={category} value={category}>
                                {t(`pharmacy.category.${category}`)}
                            </MenuItem>)}
                        </FormSelect>}

                    <FabSubmitButton color='info' />
                </Stack>
            </form>
            <DevTool control={control} />

        </>
    )
}

export default PharmacyForm