'use client';

import { useCallback } from 'react';
import { useRouter } from 'next/navigation';
import { deleteOwner, getOwners } from '../../api/api';
import GenericTable from '../../components/CoreComponents/GenericTable/GenericTable';
import { Button } from '@mui/material';
import Link from 'next/link';
import { ownerTableColumns } from '@/app/utils/ownerConstants';

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
        columns={ownerTableColumns}
        fetchData={getOwners}
        onRowClick={handleRowClick}
        onItemDelete={deleteOwner}
      />
    </>
  );
};

export default Owners;
