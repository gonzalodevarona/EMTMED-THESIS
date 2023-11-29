import { TextField } from '@mui/material'

function FormTextfield({label, name, register, errors, isRequired, ...props}) {

    return (
        <TextField
        
            label={label}
            type={name}
            {...register(name, isRequired?{ required: `${label} es requerido` }: undefined)}
            error={!!errors[name]}
            helperText={errors[name]?.message}
            {...props}
        />
    )
}

export default FormTextfield