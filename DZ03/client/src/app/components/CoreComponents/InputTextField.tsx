import { TextField, TextFieldProps } from '@mui/material';
import {
  FieldErrors,
  FieldValues,
  Path,
  UseFormRegister,
  UseFormWatch,
} from 'react-hook-form';

export type InputTextFieldProps<T extends FieldValues> = {
  id: Path<T>;
  watch: UseFormWatch<T>;
  register: UseFormRegister<T>;
  errors: FieldErrors<T>;
  isNumber?: boolean;
} & TextFieldProps;

const InputTextField = <T extends FieldValues>({
  id,
  label,
  watch,
  register,
  errors,
  disabled,
  isNumber = false,
  ...props
}: InputTextFieldProps<T>) => {
  const errorExist = !!(errors && errors[id]);
  const valueExist = !!watch?.(id);

  return (
    <TextField
      InputLabelProps={{ shrink: valueExist }}
      id={id}
      label={label}
      {...register(id, { valueAsNumber: isNumber })}
      error={errorExist && valueExist}
      helperText={
        errorExist && valueExist ? (errors[id]?.message as string) : ''
      }
      disabled={disabled}
      {...props}
    />
  );
};

export default InputTextField;
