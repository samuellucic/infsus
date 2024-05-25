import { Column, EstateOwner } from '../lib/types';

export const ownerTableColumns: Column<EstateOwner>[] = [
  {
    id: 'name',
    label: 'First name',
    align: 'center',
    minWidth: 50,
    format: (a: any) => a,
  },
  {
    id: 'surname',
    label: 'Last name',
    align: 'center',
    minWidth: 50,
    format: (a: any) => a,
  },
  {
    id: 'birthDate',
    label: 'DOB',
    align: 'center',
    minWidth: 20,
    format: (a: any) => a,
  },
  {
    id: 'address',
    label: 'Address',
    align: 'center',
    minWidth: 50,
    format: (a: any) => a,
  },
  {
    id: 'email',
    label: 'E-mail',
    align: 'center',
    minWidth: 50,
    format: (a: any) => a,
  },
];

export const ownerConstants = {
  name: {
    minLength: 2,
    maxLength: 100,
  },
  surname: {
    minLength: 2,
    maxLength: 100,
  },
  address: {
    minLength: 5,
    maxLength: 100,
  },
};
