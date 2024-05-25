'use client';

import EstateTypeForm from '../../../components/EstateType/EstateTypeForm/EstateTypeForm';
import { useForm } from 'react-hook-form';
import { EstateTypeFormType, EstateTypeSchema } from '@/app/lib/formTypes';
import { useCallback } from 'react';
import styles from './page.module.css';
import { zodResolver } from '@hookform/resolvers/zod';
import { useRouter } from 'next/navigation';
import { createEstateType } from '@/app/api/api';

const EstateTypeCreatePage = () => {
  const router = useRouter();

  const form = useForm<EstateTypeFormType>({
    resolver: zodResolver(EstateTypeSchema),
    defaultValues: {
      name: '',
      description: '',
    },
  });

  const { getValues } = form;

  const handleSubmit = useCallback(async () => {
    // TODO RESPONSE AND ERROR
    const res = await createEstateType({
      name: getValues('name'),
      description: getValues('description'),
    });

    router.push('/estate-types');
  }, [router, getValues]);

  return (
    <div className={styles.container}>
      <EstateTypeForm estateTypeForm={form} onSubmit={handleSubmit} />
    </div>
  );
};

export default EstateTypeCreatePage;
