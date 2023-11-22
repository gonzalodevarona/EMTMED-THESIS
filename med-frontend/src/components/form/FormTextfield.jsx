import { TextField } from '@mui/material'

function FormTextfield({label, name, register, errors}) {

    return (
        <TextField
            label={label}
            type={name}
            {...register(name, { required: `${label} es requerido` })}
            error={!!errors[name]}
            helperText={errors[name]?.message}
        />
    )
}

export default FormTextfield