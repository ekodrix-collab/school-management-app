import { ActivityIndicator, StyleSheet, Text, TouchableOpacity } from 'react-native';
import { useTheme } from '../../contexts/ThemeContext';

const Button = ({
    title,
    onPress,
    variant = 'primary',
    loading = false,
    disabled = false,
    style,
}) => {
    const { theme } = useTheme();

    const getButtonStyle = () => {
        if (disabled) {
            return [styles.button, styles.disabled, style];
        }
        if (variant === 'primary') {
            return [styles.button, { backgroundColor: theme.primaryColor }, style];
        }
        if (variant === 'secondary') {
            return [styles.button, styles.secondary, style];
        }
        return [styles.button, style];
    };

    return (
        <TouchableOpacity
            style={getButtonStyle()}
            onPress={onPress}
            disabled={disabled || loading}
            activeOpacity={0.7}
        >
            {loading ? (
                <ActivityIndicator color="#fff" />
            ) : (
                <Text style={styles.text}>{title}</Text>
            )}
        </TouchableOpacity>
    );
};

const styles = StyleSheet.create({
    button: {
        paddingVertical: 14,
        paddingHorizontal: 24,
        borderRadius: 8,
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: 48,
    },
    text: {
        color: '#fff',
        fontSize: 16,
        fontWeight: '600',
    },
    secondary: {
        backgroundColor: '#64748b',
    },
    disabled: {
        backgroundColor: '#d1d5db',
    },
});

export default Button;