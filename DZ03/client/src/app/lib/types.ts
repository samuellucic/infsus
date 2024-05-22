export type AtLeastOne<T, U = { [K in keyof T]: Pick<T, K> }> = Partial<T> &
  U[keyof U];

export interface RealEstateType {
  id?: number;
  name?: string;
  description?: string;
}
