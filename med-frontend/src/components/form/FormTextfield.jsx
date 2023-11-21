import { TextField } from '@mui/material'

function FormTextfield({label, type, register, errors}) {

    return (
        <TextField
            label={label}
            type={type}
            {...register(type, { required: `${label} es requerido` })}
            error={!!errors[type]}
            helperText={errors[type]?.message}
        />
    )
}

export default FormTextfield