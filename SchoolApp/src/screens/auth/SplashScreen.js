import { Image, StyleSheet, Text, View } from 'react-native';
import { useTheme } from '../../contexts/ThemeContext';

const SplashScreen = () => {
    const { theme } = useTheme();

    return (
        <View style={[styles.container, { backgroundColor: theme.primaryColor }]}>
            {theme.schoolLogo ? (
                <Image
                    source={{ uri: theme.schoolLogo }}
                    style={styles.logo}
                />
            ) : (
                <View style={styles.logoPlaceholder}>
                    <Text style={styles.logoText}>
                        {theme.schoolName?.charAt(0) || 'S'}
                    </Text>
                </View>
            )}
            <Text style={styles.schoolName}>{theme.schoolName}</Text>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
    logo: {
        width: 150,
        height: 150,
        resizeMode: 'contain',
        borderRadius: 20,
    },
    logoPlaceholder: {
        width: 120,
        height: 120,
        borderRadius: 60,
        backgroundColor: 'rgba(255,255,255,0.3)',
        justifyContent: 'center',
        alignItems: 'center',
        marginBottom: 16,
    },
    logoText: {
        fontSize: 60,
        fontWeight: 'bold',
        color: '#ffffff',
    },
    schoolName: {
        fontSize: 24,
        fontWeight: 'bold',
        color: '#ffffff',
        marginTop: 16,
        textAlign: 'center',
        paddingHorizontal: 20,
    },
});

export default SplashScreen;