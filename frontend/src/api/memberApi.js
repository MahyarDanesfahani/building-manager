import api from './axiosConfig';

export const getUnits = (buildingId) => api.get(`/units/building/${buildingId}`);
export const updateResident = (id, data) => api.put(`/residents/${id}`, data);