import React from 'react'
import Header from '../../components/Header'
import { useForm } from "react-hook-form";
import { Button, Stack } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import { useParams } from "react-router-dom";
import FormTextfield from '../../components/form/FormTextfield';


function UnitForm({ type, action }) {

    let { id } = useParams();

    const {
        handleSubmit,
        register,
        formState: { errors },
        control,
    } = useForm({
        defaultValues: {
            name: "",
        },
    });

    const onSubmit = (data) => {
        console.log(data);
    };


    return (
        <>
            {action === 'add' ? 
            <Header title={`Agregar una unidad de ${type}`} /> : 
            <Header title={`Editar la unidad de ${type} #${id}`} />}


            <form onSubmit={handleSubmit(onSubmit)} noValidate>
                <Stack spacing={2} width={400}>

                    <FormTextfield label='Nombre' type='name' register={register} errors={errors} />

                    <Button type="submit" variant="contained" color="info">
                        Agregar
                    </Button>
                </Stack>
            </form>
            <DevTool control={control} />

        </>
    )
}

export default UnitForm