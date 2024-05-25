'use client';

import { useForm } from 'react-hook-form';
import { OwnerFormType, OwnerSchema } from '@/app/lib/formTypes';
import { zodResolver } from '@hookform/resolvers/zod';
import OwnerForm from '../../../components/OwnerForm/OwnerForm';
import { useCallback, useEffect, useState } from 'react';
import { useParams, useRouter } from 'next/navigation';
import {
  deleteEstate,
  getEstatesOwnedByOwner,
  getOwner,
  getTowns,
  updateOwner,
} from '@/app/api/api';
import { Pageable, Town } from '@/app/lib/types';
import GenericTable from '../../../components/CoreComponents/GenericTable/GenericTable';
import styles from './page.module.css';
import { Button } from '@mui/material';
import Link from 'next/link';
import { estateTableColumns } from '@/app/utils/estateConstants';

const OwnerDetails = () => {
  const router = useRouter();
  const id = parseInt(useParams().id as string);
  const [error, setError] = useState<string>('');

  const [towns, setTowns] = useState<Town[]>([]);

  const form = useForm<OwnerFormType>({
    resolver: zodResolver(OwnerSchema),
    defaultValues: {
      name: '',
      surname: '',
      address: '',
      birthDate: '',
      email: '',
      town: 0,
    },
  });
  const { setValue, getValues } = form;

  useEffect(() => {
    getOwner(id).then((data) => {
      setValue('name', data.name);
      setValue('surname', data.surname);
      setValue('email', data.email);
      setValue('birthDate', data.birthDate);
      setValue('address', data.address);
      setValue('town', data.townId);
    });
  }, [id, setValue]);

  useEffect(() => {
    getTowns().then((data) => setTowns(data));
  }, []);

  const fetchEstates = useCallback(
    async (pageable: Pageable) => await getEstatesOwnedByOwner(pageable, id),
    [id]
  );

  const handleSubmit = useCallback(async () => {
    try {
      await updateOwner({
        id,
        name: getValues('name'),
        surname: getValues('surname'),
        email: getValues('email'),
        birthDate: getValues('birthDate'),
        address: getValues('address'),
        townId: getValues('town'),
      });
    } catch (error: any) {
      setError(error.response.data);
    }
  }, [getValues, id]);

  const handleRowClick = useCallback(
    async (estateId: number) =>
      router.push(`/owners/${id}/estates/${estateId}`),
    [id, router]
  );

  const handleItemDelete = useCallback(
    async (estateId: number) => {
      await deleteEstate(id, estateId);
    },
    [id]
  );

  return (
    <div className={styles.container}>
      <OwnerForm
        ownerForm={form}
        formType={'update'}
        onSubmit={handleSubmit}
        towns={towns}
      />
      <div className={styles['detail-container']}>
        <GenericTable
          columns={estateTableColumns}
          fetchData={fetchEstates}
          onRowClick={handleRowClick}
          onItemDelete={handleItemDelete}
        />
        <Link href={`/owners/${id}/estates/create`}>
          <Button variant="outlined">Add a new estate</Button>
        </Link>
      </div>
      <p>{error}</p>
    </div>
  );
};

export default OwnerDetails;
