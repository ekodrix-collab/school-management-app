import * as SecureStore from 'expo-secure-store';

export const storage = {
    // Save data
    async setItem(key, value) {
        try {
            await SecureStore.setItemAsync(key, JSON.stringify(value));
        } catch (error) {
            console.error('Storage setItem error:', error);
        }
    },

    // Get data
    async getItem(key) {
        try {
            const value = await SecureStore.getItemAsync(key);
            return value ? JSON.parse(value) : null;
        } catch (error) {
            console.error('Storage getItem error:', error);
            return null;
        }
    },

    // Remove data
    async removeItem(key) {
        try {
            await SecureStore.deleteItemAsync(key);
        } catch (error) {
            console.error('Storage removeItem error:', error);
        }
    },

    // Clear all app data
    async clear() {
        try {
            const keys = ['userToken', 'userData', 'schoolTheme'];
            await Promise.all(
                keys.map(key => SecureStore.deleteItemAsync(key))
            );
        } catch (error) {
            console.error('Storage clear error:', error);
        }
    },
};