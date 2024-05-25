import { Column, EstateType } from '../lib/types';

export const estateTypeColumns: Column<EstateType>[] = [
  {
    id: 'name',
    label: 'Name',
    minWidth: 170,
  },
  {
    id: 'description',
    label: 'Description',
    minWidth: 100,
  },
];
