import styles from './TourItem.module.css';
import { startProcess } from '../../api/api';

export interface TourItemProps {
  buyerId: string;
  id: number;
  description: string;
  onClick: () => void;
}

const TourItem = ({ buyerId, id, description, onClick }: TourItemProps) => {
  const handleClick = async () => {
    await startProcess({
      requestId: Math.round(Math.random() * 10000000) + id,
      estateId: id,
      buyerId,
    });
    onClick();
  };

  return (
    <div className={styles.container}>
      <h4>{description}</h4>
      <button className={styles.button} onClick={handleClick}>
        Apply
      </button>
    </div>
  );
};

export default TourItem;
