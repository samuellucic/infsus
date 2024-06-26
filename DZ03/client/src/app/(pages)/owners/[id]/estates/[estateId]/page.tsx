'use client';

import { useForm } from 'react-hook-form';
import { EstateFormType, EstateSchema } from '@/app/lib/formTypes';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCallback, useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import {
  getEstate,
  getEstateTypes,
  getTowns,
  updateEstate,
} from '@/app/api/api';
import { EstateType, Town } from '@/app/lib/types';
import styles from './page.module.css';
import EstateForm from '@/app/components/EstateForm/EstateForm';

const EstateDetails = () => {
  const router = useRouter();
  const estateId = parseInt(useParams().estateId as string);
  const ownerId = parseInt(useParams().id as string);
  const [error, setError] = useState<string>('');

  const [estateTypes, setEstateTypes] = useState<EstateType[]>([]);
  const [towns, setTowns] = useState<Town[]>([]);

  const form = useForm<EstateFormType>({
    resolver: zodResolver(EstateSchema),
    defaultValues: {
      address: '',
      description: '',
      price: 0,
      area: 0,
      estateType: '',
      town: 0,
    },
  });
  const { setValue, getValues } = form;

  useEffect(() => {
    getEstate(estateId).then((data) => {
      setValue('address', data.address);
      setValue('description', data.description);
      setValue('price', data.price);
      setValue('area', data.area);
      setValue('estateType', data.estateType.name);
      setValue('town', data.town.id!);
    });
  }, [estateId, setValue]);

  useEffect(() => {
    getTowns().then((data) => setTowns(data));
  }, []);

  useEffect(() => {
    getEstateTypes({ page: 0, size: 1000 }).then((data) =>
      setEstateTypes(data.content)
    );
  }, []);

  const handleSubmit = useCallback(async () => {
    try {
      await updateEstate({
        id: estateId,
        address: getValues('address'),
        description: getValues('description'),
        area: getValues('area'),
        price: getValues('price'),
        estateTypeName: getValues('estateType'),
        townId: getValues('town'),
        ownerId,
      });
      router.push(`/owners/${ownerId}`);
    } catch (error: any) {
      setError(error.response.data);
    }
  }, [getValues, estateId, ownerId, router]);

  return (
    <div className={styles.container}>
      <EstateForm
        estateForm={form}
        estateTypes={estateTypes}
        towns={towns}
        onSubmit={handleSubmit}
      />
      <p>{error}</p>
    </div>
  );
};

export default EstateDetails;
