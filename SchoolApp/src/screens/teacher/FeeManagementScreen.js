import { Ionicons } from '@expo/vector-icons';
import { useMutation, useQuery } from '@tanstack/react-query';
import { Alert, FlatList, StyleSheet, Text, View } from 'react-native';

// Components & Services
import Button from '../../components/common/Button';
import Card from '../../components/common/Card';
import Loader from '../../components/common/Loader';
import { useAuth } from '../../hooks/useAuth';
import { teacherService } from '../../services/teacherService';

const FeeManagementScreen = () => {
    const { user } = useAuth();

    // Fetch Fee Summary Data
    const { data, isLoading } = useQuery({
        queryKey: ['feeSummary', user?.classId],
        queryFn: () => teacherService.getFeeSummary(user?.classId),
    });

    // Nudge Mutation
    const mutation = useMutation({
        mutationFn: (studentIds) => teacherService.nudgeParents(studentIds),
        onSuccess: () => {
            Alert.alert("Success", "Reminder notifications have been sent to parents.");
        },
        onError: () => {
            Alert.alert("Error", "Failed to send notifications.");
        }
    });

    const handleNudgeAll = () => {
        if (!data?.pendingStudents) return;
        const ids = data.pendingStudents.map(s => s.id);
        mutation.mutate(ids);
    };

    if (isLoading) return <Loader />;

    const renderStudent = ({ item }) => (
        <View style={styles.studentRow}>
            <View>
                <Text style={styles.studentName}>{item.name}</Text>
                <Text style={styles.pendingLabel}>Pending Amount</Text>
            </View>
            <Text style={styles.studentAmount}>₹ {item.amount.toLocaleString()}</Text>
        </View>
    );

    return (
        <View style={styles.container}>
            {/* Summary Card */}
            <Card style={styles.summaryCard}>
                <View style={styles.summaryHeader}>
                    <Ionicons name="wallet" size={24} color="#fff" />
                    <Text style={styles.summaryTitle}>Total Pending Fees</Text>
                </View>
                <Text style={styles.totalAmount}>₹ {data?.totalPending?.toLocaleString() || '0'}</Text>
                <Button
                    title="Nudge All Defaulters"
                    onPress={handleNudgeAll}
                    loading={mutation.isPending}
                    style={styles.nudgeBtn}
                />
            </Card>

            <Text style={styles.listHeader}>Defaulters List ({data?.pendingStudents?.length || 0})</Text>

            <FlatList
                data={data?.pendingStudents || []}
                keyExtractor={(item) => item.id.toString()}
                renderItem={renderStudent}
                contentContainerStyle={styles.list}
                ListEmptyComponent={
                    <Text style={styles.emptyText}>No pending fees for this class.</Text>
                }
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f8fafc',
    },
    summaryCard: {
        backgroundColor: '#ef4444', // Red for urgency
        margin: 16,
        padding: 20,
        borderRadius: 20,
    },
    summaryHeader: {
        flexDirection: 'row',
        alignItems: 'center',
        marginBottom: 10,
    },
    summaryTitle: {
        color: 'rgba(255,255,255,0.8)',
        fontSize: 14,
        fontWeight: 'bold',
        marginLeft: 10,
        textTransform: 'uppercase',
    },
    totalAmount: {
        color: '#fff',
        fontSize: 32,
        fontWeight: 'bold',
        marginBottom: 20,
    },
    nudgeBtn: {
        backgroundColor: '#fff',
        borderWidth: 0,
    },
    listHeader: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#1e293b',
        marginLeft: 20,
        marginBottom: 10,
    },
    list: {
        paddingHorizontal: 16,
    },
    studentRow: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        backgroundColor: '#fff',
        padding: 16,
        borderRadius: 12,
        marginBottom: 10,
        borderWidth: 1,
        borderColor: '#f1f5f9',
    },
    studentName: {
        fontSize: 15,
        fontWeight: '600',
        color: '#1e293b',
    },
    pendingLabel: {
        fontSize: 12,
        color: '#94a3b8',
    },
    studentAmount: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#ef4444',
    },
    emptyText: {
        textAlign: 'center',
        marginTop: 40,
        color: '#94a3b8',
    }
});

export default FeeManagementScreen; // FIXED: Added missing export