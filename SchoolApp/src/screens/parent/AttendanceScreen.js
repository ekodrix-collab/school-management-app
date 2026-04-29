import { useQuery } from '@tanstack/react-query';
import { FlatList, StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import Badge from '../../components/common/Badge';
import Card from '../../components/common/Card';
import Loader from '../../components/common/Loader';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';
import { parentService } from '../../services/parentService';
import { formatDate } from '../../utils/helpers';

const AttendanceScreen = () => {
    const { user } = useAuth();
    const currentMonth = new Date().getMonth() + 1;
    const currentYear = new Date().getFullYear();

    const { data, isLoading } = useQuery({
        queryKey: ['attendance', user?.studentId, currentMonth, currentYear],
        queryFn: () =>
            parentService.getAttendance(user?.studentId, currentMonth, currentYear),
    });

    const renderItem = ({ item }) => (
        <Card>
            <View style={styles.row}>
                <View style={styles.dateContainer}>
                    <Text style={styles.date}>{formatDate(item.date)}</Text>
                    <Text style={styles.day}>{item.dayName}</Text>
                </View>
                <Badge
                    label={item.status}
                    variant={item.status === 'Present' ? 'success' : 'danger'}
                />
            </View>
        </Card>
    );

    if (isLoading) return <Loader />;

    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.title}>Attendance Record</Text>
                <Text style={styles.subtitle}>
                    {data?.presentDays || 0} / {data?.totalDays || 0} Days Present
                </Text>
            </View>

            <FlatList
                data={data?.attendance || []}
                renderItem={renderItem}
                keyExtractor={(item) => item.date}
                contentContainerStyle={styles.list}
                ListEmptyComponent={
                    <Text style={styles.emptyText}>No attendance records found</Text>
                }
            />
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: COLORS.gray[50],
    },
    header: {
        padding: 16,
        backgroundColor: COLORS.white,
        borderBottomWidth: 1,
        borderBottomColor: COLORS.gray[200],
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        color: COLORS.gray[900],
        marginBottom: 4,
    },
    subtitle: {
        fontSize: 14,
        color: COLORS.gray[600],
    },
    list: {
        padding: 16,
    },
    row: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    dateContainer: {
        flex: 1,
    },
    date: {
        fontSize: 16,
        fontWeight: '600',
        color: COLORS.gray[900],
        marginBottom: 2,
    },
    day: {
        fontSize: 14,
        color: COLORS.gray[600],
    },
    emptyText: {
        textAlign: 'center',
        color: COLORS.gray[500],
        marginTop: 40,
    },
});

export default AttendanceScreen;