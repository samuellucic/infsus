import { EstateTypeFormType } from '@/app/lib/formTypes';
import { UseFormReturn } from 'react-hook-form';
import { Button } from '@mui/material';
import styles from './EstateTypeForm.module.css';
import InputTextField from '@/app/components/CoreComponents/InputTextField';
import { useEffect } from 'react';

export interface EstateTypeFormProps {
  estateTypeForm: UseFormReturn<EstateTypeFormType>;
  onSubmit: () => void;
}

const EstateTypeForm = ({ estateTypeForm, onSubmit }: EstateTypeFormProps) => {
  const {
    register,
    watch,
    trigger,
    formState: { errors },
  } = estateTypeForm;

  const watchedName = watch('name');
  const watchedDescription = watch('description');

  useEffect(() => {
    trigger('name');
    trigger('description');
  }, [trigger, watchedName, watchedDescription]);

  return (
    <form
      className={styles.container}
      onSubmit={(event) => event.preventDefault()}>
      <div className={styles['input-container']}>
        <InputTextField<EstateTypeFormType>
          className={styles.input}
          id="name"
          label="Estate type name"
          watch={watch}
          register={register}
          errors={errors}
          required
        />
      </div>
      <div className={styles['input-container']}>
        <InputTextField<EstateTypeFormType>
          className={styles.input}
          id="description"
          label="Estate type description"
          watch={watch}
          register={register}
          errors={errors}
          required
        />
      </div>
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

export default EstateTypeForm;
