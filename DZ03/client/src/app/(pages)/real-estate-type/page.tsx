'use client';

import Link from 'next/link';
import styles from './page.module.css';
import RealEstateTypeList from '@/app/components/RealEstateType/RealEstateTypeForm/RealEstateTypeList';
import { Button } from '@mui/material';

const RealEstateTypePage = () => {
  return (
    <div className={styles.container}>
      <Link href="/real-estate-type-form">
        <Button variant="outlined" className={styles.button}>Make new estate type</Button>
      </Link>
      <RealEstateTypeList />
    </div>
  );
};

export default RealEstateTypePage;
