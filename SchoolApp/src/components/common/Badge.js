import { StyleSheet, Text, View } from 'react-native';
import { COLORS } from '../../constants/colors';

const Badge = ({ label, variant = 'default' }) => {
    const getBadgeStyle = () => {
        switch (variant) {
            case 'success':
                return { backgroundColor: COLORS.success };
            case 'danger':
                return { backgroundColor: COLORS.danger };
            case 'warning':
                return { backgroundColor: COLORS.warning };
            case 'info':
                return { backgroundColor: COLORS.info };
            default:
                return { backgroundColor: COLORS.gray[500] };
        }
    };

    return (
        <View style={[styles.badge, getBadgeStyle()]}>
            <Text style={styles.text}>{label}</Text>
        </View>
    );
};

const styles = StyleSheet.create({
    badge: {
        paddingHorizontal: 10,
        paddingVertical: 4,
        borderRadius: 12,
        alignSelf: 'flex-start',
    },
    text: {
        color: COLORS.white,
        fontSize: 12,
        fontWeight: '600',
    },
});

export default Badge;