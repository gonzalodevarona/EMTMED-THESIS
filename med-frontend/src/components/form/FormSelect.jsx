import { TextField } from "@mui/material";

const FormSelect = ({
    name,
    label,
    control,
    defaultValue,
    register,
    errors,
    children,
    ...props
}) => {


    return (
        <TextField
            select
            fullWidth
            label={label}
            defaultValue={defaultValue}
            inputProps={register(name, { required: `${label} es requerido` })}
            
            error={!!errors[name]}
            helperText={errors[name]?.message}
        >
            {children}
        </TextField>
    );
};
export default FormSelect;