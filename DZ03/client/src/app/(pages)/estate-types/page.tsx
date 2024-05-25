'use client';

import Link from 'next/link';
import styles from './page.module.css';
import { Button } from '@mui/material';
import { useCallback } from 'react';
import { deleteEstateType, getEstateTypes } from '../../api/api';
import GenericTable from '../../components/CoreComponents/GenericTable/GenericTable';
import { EstateType } from '../../lib/types';
import { estateTypeColumns } from '../../utils/estateTypeConstants';
import { useRouter } from 'next/navigation';

const EstateTypePage = () => {
  const router = useRouter();

  const handleDelete = useCallback(async (name: string) => {
    await deleteEstateType(name);
  }, []);

  const handleRowClick = useCallback(
    (name: string) => {
      router.push(`/estate-types/${name}`);
    },
    [router]
  );

  return (
    <div className={styles.container}>
      <Link href="/estate-types/create">
        <Button variant="outlined" className={styles.button}>
          Create a new estate type
        </Button>
      </Link>
      <GenericTable<EstateType>
        columns={estateTypeColumns}
        fetchData={getEstateTypes}
        onItemDelete={handleDelete}
        onRowClick={handleRowClick}
      />
    </div>
  );
};

export default EstateTypePage;
