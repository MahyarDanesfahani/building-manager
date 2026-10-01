import api from './axiosConfig';

export const getBills = (buildingId) => api.get(`/bills/building/${buildingId}`);
export const createBill = (data) => api.post('/bills', data);
export const markSharePaid = (shareId) => api.put(`/bills/share/${shareId}/pay`);