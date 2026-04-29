import { FlatList, StyleSheet, Text, View } from 'react-native';
import Card from '../../components/common/Card';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';

const NotificationsScreen = () => {
    const { user } = useAuth();

    // In a production app, these would come from your Spring Boot API
    // GET /api/notifications?role=TEACHER or role=PARENT
    const mockData = user?.role === 'TEACHER'
        ? [
            { id: 't1', title: 'Staff Meeting', message: 'Urgent meeting at 4PM today.', date: 'Today' },
            { id: 't2', title: 'Exam Duties', message: 'Duty list for Finals is published.', date: 'Yesterday' }
        ]
        : [
            { id: 'p1', title: 'School Holiday', message: 'Holiday tomorrow due to rain.', date: 'Today' },
            { id: 'p2', title: 'Fee Reminder', message: 'Term 2 fees due by next Friday.', date: '2 days ago' }
        ];

    const renderItem = ({ item }) => (
        <Card style={styles.card}>
            <View style={styles.header}>
                <Text style={styles.title}>{item.title}</Text>
                <Text style={styles.date}>{item.date}</Text>
            </View>
            <Text style={styles.message}>{item.message}</Text>
        </Card>
    );

    return (
        <View style={styles.container}>
            <FlatList
                data={mockData}
                // Safe Key Extractor to prevent "toString of undefined"
                keyExtractor={(item, index) => (item?.id ? item.id.toString() : index.toString())}
                renderItem={renderItem}
                contentContainerStyle={styles.list}
                ListEmptyComponent={
                    <View style={styles.empty}>
                        <Text style={styles.emptyText}>No notifications found.</Text>
                    </View>
                }
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#f8fafc' },
    list: { padding: 16 },
    card: {
        marginBottom: 12,
        borderLeftWidth: 5,
        borderLeftColor: COLORS.primary || '#2563eb'
    },
    header: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 6 },
    title: { fontSize: 16, fontWeight: 'bold', color: '#1e293b', flex: 1 },
    date: { fontSize: 12, color: '#94a3b8' },
    message: { fontSize: 14, color: '#475569', lineHeight: 20 },
    empty: { alignItems: 'center', marginTop: 50 },
    emptyText: { color: '#94a3b8' }
});

export default NotificationsScreen;