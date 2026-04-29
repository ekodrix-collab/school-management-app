import { useState } from 'react';
import { Alert, StyleSheet, Text, TextInput, View } from 'react-native';
import Button from '../../components/common/Button';

const LeaveRequestScreen = ({ navigation }) => {
    const [reason, setReason] = useState('');
    const [date, setDate] = useState('');

    const handleSubmit = () => {
        Alert.alert("Submitted", "Leave request sent to Class Teacher.");
        navigation.goBack();
    };

    return (
        <View style={styles.container}>
            <Text style={styles.label}>Leave Date (DD/MM/YYYY)</Text>
            <TextInput style={styles.input} value={date} onChangeText={setDate} placeholder="e.g. 28/10/2023" />

            <Text style={styles.label}>Reason</Text>
            <TextInput
                style={[styles.input, { height: 100 }]}
                multiline
                value={reason}
                onChangeText={setReason}
                placeholder="Reason for leave..."
            />

            <Button title="Submit Request" onPress={handleSubmit} />
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, padding: 20, backgroundColor: '#fff' },
    label: { fontSize: 14, fontWeight: 'bold', marginBottom: 8, color: '#475569' },
    input: { borderWidth: 1, borderColor: '#e2e8f0', borderRadius: 8, padding: 12, marginBottom: 20 }
});

export default LeaveRequestScreen;