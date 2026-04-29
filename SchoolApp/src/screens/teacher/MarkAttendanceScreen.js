import { useMutation, useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import {
    Alert,
    FlatList,
    StyleSheet,
    Text,
    TouchableOpacity,
    View,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import Button from '../../components/common/Button';
import Loader from '../../components/common/Loader';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';
import { teacherService } from '../../services/teacherService';

const MarkAttendanceScreen = ({ navigation }) => {
    const { user } = useAuth();
    const [attendance, setAttendance] = useState({});

    // Fetch Students
    const { data, isLoading, isError } = useQuery({
        queryKey: ['students', user?.classId],
        queryFn: () => teacherService.getStudentsForAttendance(user?.classId),
    });

    // Mutation for submitting
    const mutation = useMutation({
        mutationFn: teacherService.markAttendance,
        onSuccess: () => {
            Alert.alert('Success', 'Attendance marked successfully');
            navigation.goBack();
        },
        onError: (error) => {
            Alert.alert('Error', error.message || 'Failed to mark attendance');
        },
    });

    const toggleAttendance = (studentId) => {
        if (!studentId) return;
        setAttendance((prev) => ({
            ...prev,
            [studentId]: prev[studentId] === 'Absent' ? 'Present' : 'Absent',
        }));
    };

    const handleSubmit = () => {
        // Logic: Students not toggled are assumed 'Present' by default
        const attendanceList = (data || []).map(student => ({
            studentId: student.id,
            status: attendance[student.id] || 'Present'
        }));

        const submissionData = {
            classId: user?.classId,
            date: new Date().toISOString().split('T')[0],
            attendance: attendanceList,
        };

        mutation.mutate(submissionData);
    };

    const renderItem = ({ item }) => {
        // Safe check for item
        if (!item) return null;

        const status = attendance[item.id] || 'Present';
        const isPresent = status === 'Present';

        return (
            <TouchableOpacity
                style={styles.studentItem}
                onPress={() => toggleAttendance(item.id)}
                activeOpacity={0.7}
            >
                <View style={styles.studentInfo}>
                    <Text style={styles.studentName}>{item.name || 'Unknown Student'}</Text>
                    <Text style={styles.rollNumber}>Roll No: {item.rollNumber || 'N/A'}</Text>
                </View>
                <View
                    style={[
                        styles.statusButton,
                        { backgroundColor: isPresent ? COLORS.success : COLORS.danger },
                    ]}
                >
                    <Text style={styles.statusText}>{status}</Text>
                </View>
            </TouchableOpacity>
        );
    };

    if (isLoading) return <Loader />;

    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.header}>
                <View>
                    <Text style={styles.title}>Mark Attendance</Text>
                    <Text style={styles.date}>{new Date().toDateString()}</Text>
                </View>
                <View style={styles.countBadge}>
                    <Text style={styles.countText}>{data?.length || 0} Students</Text>
                </View>
            </View>

            <FlatList
                data={data || []}
                renderItem={renderItem}
                // CRITICAL FIX: Safe Key Extractor
                keyExtractor={(item, index) => (item?.id ? item.id.toString() : index.toString())}
                contentContainerStyle={styles.list}
                ListEmptyComponent={
                    <View style={styles.emptyContainer}>
                        <Text style={styles.emptyText}>No students assigned to your class.</Text>
                    </View>
                }
            />

            <View style={styles.footer}>
                <Button
                    title="Submit Attendance"
                    onPress={handleSubmit}
                    loading={mutation.isPending}
                    disabled={!data || data.length === 0}
                />
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f8fafc',
    },
    header: {
        padding: 20,
        backgroundColor: COLORS.white,
        borderBottomWidth: 1,
        borderBottomColor: '#e2e8f0',
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    title: {
        fontSize: 20,
        fontWeight: 'bold',
        color: '#1e293b',
    },
    date: {
        fontSize: 14,
        color: '#64748b',
        marginTop: 2,
    },
    countBadge: {
        backgroundColor: '#f1f5f9',
        paddingHorizontal: 12,
        paddingVertical: 6,
        borderRadius: 20,
    },
    countText: {
        fontSize: 12,
        fontWeight: '600',
        color: '#475569',
    },
    list: {
        padding: 16,
    },
    studentItem: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        backgroundColor: COLORS.white,
        padding: 16,
        borderRadius: 12,
        marginBottom: 12,
        elevation: 2,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 1 },
        shadowOpacity: 0.1,
        shadowRadius: 2,
    },
    studentInfo: {
        flex: 1,
    },
    studentName: {
        fontSize: 16,
        fontWeight: '600',
        color: '#1e293b',
        marginBottom: 2,
    },
    rollNumber: {
        fontSize: 13,
        color: '#64748b',
    },
    statusButton: {
        paddingHorizontal: 14,
        paddingVertical: 8,
        borderRadius: 8,
        minWidth: 80,
        alignItems: 'center',
    },
    statusText: {
        color: COLORS.white,
        fontWeight: 'bold',
        fontSize: 13,
    },
    footer: {
        padding: 16,
        backgroundColor: COLORS.white,
        borderTopWidth: 1,
        borderTopColor: '#e2e8f0',
    },
    emptyContainer: {
        alignItems: 'center',
        marginTop: 50,
    },
    emptyText: {
        color: '#94a3b8',
    }
});

export default MarkAttendanceScreen;