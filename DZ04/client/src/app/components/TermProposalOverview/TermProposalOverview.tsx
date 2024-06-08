import dayjs from 'dayjs';
import styles from './TermProposalOverview.module.css';
import { answerTermProposal } from '@/app/api/api';

export interface TermProposalOverviewProps {
  taskId: string;
  estateDescription: string;
  date: string;
  onClick: () => void;
}

const TermProposalOverview = ({
  taskId,
  estateDescription,
  date,
  onClick,
}: TermProposalOverviewProps) => {
  const handleAnswer = async (termFits: boolean) => {
    await answerTermProposal({
      taskId,
      termFits,
    });
    onClick();
  };

  return (
    <div className={styles.container}>
      <h4>{estateDescription}</h4>
      <h5>{dayjs(date).toString()}</h5>
      <div className={styles.buttons}>
        <button className={styles.button} onClick={() => handleAnswer(true)}>
          Accept
        </button>
        <button className={styles.button} onClick={() => handleAnswer(false)}>
          Refuse
        </button>
      </div>
    </div>
  );
};

export default TermProposalOverview;
