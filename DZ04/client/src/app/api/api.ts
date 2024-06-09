import axios from 'axios';
import {
  AcceptTourData,
  EstateData,
  NotificationData,
  StartProcessData,
  TermProposalAnswerData,
  TermProposalData,
  TourInfo,
} from '../types';

const api = axios.create({
  baseURL: 'http://localhost:10000/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

export default api;

export const getEstates = async () => {
  const res = await api.get<EstateData[]>('/estate/all');
  return res.data;
};

export const startProcess = async (startProcessData: StartProcessData) => {
  return await api.post('/camunda/start', startProcessData);
};

export const getNotifications = async (userId: string) => {
  const res = await api.get<NotificationData[]>('/camunda/notifications', {
    params: {
      userId,
    },
  });
  return res.data;
};

export const getInfo = async () => {
  const res = await api.get<TourInfo[]>('/camunda/tourinfo');
  return res.data;
};

export const getTasks = async (userId: string) => {
  const res = await api.get('/camunda/tasks', {
    params: {
      userId,
    },
  });
  return res.data;
};

export const acceptTour = async (acceptTourData: AcceptTourData) => {
  return await api.post('/camunda/presuggest', acceptTourData);
};

export const getAcceptedTours = async (id: string) => {
  const res = await api.get('/camunda/accepted-tours', {
    params: { id },
  });
  return res.data;
};

export const proposeTerm = async (termProposal: TermProposalData) => {
  return await api.post('/camunda/suggest', termProposal);
};

export const answerTermProposal = async (
  termProposalAnswerData: TermProposalAnswerData
) => {
  return await api.post('/camunda/decide', termProposalAnswerData);
};
