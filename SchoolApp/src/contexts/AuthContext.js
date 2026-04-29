import { createContext, useEffect, useState } from 'react';
import { storage } from '../utils/storage';

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        // Check if user is already logged in on app start
        checkUser();
    }, []);

    const checkUser = async () => {
        try {
            const userData = await storage.getItem('userData');
            const token = await storage.getItem('userToken');

            if (userData && token) {
                setUser(userData);
            } else {
                setUser(null);
            }
        } catch (error) {
            console.error('Check user error:', error);
            setUser(null);
        } finally {
            // Always set loading to false when done to remove splash screen
            setLoading(false);
        }
    };

    const login = async (mobileNumber, password) => {
        try {
            // ============================================
            // TEMPORARY MOCK LOGIN - For Development/Demo
            // ============================================

            // 1. MOCK PARENT LOGIN
            if (mobileNumber === '9876543210' && password === 'test123') {
                const mockUser = {
                    id: 1,
                    name: 'Mr. Rajesh Kumar',
                    role: 'PARENT',
                    mobileNumber: '9876543210',
                    studentId: 101,
                    studentName: 'Adithya Kumar',
                    className: 'Class 10-A',
                    isFirstLogin: false,
                };

                await storage.setItem('userToken', 'mock-token-parent-123');
                await storage.setItem('userData', mockUser);
                setUser(mockUser);
                return mockUser;
            }

            // 2. MOCK TEACHER LOGIN (Updated with Class Teacher details)
            if (mobileNumber === '9876543211' && password === 'teacher123') {
                const mockTeacher = {
                    id: 2,
                    name: 'Mrs. Lekshmi',
                    role: 'TEACHER',
                    mobileNumber: '9876543211',
                    isClassTeacher: true, // triggers "My Class" console on dashboard
                    className: '10-A',
                    classId: 1,
                    subject: 'Mathematics',
                    isFirstLogin: false,
                };

                await storage.setItem('userToken', 'mock-token-teacher-456');
                await storage.setItem('userData', mockTeacher);
                setUser(mockTeacher);
                return mockTeacher;
            }

            // Fallback for wrong credentials
            throw new Error('Invalid mobile number or password');

            /* 
            ============================================
            REAL API LOGIN - Uncomment when Spring Boot backend is ready
            ============================================
            const response = await authService.login(mobileNumber, password);
            await storage.setItem('userToken', response.token);
            await storage.setItem('userData', response.user);
            setUser(response.user);
            return response;
            */

        } catch (error) {
            throw error;
        }
    };

    const changePassword = async (oldPassword, newPassword) => {
        try {
            // Real API Call would go here:
            // await authService.changePassword(oldPassword, newPassword);

            // Update local state and storage to reflect password change success
            const updatedUser = { ...user, isFirstLogin: false };
            await storage.setItem('userData', updatedUser);
            setUser(updatedUser);
        } catch (error) {
            throw error;
        }
    };

    const logout = async () => {
        try {
            // Clear all data from SecureStore
            await storage.clear();
            setUser(null);
        } catch (error) {
            console.error('Logout error:', error);
            setUser(null);
        }
    };

    return (
        <AuthContext.Provider
            value={{
                user,
                loading,
                login,
                logout,
                changePassword,
            }}
        >
            {children}
        </AuthContext.Provider>
    );
};