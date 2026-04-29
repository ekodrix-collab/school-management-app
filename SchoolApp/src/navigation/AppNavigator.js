import { NavigationContainer } from '@react-navigation/native';
import { useEffect, useState } from 'react'; // Add React here
import { useAuth } from '../hooks/useAuth';
import SplashScreen from '../screens/auth/SplashScreen';
import AuthNavigator from './AuthNavigator';
import ParentNavigator from './ParentNavigator';
import TeacherNavigator from './TeacherNavigator';

const AppNavigator = () => {
    const { user, loading } = useAuth();
    const [showSplash, setShowSplash] = useState(true);

    useEffect(() => {
        const timer = setTimeout(() => {
            setShowSplash(false);
        }, 2000);
        return () => clearTimeout(timer);
    }, []);

    if (showSplash || loading) {
        return <SplashScreen />;
    }

    // Logic is fine, but ensure the Navigators are definitely exported as 'default'
    const getNavigator = () => {
        if (!user) return <AuthNavigator />;
        if (user.isFirstLogin) return <AuthNavigator />;
        if (user.role === 'PARENT') return <ParentNavigator />;
        if (user.role === 'TEACHER') return <TeacherNavigator />;
        return <AuthNavigator />;
    };

    return (
        <NavigationContainer>
            {getNavigator()}
        </NavigationContainer>
    );
};

export default AppNavigator;