import { ReactNode } from 'react';

export type AtLeastOne<T, U = { [K in keyof T]: Pick<T, K> }> = Partial<T> &
  U[keyof U];

export type Id = {
  id: number | string;
};

export interface Pageable {
  page: number;
  size: number;
}

export interface PageableResponse<T> {
  content: T[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: { empty: boolean; sorted: boolean; unsorted: boolean };
    unpaged: boolean;
  };
  size: number;
  sort: { empty: boolean; sorted: boolean; unsorted: boolean };
  totalElements: number;
  totalPages: number;
}

export interface Column<T> {
  id: keyof T;
  label: string;
  minWidth?: number;
  align?: 'right' | 'center' | 'left';
  format?: (value: T[keyof T]) => ReactNode[];
}

export interface EstateOwner {
  id?: number;
  name: string;
  surname: string;
  birthDate: string;
  address: string;
  email: string;
  townId: number;
}

export interface Estate {
  id?: number;
  price: number;
  address: string;
  area: number;
  description: string;
  estateType: string;
}

export interface EstateType {
  name: string;
  description: string;
}

export interface Town {
  id?: number;
  name: string;
  postCode: string;
  region: string;
  country: string;
}
