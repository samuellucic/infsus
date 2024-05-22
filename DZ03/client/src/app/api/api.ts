import { Axios } from 'axios';
import { RealEstateType } from '../lib/types';

const api = new Axios({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

// create or update
export const saveRealEstateType = async ({
  id,
  ...realEstateType
}: RealEstateType) => {
  if (id) {
    return await api.put(`/api/real-estate-type/${id}`, realEstateType);
  }
  return await api.post('/api/real-estate-type', realEstateType);
};
