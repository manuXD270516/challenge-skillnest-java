import axios from '../api/axiosInstance';
import { Publication } from '../interfaces/Publication';

export const fetchPublications = async (): Promise<Publication[]> => {
  const response = await axios.get('/api/posts');
  return response.data;
};

export const getPublicationById = async (id: number): Promise<Publication> => {
  const response = await axios.get(`/api/posts/${id}`);
  return response.data;
};

export const createPublication = async (publication: Publication) => {
  return await axios.post('/api/posts', publication);
};

export const updatePublication = async (id: number, publication: Publication) => {
  return await axios.put(`/api/posts/${id}`, publication);
};

export const deletePublication = async (id: number) => {
  return await axios.delete(`/api/posts/${id}`);
};
