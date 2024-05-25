import { Column, Estate } from '../lib/types';

export const ownerTableColumns: Column<Estate>[] = [
  {
    id: 'price',
    label: 'Price',
    align: 'center',
    minWidth: 50,
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
    id: 'area',
    label: 'Area',
    align: 'center',
    minWidth: 20,
    format: (a: any) => a,
  },
  {
    id: 'description',
    label: 'Description',
    align: 'center',
    minWidth: 50,
    format: (a: any) => a,
  },
];
