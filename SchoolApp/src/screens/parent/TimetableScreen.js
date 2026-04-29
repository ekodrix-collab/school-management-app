import { ScrollView, StyleSheet, Text, View } from 'react-native';

const TimetableScreen = () => {
    const timetable = [
        { day: 'Mon', periods: ['Math', 'Eng', 'Malayalam', 'Break', 'Sci', 'Soc', 'IT'] },
        { day: 'Tue', periods: ['Eng', 'Math', 'Sci', 'Break', 'Mal', 'Arts', 'PT'] },
        { day: 'Wed', periods: ['Hindi', 'Eng', 'Soc', 'Break', 'Math', 'Sci', 'Mal'] },
    ];

    return (
        <ScrollView style={styles.container}>
            <View style={styles.table}>
                {timetable.map((item, index) => (
                    <View key={index} style={styles.row}>
                        <View style={styles.dayCell}><Text style={styles.dayText}>{item.day}</Text></View>
                        {item.periods.map((p, i) => (
                            <View key={i} style={[styles.cell, p === 'Break' && styles.break]}>
                                <Text style={styles.cellText}>{p}</Text>
                            </View>
                        ))}
                    </View>
                ))}
            </View>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#fff', padding: 10 },
    row: { flexDirection: 'row', borderBottomWidth: 1, borderBottomColor: '#e2e8f0' },
    dayCell: { width: 50, padding: 10, backgroundColor: '#f1f5f9', justifyContent: 'center' },
    dayText: { fontWeight: 'bold', color: '#475569' },
    cell: { flex: 1, padding: 10, alignItems: 'center', justifyContent: 'center', borderLeftWidth: 1, borderLeftColor: '#e2e8f0' },
    break: { backgroundColor: '#fef2f2' },
    cellText: { fontSize: 10, textAlign: 'center' }
});

export default TimetableScreen;