'use client';

import styles from './page.module.css';
import { useRouter } from 'next/navigation';

export default function Home() {
  const router = useRouter();
  router.push('/real-estate-type');

  return <main className={styles.main}></main>;
}
