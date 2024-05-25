import { Controller, UseFormReturn } from 'react-hook-form';
import { OwnerFormType } from '../../lib/formTypes';
import { FormType } from '../CoreComponents/GenericForm/GenericForm';
import { useCallback, useState } from 'react';
import InputTextField from '../CoreComponents/InputTextField';
import { DatePicker } from '@mui/x-date-pickers';
import dayjs from 'dayjs';
import {
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
} from '@mui/material';
import { Town } from '../../lib/types';
import styles from './OwnerForm.module.css';

export interface OwnerFormProps {
  ownerForm: UseFormReturn<OwnerFormType>;
  formType: FormType;
  towns: Town[];
  onSubmit: () => void;
}

const OwnerForm = ({
  ownerForm,
  formType,
  towns,
  onSubmit,
}: OwnerFormProps) => {
  const [enableUpdate, setEnableUpdate] = useState(false);
  const disabled = formType === 'update' && !enableUpdate;

  const {
    control,
    register,
    watch,
    getValues,
    formState: { errors },
  } = ownerForm;

  const handleUpdate = useCallback(() => {
    onSubmit();
    setEnableUpdate((prevState) => !prevState);
  }, [onSubmit]);

  return (
    <form
      className={styles.container}
      onSubmit={(event) => event.preventDefault()}>
      <div className={styles['input-container']}>
        <InputTextField<OwnerFormType>
          id="name"
          label="Owner's name"
          watch={watch}
          register={register}
          errors={errors}
          disabled={disabled}
        />
        <InputTextField<OwnerFormType>
          id="surname"
          label="Owner's surname"
          watch={watch}
          register={register}
          errors={errors}
          disabled={disabled}
        />
        <Controller
          control={control}
          name={'birthDate'}
          rules={{ required: true }}
          render={({ field }) => {
            return (
              <DatePicker
                className={styles.input}
                label="Owner's birth date"
                value={dayjs(field.value)}
                inputRef={field.ref}
                onChange={(date) => {
                  field.onChange(date);
                }}
                disabled={disabled}
              />
            );
          }}
        />
        <InputTextField<OwnerFormType>
          id="address"
          label="Owner's address"
          watch={watch}
          register={register}
          errors={errors}
          disabled={disabled}
        />
        <InputTextField<OwnerFormType>
          id="email"
          label="Owner's email"
          watch={watch}
          register={register}
          errors={errors}
          disabled={disabled}
        />
        <FormControl variant="outlined">
          <InputLabel id="town-label">Town</InputLabel>
          <Controller
            control={control}
            name={'town'}
            rules={{ required: true }}
            render={({ field }) => {
              return (
                <Select
                  id="town"
                  labelId="town-label"
                  label="Town"
                  value={`${field.value}`}
                  inputRef={field.ref}
                  onChange={(val) => {
                    field.onChange(val);
                  }}
                  disabled={disabled}>
                  {towns &&
                    towns.map(({ id, name }) => (
                      <MenuItem key={id} value={id}>
                        {name}
                      </MenuItem>
                    ))}
                </Select>
              );
            }}
          />
        </FormControl>
      </div>
      {formType === 'create' && (
        <Button
          variant="outlined"
          className={styles.button}
          type="submit"
          disabled={Object.keys(errors).length > 0}
          onClick={onSubmit}>
          Submit
        </Button>
      )}
      {formType === 'update' && (
        <>
          {!enableUpdate && (
            <Button
              variant="outlined"
              className={styles.button}
              type="submit"
              disabled={Object.keys(errors).length > 0}
              onClick={() => setEnableUpdate((prevState) => !prevState)}>
              Edit
            </Button>
          )}
          {enableUpdate && (
            <Button
              variant="outlined"
              className={styles.button}
              type="submit"
              disabled={Object.keys(errors).length > 0}
              onClick={handleUpdate}>
              Update information
            </Button>
          )}
        </>
      )}
    </form>
  );
};

export default OwnerForm;
