import { useQuery } from '@tanstack/react-query';
import { FlatList, StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import Card from '../../components/common/Card';
import Loader from '../../components/common/Loader';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';
import { parentService } from '../../services/parentService';
import { formatDate } from '../../utils/helpers';

const HomeworkScreen = () => {
    const { user } = useAuth();

    const { data, isLoading } = useQuery({
        queryKey: ['homework', user?.studentId],
        queryFn: () => parentService.getHomework(user?.studentId),
    });

    const renderItem = ({ item }) => {
        if (!item) return null;

        return (
            <Card style={styles.card}>
                <View style={styles.subjectContainer}>
                    <Text style={styles.subject}>{item.subject || 'Subject'}</Text>
                    <Text style={styles.date}>Assigned: {item.date ? formatDate(item.date) : 'N/A'}</Text>
                </View>
                <Text style={styles.title}>{item.title || 'Untitled Homework'}</Text>
                <Text style={styles.description}>{item.description || 'No description provided.'}</Text>
                {item.dueDate && (
                    <Text style={styles.dueDate}>Due Date: {formatDate(item.dueDate)}</Text>
                )}
            </Card>
        );
    };

    if (isLoading) return <Loader />;

    return (
        <SafeAreaView style={styles.container} edges={['bottom']}>
            <FlatList
                data={data || []}
                renderItem={renderItem}
                // CRITICAL FIX: Fallback to index if id is missing
                keyExtractor={(item, index) => (item?.id ? item.id.toString() : index.toString())}
                contentContainerStyle={styles.list}
                ListEmptyComponent={
                    <View style={styles.emptyContainer}>
                        <Text style={styles.emptyText}>No homework assigned</Text>
                    </View>
                }
            />
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f8fafc',
    },
    list: {
        padding: 16,
    },
    card: {
        marginBottom: 16,
        borderLeftWidth: 4,
        borderLeftColor: COLORS.primary || '#2563eb',
    },
    subjectContainer: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: 8,
    },
    subject: {
        fontSize: 13,
        fontWeight: 'bold',
        color: COLORS.primary || '#2563eb',
        textTransform: 'uppercase',
    },
    date: {
        fontSize: 12,
        color: '#94a3b8',
    },
    title: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#1e293b',
        marginBottom: 8,
    },
    description: {
        fontSize: 14,
        color: '#475569',
        lineHeight: 20,
    },
    dueDate: {
        fontSize: 13,
        color: '#d97706', // warning color
        marginTop: 12,
        fontWeight: '600',
    },
    emptyContainer: {
        alignItems: 'center',
        marginTop: 60,
    },
    emptyText: {
        color: '#94a3b8',
        fontSize: 16,
    },
});

export default HomeworkScreen;