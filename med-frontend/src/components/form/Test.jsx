import { useForm } from "react-hook-form";
import { TextField, Button, Stack } from "@mui/material";
import { DevTool } from "@hookform/devtools";
import FormTextfield from "./FormTextfield";


let renderCount = 0;

export default function LoginForm (){
  const {
    handleSubmit,
    register,
    formState: { errors },
    control,
  } = useForm({
    defaultValues: {
      email: "",
      password: "",
      name: "",
    },
  });

  const onSubmit = (data) => {
    console.log(data);
  };

  renderCount++;

  return (
    <>
      <h1>Login ({renderCount / 2})</h1>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <Stack spacing={2} width={400}>
          <TextField
            label="Email"
            type="email"
            {...register("email", { required: "Email is required" })}
            error={!!errors.email}
            helperText={errors.email?.message}
          />
          <TextField
            label="Password"
            type="password"
            {...register("password", { required: "Password is required" })}
            error={!!errors.password}
            helperText={errors.password?.message}
          />
          <FormTextfield label='name' type='name' register={register} errors={errors}/>

          <Button type="submit" variant="contained" color="primary">
            Login
          </Button>
        </Stack>
      </form>
      <DevTool control={control} />
    </>
  );
};