'use client';

import { useCallback, useEffect, useState } from 'react';
import styles from './page.module.css';
import { useParams } from 'next/navigation';
import { getInfo, getNotifications, getTasks } from '../../../api/api';
import TourRequest from '../../../components/TourRequest/TourRequest';
import {
  NotificationData,
  TermProposalData,
  TermProposalTaskData,
  TourInfo,
} from '@/app/types';
import TermProposal from '@/app/components/TermProposal/TermProposal';
import Notification from '@/app/components/Notification/Notification';

const AgentPage = () => {
  const { agent }: { agent: string } = useParams();

  const [tasks, setTasks] = useState<TermProposalTaskData[]>([]);
  const [infos, setInfos] = useState<TourInfo[]>([]);
  const [notifications, setNotifications] = useState<NotificationData[]>([]);

  useEffect(() => {
    const ref = setInterval(async () => {
      const data = await getNotifications('agent');
      data.push(...(await getNotifications(agent)));
      if (data.length > 0) {
        setNotifications(data);
      }
    }, 3000);
    return () => {
      clearInterval(ref);
    };
  }, []);

  const fetchInfos = useCallback(() => {
    return getInfo().then((data) => {
      setInfos(data);
    });
  }, []);

  const fetchTasks = useCallback(() => {
    return getTasks(agent).then((data) => {
      setTasks(data);
    });
  }, [agent]);

  useEffect(() => {
    fetchInfos();
  }, []);

  useEffect(() => {
    fetchTasks();
  }, [agent]);

  const handleTourRequestAccept = async () => {
    await fetchInfos();
    await fetchTasks();
  };

  const handleTermProposal = handleTourRequestAccept;
  return (
    <main className={styles.container}>
      <section className={styles.section}>
        <h1>Notifications</h1>
        <div className={styles['requests-container']}>
          {notifications.length > 0 &&
            notifications.map(({ message, id }) => (
              <Notification message={message} key={id} />
            ))}
        </div>
      </section>
      <section className={styles.section}>
        <h1>Accept tours</h1>
        <div className={styles['requests-container']}>
          {infos.length > 0 &&
            infos
              .filter(
                ({ agentId }) => agentId === null || agentId === undefined
              )
              .map((info) => {
                return (
                  <TourRequest
                    key={info.pid}
                    {...info}
                    agentId={agent}
                    onClick={handleTourRequestAccept}
                  />
                );
              })}
        </div>
      </section>
      <section className={styles.section}>
        <h1>Propose terms</h1>
        <div className={styles['requests-container']}>
          {tasks.length > 0 &&
            tasks.map(({ variables, tid }) => {
              const { buyerName, estateDescription } = variables;

              return (
                <TermProposal
                  key={tid}
                  buyerName={buyerName}
                  estateDescription={estateDescription}
                  taskId={tid}
                  onClick={handleTermProposal}
                />
              );
            })}
        </div>
      </section>
    </main>
  );
};

export default AgentPage;
