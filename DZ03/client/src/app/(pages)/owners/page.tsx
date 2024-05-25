'use client';

import { useCallback } from 'react';
import { Column, EstateOwner } from '../../lib/types';
import { useRouter } from 'next/navigation';
import { deleteOwner, getOwners } from '../../api/api';
import GenericTable from '../../components/CoreComponents/GenericTable/GenericTable';

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
