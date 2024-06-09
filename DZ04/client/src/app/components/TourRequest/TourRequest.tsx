import { acceptTour } from '@/app/api/api';
import styles from './TourRequest.module.css';

export interface TourRequestProps {
  agentId: string;
  pid: string;
  buyerName: string;
  estateDescription: string;
  onClick: () => void;
}

const TourRequest = ({
  agentId,
  pid,
  buyerName,
  estateDescription,
  onClick,
}: TourRequestProps) => {
  const handleClick = async () => {
    await acceptTour({
      agentId,
      pid,
    });
    onClick();
  };

  return (
    <div className={styles.container}>
      <div className={styles.text}>
        <h4 className={styles.buyer}>{buyerName}</h4>
        <h5 className={styles.estate}>{estateDescription}</h5>
      </div>
      <button className={styles.button} onClick={handleClick}>
        Accept
      </button>
    </div>
  );
};

export default TourRequest;
