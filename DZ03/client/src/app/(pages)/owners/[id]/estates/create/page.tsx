'use client';

import { useForm } from 'react-hook-form';
import { EstateFormType, EstateSchema } from '@/app/lib/formTypes';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCallback, useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { createEstate, getEstateTypes, getTowns } from '@/app/api/api';
import { EstateType, Town } from '@/app/lib/types';
import styles from './page.module.css';
import EstateForm from '@/app/components/EstateForm/EstateForm';

const EstateDetails = () => {
  const router = useRouter();
  const ownerId = parseInt(useParams().id as string);

  const [estateTypes, setEstateTypes] = useState<EstateType[]>([]);
  const [towns, setTowns] = useState<Town[]>([]);

  const form = useForm<EstateFormType>({
    resolver: zodResolver(EstateSchema),
    defaultValues: {
      address: '',
      description: '',
      price: undefined,
      area: undefined,
      estateType: '',
    },
  });
  const { getValues } = form;

  useEffect(() => {
    getTowns().then((data) => setTowns(data));
  }, []);

  useEffect(() => {
    getEstateTypes({ page: 0, size: 1000 }).then((data) =>
      setEstateTypes(data.content)
    );
  }, []);

  const handleSubmit = useCallback(async () => {
    await createEstate({
      address: getValues('address'),
      description: getValues('description'),
      area: getValues('area'),
      price: getValues('price'),
      estateTypeName: getValues('estateType'),
      townId: getValues('town'),
      ownerId,
    });
    router.push(`/owners/${ownerId}`);
  }, [getValues, router, ownerId]);

  return (
    <div className={styles.container}>
      <EstateForm
        estateForm={form}
        estateTypes={estateTypes}
        towns={towns}
        onSubmit={handleSubmit}
      />
    </div>
  );
};

export default EstateDetails;
