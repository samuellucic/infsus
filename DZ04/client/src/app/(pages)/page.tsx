'use client';

import Notification from '../components/Notification/Notification';
import TermProposalOverview from '../components/TermProposalOverview/TermProposalOverview';
import TourRequest from '../components/TourRequest/TourRequest';
import styles from './page.module.css';
import TermProposal from '../components/TermProposal/TermProposal';

export default function Home() {
  return (
    <main className={styles.main}>
      <div>
        <TermProposal
          buyerName={'Ime nekog sugavog kupca'}
          estateName={'Neka debela masna nekretnina'}
        />
        <TermProposalOverview
          requestId={1}
          date="2.5.2023 19:00"
          estateName="Neka debela nekretnina"
          onAccept={() => {}}
          onRefuse={() => {}}
        />
        <TourRequest
          tourRequestId={1}
          agentId={1}
          buyerId={1}
          buyerName="Kupac koji kupuje"
          estateId={1}
          estateName="Ime nekretnine koje je ime"
          onClick={() => {}}
        />
        <div className={styles['notifications-container']}>
          {[
            'Stigla je obavijest',
            'Stigla je još jedna obavijest',
            'Stiglo je jošššš obavijesti',
            'AAAAAAAAAAAAAAAAAAAAAAAAA',
          ].map((k) => {
            return (
              <div key={k}>
                <Notification message={k} />
              </div>
            );
          })}
        </div>
      </div>
    </main>
  );
}
