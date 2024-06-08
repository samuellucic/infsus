import { Alert } from '@mui/material';
import styles from './Notification.module.css';

export interface NotificationProps {
  message: string;
}

const Notification = ({ message }: NotificationProps) => {
  return (
    <div className={styles.container}>
      <Alert severity="info">{message}</Alert>
    </div>
  );
};

export default Notification;
