import axios from 'axios';
import {
  Estate,
  EstateOwner,
  EstateType,
  Pageable,
  PageableResponse,
  Town,
} from '../lib/types';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// owners
export const getOwners = async (params: Pageable) => {
  return (
    await api.get('/owner/all', {
      params,
    })
  ).data;
};

export const getOwner = async (id: number) => {
  return (await api.get<EstateOwner>(`/owner/${id}`)).data;
};

export const createOwner = async (owner: EstateOwner) => {
  return await api.post('/owner', owner);
};

export const updateOwner = async (owner: EstateOwner) => {
  return await api.post('/owner/update', owner);
};

export const deleteOwner = async (id: number) => {
  return await api.delete(`/owner/${id}`);
};

// estates
export const getEstatesOwnedByOwner = async (
  params: Pageable,
  ownerId: number
) => {
  return (
    await api.get(`/estate`, {
      params: { ...params, ownerId },
    })
  ).data;
};

export const getEstate = async (id: number) => {
  return (await api.get<Estate>(`/estate/${id}`)).data;
};

export const createEstate = async (estate: Estate) => {
  return await api.post('/estate', estate);
};

export const updateEstate = async (estate: Estate) => {
  return await api.post('/estate/update', estate);
};

export const deleteEstate = async (estateId: number) => {
  return await api.delete(`/estate/${estateId}`);
};

// estate types
export const getEstateTypes = async (params: Pageable) => {
  const resp = (
    await api.get<PageableResponse<EstateType>>('/estateType/all', { params })
  ).data;

  return {
    content: resp.content.map((item) => ({ id: item.name, ...item })),
    totalElements: resp.totalElements,
  };
};

export const getEstateType = async (name: string) => {
  return (
    await api.get<EstateType>('/estateType', {
      params: {
        estateType: name,
      },
    })
  ).data;
};

export const createEstateType = async (estateType: EstateType) => {
  return await api.post('/estateType', estateType);
};

export const updateEstateType = async (estateType: EstateType) => {
  return await api.post('/estateType/update', estateType);
};

export const deleteEstateType = async (estateTypeName: string) => {
  return await api.delete('/estateType', {
    params: {
      estateType: estateTypeName,
    },
  });
};

// towns
export const getTowns = async (params: Pageable = { page: 0, size: 1000 }) => {
  return (await api.get<PageableResponse<Town>>('/town/all', { params })).data
    .content;
};

export const getTown = async (id: number) => {
  return (await api.get(`/town/${id}`)).data;
};

export const createTown = async (town: Town) => {
  return await api.post('/town', town);
};

export const updateTown = async (town: Town) => {
  return await api.post('/town/update', town);
};

export const deleteTown = async (id: number) => {
  return await api.delete(`/town/${id}`);
};

export default api;
