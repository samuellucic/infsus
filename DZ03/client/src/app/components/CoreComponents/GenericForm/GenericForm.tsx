'use clients';

import styles from './GenericForm.module.css';
import { Controller, FieldValues, Path, UseFormReturn } from 'react-hook-form';
import { Button, TextField } from '@mui/material';
import { DatePicker } from '@mui/x-date-pickers';
import { useState } from 'react';
import dayjs from 'dayjs';

export interface FormItem<T> {
  id: Path<T>;
  type: 'text' | 'date';
  label: string;
}

export type FormType = 'create' | 'update';

export interface GenericFormProps<T extends FieldValues> {
  formItems: FormItem<T>[];
  form: UseFormReturn<T>;
  formType: FormType;
  onSubmit: () => void;
}

const GenericForm = <T extends FieldValues>({
  formItems,
  form,
  formType,
  onSubmit,
}: GenericFormProps<T>) => {
  const [enableUpdate, setEnableUpdate] = useState(false);

  const {
    control,
    register,
    watch,
    formState: { errors },
  } = form;

  return (
    <form
      className={styles.container}
      onSubmit={(event) => event.preventDefault()}>
      {formItems.map(({ id, label, type }) => {
        const errorExist = !!(errors && errors[id]);
        const valueExist = !!watch?.(id);

        return (
          <div key={id} className={styles['input-container']}>
            {type === 'text' && (
              <TextField
                InputLabelProps={{ shrink: valueExist }}
                className={styles.input}
                id={id}
                label={label}
                {...register(id)}
                error={errorExist && valueExist}
                helperText={
                  errorExist && valueExist
                    ? (errors[id]?.message as string)
                    : ''
                }
                disabled={formType === 'update' && !enableUpdate}
              />
            )}
            {type === 'date' && (
              <Controller
                control={control}
                name={id}
                rules={{ required: true }}
                render={({ field }) => {
                  return (
                    <DatePicker
                      className={styles.input}
                      label="Date"
                      value={dayjs(field.value)}
                      inputRef={field.ref}
                      onChange={(date) => {
                        field.onChange(date);
                      }}
                      disabled={formType === 'update' && !enableUpdate}
                    />
                  );
                }}
              />
            )}
          </div>
        );
      })}
      {formType === 'create' && (
        <Button
          variant="outlined"
          className={styles.button}
          type="submit"
          disabled={!!errors.name || !!errors.description}
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
              disabled={!!errors.name || !!errors.description}
              onClick={() => setEnableUpdate((prevState) => !prevState)}>
              Edit
            </Button>
          )}{' '}
          {enableUpdate && (
            <Button
              variant="outlined"
              className={styles.button}
              type="submit"
              disabled={!!errors.name || !!errors.description}
              onClick={onSubmit}>
              Update information
            </Button>
          )}
        </>
      )}
    </form>
  );
};

export default GenericForm;
