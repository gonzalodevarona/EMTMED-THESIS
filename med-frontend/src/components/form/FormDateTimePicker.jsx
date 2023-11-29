import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import { Controller } from "react-hook-form"


const FormDateTimePicker = ({
    name,
    label,
    control,
    defaultValue,
    errors,
    disabled,
    children,
    disablePast,
    disableFuture,
    value,
    ...props
}) => {


    return (




        <Controller
            control={control}
            name={name}
            rules={{ required: true }}
            render={({ field }) => {
                return (
                    <LocalizationProvider dateAdapter={AdapterDayjs}>

                        <DateTimePicker
                            fullWidth
                            disabled={disabled}
                            disablePast={disablePast}
                            disableFuture={disableFuture}
                            label={label}
                            error={!!errors[name]}
                            value={field.value ? field.value : value}
                            inputRef={field.ref}
                            onChange={(date) => {
                                field.onChange(date);
                            }}
                            helperText={errors[name]?.message} 
                            />
                    </LocalizationProvider>
                );
            }}
        />
    );
};
export default FormDateTimePicker;



