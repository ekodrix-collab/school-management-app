import { StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { COLORS } from '../../constants/colors';

const ListItem = ({ title, subtitle, rightElement, onPress }) => {
    const Container = onPress ? TouchableOpacity : View;

    return (
        <Container
            style={styles.container}
            onPress={onPress}
            activeOpacity={onPress ? 0.7 : 1}
        >
            <View style={styles.content}>
                <Text style={styles.title}>{title}</Text>
                {subtitle && <Text style={styles.subtitle}>{subtitle}</Text>}
            </View>
            {rightElement && <View style={styles.right}>{rightElement}</View>}
        </Container>
    );
};

const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        alignItems: 'center',
        paddingVertical: 12,
        paddingHorizontal: 16,
        backgroundColor: COLORS.white,
        borderBottomWidth: 1,
        borderBottomColor: COLORS.gray[200],
    },
    content: {
        flex: 1,
    },
    title: {
        fontSize: 16,
        fontWeight: '500',
        color: COLORS.gray[900],
        marginBottom: 4,
    },
    subtitle: {
        fontSize: 14,
        color: COLORS.gray[600],
    },
    right: {
        marginLeft: 12,
    },
});

export default ListItem;