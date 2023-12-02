import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { Controller } from "react-hook-form"
import { convertDateObjectToDayjs } from '../../utils/EntityProcessingMethods';

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

                        <DatePicker
                            fullWidth
                            disabled={disabled}
                            disablePast={disablePast}
                            disableFuture={disableFuture}
                            {...props}
                            label={label}
                            error={!!errors[name]}
                            value={field.value ? convertDateObjectToDayjs(field.value) : value}
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



