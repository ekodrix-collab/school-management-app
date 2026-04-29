import { Ionicons } from '@expo/vector-icons';
import { ScrollView, StyleSheet, Text, View } from 'react-native';

const TeacherScheduleScreen = () => {
    const schedule = [
        { time: '09:30 AM', class: '10-A', sub: 'Mathematics', room: 'Room 201' },
        { time: '10:20 AM', class: '10-C', sub: 'Mathematics', room: 'Room 204' },
        { time: '11:10 AM', class: 'Staff Room', sub: 'Free Period', room: '-' },
    ];

    return (
        <ScrollView style={styles.container}>
            {schedule.map((item, index) => (
                <View key={index} style={styles.row}>
                    <View style={styles.timeCol}>
                        <Text style={styles.time}>{item.time}</Text>
                    </View>
                    <View style={styles.infoCol}>
                        <Text style={styles.class}>{item.class} • {item.sub}</Text>
                        <View style={styles.roomRow}>
                            <Ionicons name="location-outline" size={14} color="#64748b" />
                            <Text style={styles.room}>{item.room}</Text>
                        </View>
                    </View>
                </View>
            ))}
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#fff' },
    row: { flexDirection: 'row', padding: 20, borderBottomWidth: 1, borderBottomColor: '#f1f5f9' },
    timeCol: { width: 80 },
    time: { fontSize: 13, fontWeight: 'bold', color: '#2563eb' },
    class: { fontSize: 16, fontWeight: 'bold', color: '#1e293b' },
    roomRow: { flexDirection: 'row', alignItems: 'center', marginTop: 4 },
    room: { fontSize: 13, color: '#64748b', marginLeft: 4 }
});

export default TeacherScheduleScreen;