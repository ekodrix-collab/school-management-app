import { Alert, FlatList, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import Card from '../../components/common/Card';

const LeaveApprovalScreen = () => {
    const requests = [
        { id: '1', name: 'Adithya Kumar', reason: 'Viral Fever', date: '28 Oct - 30 Oct' },
        { id: '2', name: 'Rahul Nair', reason: 'Sister’s Marriage', date: '01 Nov' },
    ];

    const handleAction = (name, action) => {
        Alert.alert("Success", `Leave for ${name} has been ${action}.`);
    };

    return (
        <View style={styles.container}>
            <FlatList
                data={requests}
                keyExtractor={item => item.id}
                renderItem={({ item }) => (
                    <Card style={styles.card}>
                        <Text style={styles.name}>{item.name}</Text>
                        <Text style={styles.reason}>{item.reason}</Text>
                        <Text style={styles.date}>Date: {item.date}</Text>
                        <View style={styles.actions}>
                            <TouchableOpacity onPress={() => handleAction(item.name, 'Rejected')} style={styles.reject}>
                                <Text style={styles.btnText}>Reject</Text>
                            </TouchableOpacity>
                            <TouchableOpacity onPress={() => handleAction(item.name, 'Approved')} style={styles.approve}>
                                <Text style={styles.btnText}>Approve</Text>
                            </TouchableOpacity>
                        </View>
                    </Card>
                )}
            />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#f8fafc', padding: 16 },
    card: { padding: 16, marginBottom: 12 },
    name: { fontSize: 16, fontWeight: 'bold', color: '#1e293b' },
    reason: { fontSize: 14, color: '#64748b', marginTop: 4 },
    date: { fontSize: 13, color: '#94a3b8', marginTop: 4 },
    actions: { flexDirection: 'row', justifyContent: 'flex-end', marginTop: 15 },
    approve: { backgroundColor: '#22c55e', paddingHorizontal: 20, paddingVertical: 8, borderRadius: 8, marginLeft: 10 },
    reject: { backgroundColor: '#ef4444', paddingHorizontal: 20, paddingVertical: 8, borderRadius: 8 },
    btnText: { color: '#fff', fontWeight: 'bold' }
});

export default LeaveApprovalScreen;