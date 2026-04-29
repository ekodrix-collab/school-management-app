import apiClient from './api';

export const authService = {
    // Login
    async login(mobileNumber, password) {
        const response = await apiClient.post('/auth/login', {
            mobileNumber,
            password,
        });
        return response.data;
    },

    // Change password
    async changePassword(oldPassword, newPassword) {
        const response = await apiClient.post('/auth/change-password', {
            oldPassword,
            newPassword,
        });
        return response.data;
    },

    // Get school theme
    async getSchoolTheme() {
        const response = await apiClient.get('/school/theme');
        return response.data;
    },

    // Logout
    async logout() {
        const response = await apiClient.post('/auth/logout');
        return response.data;
    },
};