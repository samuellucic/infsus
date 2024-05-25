import { EstateFormType } from '@/app/lib/formTypes';
import { Controller, UseFormReturn } from 'react-hook-form';
import {
  Button,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
} from '@mui/material';
import styles from './EstateForm.module.css';
import InputTextField from '@/app/components/CoreComponents/InputTextField';
import { useEffect } from 'react';
import { EstateType, Town } from '@/app/lib/types';

export interface EstateFormProps {
  estateForm: UseFormReturn<EstateFormType>;
  estateTypes: EstateType[];
  towns: Town[];
  onSubmit: () => void;
}

const EstateForm = ({
  estateForm,
  estateTypes,
  towns,
  onSubmit,
}: EstateFormProps) => {
  const {
    control,
    getValues,
    register,
    watch,
    trigger,
    formState: { errors },
  } = estateForm;

  const watchedAddress = watch('address');
  const watchedDescription = watch('description');
  const watchedArea = watch('area');
  const watchedPrice = watch('price');
  const watchedEstateType = watch('estateType');

  useEffect(() => {
    trigger('address');
    trigger('description');
    trigger('area');
    trigger('price');
    trigger('estateType');
  }, [
    trigger,
    watchedAddress,
    watchedDescription,
    watchedArea,
    watchedPrice,
    watchedEstateType,
  ]);

  return (
    <form
      className={styles.container}
      onSubmit={(event) => event.preventDefault()}>
      <div className={styles['input-container']}>
        <InputTextField<EstateFormType>
          className={styles.input}
          id="address"
          label="Estate address"
          watch={watch}
          register={register}
          errors={errors}
          required
        />
      </div>
      <div className={styles['input-container']}>
        <InputTextField<EstateFormType>
          className={styles.input}
          id="description"
          label="Estate description"
          watch={watch}
          register={register}
          errors={errors}
          required
        />
      </div>
      <div className={styles['input-container']}>
        <InputTextField<EstateFormType>
          className={styles.input}
          id="area"
          label="Estate area"
          watch={watch}
          register={register}
          errors={errors}
          isNumber
          required
        />
      </div>
      <div className={styles['input-container']}>
        <InputTextField<EstateFormType>
          className={styles.input}
          id="price"
          label="Estate price"
          watch={watch}
          register={register}
          errors={errors}
          isNumber
          required
        />
      </div>
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
                }}>
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
      <FormControl variant="outlined">
        <InputLabel id="estate-type-label">Estate type</InputLabel>
        <Controller
          control={control}
          name={'estateType'}
          rules={{ required: true }}
          render={({ field }) => {
            return (
              <Select
                id="estateType"
                labelId="estate-type-label"
                label="Estate type"
                value={field.value}
                inputRef={field.ref}
                onChange={(val) => {
                  field.onChange(val);
                }}>
                {estateTypes &&
                  estateTypes.map(({ name }) => (
                    <MenuItem key={name} value={name}>
                      {name}
                    </MenuItem>
                  ))}
              </Select>
            );
          }}
        />
      </FormControl>
      <Button
        variant="outlined"
        className={styles.button}
        type="submit"
        disabled={Object.keys(errors).length > 0}
        onClick={onSubmit}>
        Submit
      </Button>
    </form>
  );
};

export default EstateForm;
