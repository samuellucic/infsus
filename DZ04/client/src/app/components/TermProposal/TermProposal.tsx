'use client';

import styles from './TermProposal.module.css';
import { DateTimePicker } from '@mui/x-date-pickers';
import { useState } from 'react';
import { Dayjs } from 'dayjs';
import { proposeTerm } from '@/app/api/api';

export interface TermProposalProps {
  taskId: string;
  buyerName: string;
  estateDescription: string;
  onClick: () => void;
}

const TermProposal = ({
  taskId,
  buyerName,
  estateDescription,
  onClick,
}: TermProposalProps) => {
  const [term, setTerm] = useState<Dayjs | null>(null);

  const handleClick = async () => {
    if (term) {
      await proposeTerm({
        taskId,
        tourDateTime: term!.toISOString(),
      });
      onClick();
    }
  };

  return (
    <div className={styles.container}>
      <h4>{buyerName}</h4>
      <h5>{estateDescription}</h5>
      <DateTimePicker
        sx={{
          width: '20rem',
        }}
        label="Choose date and time of the tour"
        value={term}
        onChange={(value) => setTerm(value)}
      />
      <button
        className={styles.button}
        onClick={handleClick}
        disabled={term === null}>
        Propose
      </button>
    </div>
  );
};

export default TermProposal;
