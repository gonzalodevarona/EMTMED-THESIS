import { TextField } from "@mui/material";

const FormSelect = ({
    name,
    label,
    control,
    defaultValue,
    register,
    errors,
    disabled,
    children,
    ...props
}) => {


    return (
        <TextField
            select
            disabled={disabled}
            fullWidth
            label={label}
            defaultValue={defaultValue}
            inputProps={register(name, { required: `${label} es requerido` })}
            
            error={!!errors[name]}
            helperText={errors[name]?.message}
            {...props}
        >
            {children}
        </TextField>
    );
};
export default FormSelect;