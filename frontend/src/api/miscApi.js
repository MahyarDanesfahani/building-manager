import api from './axiosConfig';

export const getMiscExpenses = (buildingId) => api.get(`/misc-expenses/building/${buildingId}`);
export const createMiscExpense = (data) => api.post('/misc-expenses', data);
export const markMiscSharePaid = (shareId) => api.put(`/misc-expenses/share/${shareId}/pay`);