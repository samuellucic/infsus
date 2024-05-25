'use client';

import { useForm } from 'react-hook-form';
import { EstateSchema, OwnerFormType } from '@/app/lib/formTypes';
import { zodResolver } from '@hookform/resolvers/zod';
import OwnerForm from '../../../components/OwnerForm/OwnerForm';
import { useCallback, useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { createOwner, getTowns } from '@/app/api/api';
import { Town } from '@/app/lib/types';
import styles from './page.module.css';

const OwnerDetails = () => {
  const router = useRouter();
  const id = parseInt(useParams().id as string);

  const [towns, setTowns] = useState<Town[]>([]);

  const form = useForm<OwnerFormType>({
    resolver: zodResolver(EstateSchema),
    defaultValues: {
      name: '',
      surname: '',
      address: '',
      birthDate: '',
      email: '',
      town: undefined,
    },
  });
  const { getValues } = form;

  useEffect(() => {
    getTowns().then((data) => setTowns(data));
  }, []);

  const handleSubmit = useCallback(async () => {
    await createOwner({
      id,
      name: getValues('name'),
      surname: getValues('surname'),
      email: getValues('email'),
      birthDate: getValues('birthDate'),
      address: getValues('address'),
      townId: getValues('town'),
    });
    router.push('/owners');
  }, [getValues, id, router]);

  return (
    <div className={styles.container}>
      <OwnerForm
        ownerForm={form}
        formType={'create'}
        onSubmit={handleSubmit}
        towns={towns}
      />
    </div>
  );
};

export default OwnerDetails;
