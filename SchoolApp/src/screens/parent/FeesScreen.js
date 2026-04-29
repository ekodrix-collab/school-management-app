import { useQuery } from '@tanstack/react-query';
import { FlatList, StyleSheet, Text, View } from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import Badge from '../../components/common/Badge';
import Card from '../../components/common/Card';
import Loader from '../../components/common/Loader';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';
import { parentService } from '../../services/parentService';
import { formatCurrency, formatDate } from '../../utils/helpers';

const FeesScreen = () => {
    const { user } = useAuth();

    const { data, isLoading } = useQuery({
        queryKey: ['fees', user?.studentId],
        queryFn: () => parentService.getFees(user?.studentId),
    });

    const renderItem = ({ item }) => (
        <Card>
            <View style={styles.header}>
                <Text style={styles.feeType}>{item.feeType}</Text>
                <Badge
                    label={item.status}
                    variant={item.status === 'Paid' ? 'success' : 'warning'}
                />
            </View>

            <View style={styles.row}>
                <Text style={styles.label}>Amount:</Text>
                <Text style={styles.amount}>{formatCurrency(item.amount)}</Text>
            </View>

            <View style={styles.row}>
                <Text style={styles.label}>Due Date:</Text>
                <Text style={styles.value}>{formatDate(item.dueDate)}</Text>
            </View>

            {item.paidDate && (
                <View style={styles.row}>
                    <Text style={styles.label}>Paid On:</Text>
                    <Text style={styles.value}>{formatDate(item.paidDate)}</Text>
                </View>
            )}
        </Card>
    );

    if (isLoading) return <Loader />;

    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.summary}>
                <View style={styles.summaryItem}>
                    <Text style={styles.summaryLabel}>Total Fees</Text>
                    <Text style={styles.summaryValue}>
                        {formatCurrency(data?.totalFees || 0)}
                    </Text>
                </View>
                <View style={styles.summaryItem}>
                    <Text style={styles.summaryLabel}>Paid</Text>
                    <Text style={[styles.summaryValue, { color: COLORS.success }]}>
                        {formatCurrency(data?.paidFees || 0)}
                    </Text>
                </View>
                <View style={styles.summaryItem}>
                    <Text style={styles.summaryLabel}>Pending</Text>
                    <Text style={[styles.summaryValue, { color: COLORS.warning }]}>
                        {formatCurrency(data?.pendingFees || 0)}
                    </Text>
                </View>
            </View>

            <FlatList
                data={data?.fees || []}
                renderItem={renderItem}
                keyExtractor={(item) => item.id.toString()}
                contentContainerStyle={styles.list}
                ListEmptyComponent={
                    <Text style={styles.emptyText}>No fee records found</Text>
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
    summary: {
        flexDirection: 'row',
        backgroundColor: COLORS.white,
        padding: 16,
        borderBottomWidth: 1,
        borderBottomColor: COLORS.gray[200],
    },
    summaryItem: {
        flex: 1,
        alignItems: 'center',
    },
    summaryLabel: {
        fontSize: 12,
        color: COLORS.gray[600],
        marginBottom: 4,
    },
    summaryValue: {
        fontSize: 16,
        fontWeight: 'bold',
        color: COLORS.gray[900],
    },
    list: {
        padding: 16,
    },
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: 12,
    },
    feeType: {
        fontSize: 16,
        fontWeight: '600',
        color: COLORS.gray[900],
    },
    row: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginTop: 6,
    },
    label: {
        fontSize: 14,
        color: COLORS.gray[600],
    },
    value: {
        fontSize: 14,
        color: COLORS.gray[900],
    },
    amount: {
        fontSize: 16,
        fontWeight: 'bold',
        color: COLORS.gray[900],
    },
    emptyText: {
        textAlign: 'center',
        color: COLORS.gray[500],
        marginTop: 40,
    },
});

export default FeesScreen;