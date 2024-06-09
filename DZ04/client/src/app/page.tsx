'use client';

import Notification from './components/Notification/Notification';
import TermProposalOverview from './components/TermProposalOverview/TermProposalOverview';
import TourRequest from './components/TourRequest/TourRequest';
import styles from './page.module.css';
import TermProposal from './components/TermProposal/TermProposal';
import Link from 'next/link';

export default function Home() {
  return (
    <main className={styles.main}>
      <h3>Who are you</h3>
      <div className={styles.links}>
        <Link className={styles.button} href={'/buyers/buyer1'}>
          Buyer
        </Link>
        <Link className={styles.button} href={'/agents/agent1'}>
          Agent
        </Link>
      </div>
    </main>
  );
}
