'use client';

import { useCallback, useEffect, useState } from 'react';
import { getEstates, getInfo, getTasks } from '../../../api/api';
import { EstateData, TermProposalOverviewTaskData } from '../../../types';
import TourItem from '../../../components/TourItem/TourItem';
import styles from './page.module.css';
import { useParams } from 'next/navigation';
import TermProposalOverview from '@/app/components/TermProposalOverview/TermProposalOverview';

const BuyerPage = () => {
  const { buyer }: { buyer: string } = useParams();

  const [estates, setEstates] = useState<EstateData[]>([]);
  const [terms, setTerms] = useState<TermProposalOverviewTaskData[]>([]);

  const fetchEstates = useCallback(async () => {
    const estates = await getEstates();
    const info = await getInfo();

    setEstates(
      estates.filter((estate) => {
        return info.find((item) => item.estateId === estate.id) === undefined;
      })
    );
  }, []);

  const fetchTasks = useCallback(() => {
    getTasks(buyer).then((data) => {
      setTerms(data);
    });
  }, [buyer]);

  useEffect(() => {
    fetchEstates();
  }, []);

  useEffect(() => {
    fetchTasks();
  }, [buyer]);

  const handleClick = useCallback(() => {
    fetchEstates();
    fetchTasks();
  }, []);

  return (
    <main className={styles.container}>
      <section className={styles.section}>
        <h1>Apply for tour</h1>
        <div className={styles['requests-container']}>
          {estates.length > 0 &&
            estates.map((estate) => {
              return (
                <TourItem
                  buyerId={buyer as string}
                  {...estate}
                  key={estate.id}
                  onClick={handleClick}
                />
              );
            })}
        </div>
      </section>
      <section className={styles.section}>
        <h1>Review terms</h1>
        <div className={styles['requests-container']}>
          {terms.length > 0 &&
            terms.map(({ tid, variables }) => {
              const data = {
                ...variables,
                date: variables.TourDateTime,
              };

              return (
                <TermProposalOverview
                  key={tid}
                  taskId={tid}
                  {...data}
                  onClick={handleClick}
                />
              );
            })}
        </div>
      </section>
    </main>
  );
};

export default BuyerPage;
