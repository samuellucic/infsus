import api from './api';
import { EstateType } from '../lib/types';

export const saveRealEstateType = async (estateType: EstateType) => {
    return await api.post('/api/estateType', estateType);
  };

  export const getRealEstateTypesPaginated = async (page: number, size: number) => {
    try {
      const response = await api.get('/api/estateType/all', {
        params: {
          page: page,
          size: size
        }
      });
      return response.data;
    } catch (error) {
      console.error('Error fetching paginated real estate types:', error);
      throw error;
    }
  };