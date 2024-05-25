'use client';

import { useParams, useRouter } from 'next/navigation';
import { useForm } from 'react-hook-form';
import { EstateTypeFormType, EstateTypeSchema } from '../../../lib/formTypes';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCallback, useEffect } from 'react';
import { getEstateType, updateEstateType } from '@/app/api/api';
import EstateTypeForm from '../../../components/EstateType/EstateTypeForm/EstateTypeForm';
import styles from './page.module.css';

const EstateTypeDetails = () => {
  const router = useRouter();
  const { name }: { name: string } = useParams();

  const form = useForm<EstateTypeFormType>({
    resolver: zodResolver(EstateTypeSchema),
    defaultValues: {
      name: '',
      description: '',
    },
  });
  const { setValue, getValues } = form;

  useEffect(() => {
    getEstateType(name).then((data) => {
      setValue('name', data.name);
      setValue('description', data.description);
    });
  }, [name, setValue]);

  const handleSubmit = useCallback(async () => {
    await updateEstateType({
      name: getValues('name'),
      description: getValues('description'),
    });
    router.push('/estate-types');
  }, [getValues, router]);

  return (
    <div className={styles.container}>
      <EstateTypeForm estateTypeForm={form} onSubmit={handleSubmit} />
    </div>
  );
};

export default EstateTypeDetails;
