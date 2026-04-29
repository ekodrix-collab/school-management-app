import { createContext, useContext, useEffect, useState } from 'react';
import { storage } from '../utils/storage';

const ThemeContext = createContext();

// Default theme
const defaultTheme = {
    primaryColor: '#2563eb',
    schoolName: 'School Management',
    schoolLogo: null,
};

export const ThemeProvider = ({ children }) => {
    const [theme, setTheme] = useState(defaultTheme);

    useEffect(() => {
        loadTheme();
    }, []);

    const loadTheme = async () => {
        try {
            const savedTheme = await storage.getItem('schoolTheme');
            if (savedTheme) {
                setTheme(savedTheme);
            }
        } catch (error) {
            console.error('Load theme error:', error);
        }
    };

    const updateTheme = async (newTheme) => {
        try {
            const updatedTheme = { ...defaultTheme, ...newTheme };
            setTheme(updatedTheme);
            await storage.setItem('schoolTheme', updatedTheme);
        } catch (error) {
            console.error('Update theme error:', error);
        }
    };

    return (
        <ThemeContext.Provider value={{ theme, updateTheme }}>
            {children}
        </ThemeContext.Provider>
    );
};

export const useTheme = () => {
    const context = useContext(ThemeContext);
    if (!context) {
        throw new Error('useTheme must be used within ThemeProvider');
    }
    return context;
};