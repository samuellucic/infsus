import { ReactNodeArray } from 'react';

export type AtLeastOne<T, U = { [K in keyof T]: Pick<T, K> }> = Partial<T> &
  U[keyof U];

export interface EstateType {
  name?: string;
  description?: string;
}

export interface Column<T> {
  id: keyof T;
  label: string;
  minWidth?: number;
  align?: 'right';
  format?: (value: T[keyof T]) => ReactNodeArray; 
}
