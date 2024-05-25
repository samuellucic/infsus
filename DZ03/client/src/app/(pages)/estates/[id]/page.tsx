'use client';

import { useForm } from 'react-hook-form';
import { EstateFormType, EstateSchema } from '@/app/lib/formTypes';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCallback, useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import { getEstate, getEstateTypes, updateEstate } from '@/app/api/api';
import { EstateType } from '@/app/lib/types';
import styles from './page.module.css';
import EstateForm from '@/app/components/EstateForm/EstateForm';

const EstateDetails = () => {
  const router = useRouter();
  const id = parseInt(useParams().id as string);

  const [estateTypes, setEstateTypes] = useState<EstateType[]>([]);

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
  const { setValue, getValues } = form;

  useEffect(() => {
    getEstate(id).then((data) => {
      setValue('address', data.address);
      setValue('description', data.description);
      setValue('price', data.price);
      setValue('area', data.area);
      setValue('estateType', data.estateType);
    });
  }, [id, setValue]);

  useEffect(() => {
    getEstateTypes({ page: 0, size: 1000 }).then((data) =>
      setEstateTypes(data.content)
    );
  }, []);

  const handleSubmit = useCallback(async () => {
    await updateEstate({
      id,
      address: getValues('address'),
      description: getValues('description'),
      area: getValues('area'),
      price: getValues('price'),
      estateType: getValues('estateType'),
    });
  }, [getValues, id]);

  return (
    <div className={styles.container}>
      <EstateForm
        estateForm={form}
        estateTypes={estateTypes}
        onSubmit={handleSubmit}
      />
    </div>
  );
};

export default EstateDetails;
