'use client';

import { useCallback } from 'react';
import { Column, EstateOwner } from '../../lib/types';
import { useRouter } from 'next/navigation';
import { deleteOwner, getOwners } from '../../api/api';
import GenericTable from '../../components/CoreComponents/GenericTable/GenericTable';
import { Button } from '@mui/material';
import Link from 'next/link';

const params = {
  page: 0,
  size: 10,
};

const columns: Column<EstateOwner>[] = [
  {
    id: 'name',
    label: 'First name',
    align: 'center',
    minWidth: 50,
    format: (a: any) => a,
  },
  {
    id: 'surname',
    label: 'Last name',
    align: 'center',
    minWidth: 50,
    format: (a: any) => a,
  },
  {
    id: 'birthDate',
    label: 'DOB',
    align: 'center',
    minWidth: 20,
    format: (a: any) => a,
  },
  {
    id: 'address',
    label: 'Address',
    align: 'center',
    minWidth: 50,
    format: (a: any) => a,
  },
  {
    id: 'email',
    label: 'E-mail',
    align: 'center',
    minWidth: 50,
    format: (a: any) => a,
  },
];

const Owners = () => {
  const router = useRouter();

  const handleRowClick = useCallback(
    (id: number) => router.push(`/owners/${id}`),
    [router]
  );

  return (
    <>
      <Link href="/owners/create">
        <Button variant="outlined">Add a new owner</Button>
      </Link>
      <GenericTable
        columns={columns}
        fetchData={getOwners}
        onRowClick={handleRowClick}
        onItemDelete={deleteOwner}
      />
    </>
  );
};

export default Owners;
