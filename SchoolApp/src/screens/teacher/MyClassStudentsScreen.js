import { Ionicons } from '@expo/vector-icons';
import { FlatList, StyleSheet, Text, View } from 'react-native';
import Card from '../../components/common/Card';

const MyClassStudentsScreen = () => {
    const students = [
        { id: '1', name: 'Adithya Kumar', roll: '1', rank: 'A+', att: '98%', fee: 'Paid' },
        { id: '2', name: 'Fathima Sahra', roll: '2', rank: 'A', att: '92%', fee: 'Paid' },
        { id: '3', name: 'Kevin Thomas', roll: '3', rank: 'B+', att: '85%', fee: 'Unpaid' },
    ];

    return (
        <View style={styles.container}>
            <FlatList
                data={students}
                keyExtractor={item => item.id}
                renderItem={({ item }) => (
                    <Card style={styles.card}>
                        <View style={styles.row}>
                            <View style={styles.mainInfo}>
                                <Text style={styles.name}>{item.roll}. {item.name}</Text>
                                <Text style={styles.subText}>Att: {item.att} | Grade: {item.rank}</Text>
                            </View>
                            <View style={[styles.feeBadge, { backgroundColor: item.fee === 'Paid' ? '#f0fdf4' : '#fef2f2' }]}>
                                <Text style={[styles.feeText, { color: item.fee === 'Paid' ? '#16a34a' : '#dc2626' }]}>{item.fee}</Text>
                            </View>
                            <Ionicons name="chevron-forward" size={20} color="#cbd5e1" />
                        </View>
                    </Card>
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#f8fafc', padding: 16 },
    card: { marginBottom: 10, padding: 15 },
    row: { flexDirection: 'row', alignItems: 'center' },
    mainInfo: { flex: 1 },
    name: { fontSize: 16, fontWeight: 'bold', color: '#1e293b' },
    subText: { fontSize: 13, color: '#64748b', marginTop: 2 },
    feeBadge: { paddingHorizontal: 10, paddingVertical: 4, borderRadius: 8, marginRight: 10 },
    feeText: { fontSize: 11, fontWeight: 'bold' }
});

export default MyClassStudentsScreen;