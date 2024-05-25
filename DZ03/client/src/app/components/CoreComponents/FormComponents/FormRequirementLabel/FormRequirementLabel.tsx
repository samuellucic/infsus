import { Typography } from '@mui/material';
import ClearIcon from '@mui/icons-material/Clear';
import CheckIcon from '@mui/icons-material/Check';
import styles from './FormRequirementLabel.module.css';

export interface FormRequirementLabelProps {
  label: string;
  valid: boolean;
}

const FormRequirementLabel = ({ label, valid }: FormRequirementLabelProps) => {
  return (
    <Typography className={styles.container} color={valid ? 'white' : 'red'}>
      {valid ? (
        <CheckIcon color="success" fontSize="small" />
      ) : (
        <ClearIcon color="error" fontSize="small" />
      )}
      {label}
    </Typography>
  );
};

export default FormRequirementLabel;
