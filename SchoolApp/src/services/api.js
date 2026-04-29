import axios from 'axios';
import { storage } from '../utils/storage';

// Replace with your actual API URL
const API_BASE_URL = 'https://your-api-url.com/api';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    timeout: 10000,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Request interceptor - Add token to all requests
apiClient.interceptors.request.use(
    async (config) => {
        const token = await storage.getItem('userToken');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response interceptor - Handle errors
apiClient.interceptors.response.use(
    (response) => response,
    async (error) => {
        if (error.response?.status === 401) {
            // Token expired - logout user
            await storage.clear();
            // Navigate to login (handled in AuthContext)
        }
        return Promise.reject(error);
    }
);

export default apiClient;