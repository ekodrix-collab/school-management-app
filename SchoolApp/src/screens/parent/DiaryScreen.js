import { FlatList, StyleSheet, Text, View } from 'react-native';
import Card from '../../components/common/Card';
import { formatDate } from '../../utils/helpers';

const DiaryScreen = () => {
    const remarks = [
        { id: '1', date: '2023-10-26', teacher: 'Mrs. Bindu', note: 'Excellent performance in today’s Science quiz!', type: 'Appreciation' },
        { id: '2', date: '2023-10-24', teacher: 'Mr. Rajesh', note: 'Please ensure he completes the pending Math homework.', type: 'Warning' },
    ];

    return (
        <View style={styles.container}>
            <FlatList
                data={remarks}
                keyExtractor={item => item.id}
                renderItem={({ item }) => (
                    <Card style={[styles.card, { borderLeftColor: item.type === 'Appreciation' ? '#22c55e' : '#f59e0b' }]}>
                        <View style={styles.header}>
                            <Text style={styles.date}>{formatDate(item.date)}</Text>
                            <Text style={styles.teacher}>{item.teacher}</Text>
                        </View>
                        <Text style={styles.note}>{item.note}</Text>
                    </Card>
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#f8fafc', padding: 16 },
    card: { borderLeftWidth: 5, marginBottom: 12 },
    header: { flexDirection: 'row', justifyContent: 'space-between', marginBottom: 8 },
    date: { fontSize: 12, color: '#64748b' },
    teacher: { fontSize: 13, fontWeight: 'bold', color: '#1e293b' },
    note: { fontSize: 15, color: '#334155', lineHeight: 22 }
});

export default DiaryScreen;