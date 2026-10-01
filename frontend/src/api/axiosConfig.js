import axios from 'axios';

const api = axios.create({
    baseURL: 'http://localhost:8081/api',
});

// این بخش خودکار توکن رو به هر درخواست اضافه می‌کنه (اگر وجود داشته باشه)
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export default api;