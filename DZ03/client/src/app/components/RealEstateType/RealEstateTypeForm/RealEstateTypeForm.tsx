import { RealEstateTypeFormType } from '@/app/lib/formTypes';
import { useEffect, useState } from 'react';
import { UseFormReturn } from 'react-hook-form';
import { Button, TextField } from '@mui/material';
import {
  checkDescriptionRequirements,
  checkNameRequirements,
  descriptionRequirements,
  nameRequirements,
  RealEstateTypeDescriptionRequirementsType,
  RealEstateTypeNameRequirementsType,
  RealEstateTypeRequirements,
} from '../../../utils/realEstateTypeValidation';
import styles from './RealEstateTypeForm.module.css';
import FormRequirementLabel from '../../CoreComponents/FormRequirementLabel/FormRequirementLabel';

export interface RealEstateTypeFormProps {
  realEstateTypeForm: UseFormReturn<RealEstateTypeFormType>;
  onSubmit: () => void;
}

const RealEstateTypeForm = ({
  realEstateTypeForm,
  onSubmit,
}: RealEstateTypeFormProps) => {
  const {
    trigger,
    register,
    watch,
    formState: { errors },
  } = realEstateTypeForm;

  const watchedName = watch('name');
  const watchedDescription = watch('description');

  const [nameValidationResult, setNameValidationResult] = useState<
    Record<keyof RealEstateTypeRequirements, boolean>
  >({});
  const [descriptionValidationResult, setDescriptionValidationResult] =
    useState<Record<keyof RealEstateTypeRequirements, boolean>>({});

  useEffect(() => {
    setNameValidationResult(checkNameRequirements(watchedName));
    trigger('name');
  }, [trigger, watchedName]);
  useEffect(() => {
    setDescriptionValidationResult(
      checkDescriptionRequirements(watchedDescription)
    );
    trigger('description');
  }, [trigger, watchedDescription]);

  return (
    <form
      className={styles.container}
      onSubmit={(event) => event.preventDefault()}>
      <div className={styles['input-container']}>
        <TextField
          className={styles.input}
          id="name"
          label="Enter the name of the real estate type"
          {...register('name')}
          error={!!watch?.('name') && !!errors.name}
          helperText={errors.name?.message}
        />
        <div className={styles.error}>
          {Object.entries(nameValidationResult).map(([key, value]) => (
            <FormRequirementLabel
              key={key}
              label={
                nameRequirements[key as RealEstateTypeNameRequirementsType]
                  .message
              }
              valid={value}
            />
          ))}
        </div>
      </div>
      <div className={styles['input-container']}>
        <TextField
          className={styles.input}
          id="description"
          label="Enter the description of the real estate type"
          {...register('description')}
          error={!!watch?.('description') && !!errors.description}
          helperText={errors.description?.message}
        />
        <div className={styles.error}>
          {Object.entries(descriptionValidationResult).map(([key, value]) => (
            <FormRequirementLabel
              key={key}
              label={
                descriptionRequirements[
                  key as RealEstateTypeDescriptionRequirementsType
                ].message
              }
              valid={value}
            />
          ))}
        </div>
      </div>
      <Button
        variant="outlined"
        className={styles.button}
        type="submit"
        disabled={!!errors.name || !!errors.description}
        onClick={onSubmit}>
        Submit
      </Button>
    </form>
  );
};

export default RealEstateTypeForm;
