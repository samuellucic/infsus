'use client';

import RealEstateTypeForm from '../../components/RealEstateTypeForm/RealEstateTypeForm';
import { useForm } from 'react-hook-form';
import { RealEstateTypeFormType, RealEstateTypeSchema } from '../../lib/formTypes';
import { useCallback } from 'react';
import styles from './page.module.css';
import { zodResolver } from '@hookform/resolvers/zod';
import { saveRealEstateType } from '../../api/api';

const RealEstateTypePage = () => {
  const form = useForm<RealEstateTypeFormType>({
    resolver: zodResolver(RealEstateTypeSchema),
    defaultValues: {
      name: '',
      description: '',
    },
  });

  const { getValues } = form;

  const handleSubmit = useCallback(async () => {
    // TODO RESPONSE AND ERROR
    const res = await saveRealEstateType({
      name: getValues('name'),
      description: getValues('description'),
    });
  }, [getValues]);

  return (
    <div className={styles.container}>
      <RealEstateTypeForm realEstateTypeForm={form} onSubmit={handleSubmit} />
    </div>
  );
};

export default RealEstateTypePage;
