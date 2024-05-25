import { Column, EstateGet } from '@/app/lib/types';

export const estateTableColumns: Column<EstateGet>[] = [
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

export const estateConstants = {
  price: {
    min: 1,
  },
  address: {
    minLength: 5,
    maxLength: 100,
  },
  area: {
    min: 1,
  },
  description: {
    minLength: 1,
    maxLength: 2000,
  },
};
