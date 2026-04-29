import { useMutation, useQuery } from '@tanstack/react-query';
import { useState } from 'react';
import {
    Alert,
    FlatList,
    StyleSheet,
    Text,
    View,
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import Button from '../../components/common/Button';
import Input from '../../components/common/Input';
import Loader from '../../components/common/Loader';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';
import { teacherService } from '../../services/teacherService';

const AddMarksScreen = ({ navigation }) => {
    const { user } = useAuth();
    const [marks, setMarks] = useState({});
    const [selectedExam, setSelectedExam] = useState(null);

    const { data: students, isLoading } = useQuery({
        queryKey: ['students', user?.classId],
        queryFn: () => teacherService.getStudentsForAttendance(user?.classId),
    });

    const mutation = useMutation({
        mutationFn: teacherService.addMarks,
        onSuccess: () => {
            Alert.alert('Success', 'Marks added successfully');
            navigation.goBack();
        },
        onError: (error) => {
            Alert.alert('Error', error.response?.data?.message || 'Failed to add marks');
        },
    });

    const handleMarksChange = (studentId, value) => {
        setMarks((prev) => ({
            ...prev,
            [studentId]: value,
        }));
    };

    const handleSubmit = () => {
        const marksData = {
            examId: selectedExam,
            subject: user?.subject,
            marks: Object.entries(marks).map(([studentId, marksObtained]) => ({
                studentId: parseInt(studentId),
                marksObtained: parseInt(marksObtained) || 0,
            })),
        };

        mutation.mutate(marksData);
    };

    const renderItem = ({ item }) => (
        <View style={styles.studentItem}>
            <View style={styles.studentInfo}>
                <Text style={styles.studentName}>{item.name}</Text>
                <Text style={styles.rollNumber}>Roll No: {item.rollNumber}</Text>
            </View>
            <Input
                value={marks[item.id]?.toString() || ''}
                onChangeText={(value) => handleMarksChange(item.id, value)}
                placeholder="Marks"
                keyboardType="numeric"
                style={styles.marksInput}
            />
        </View>
    );

    if (isLoading) return <Loader />;

    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.header}>
                <Text style={styles.title}>Add Marks</Text>
                <Text style={styles.subtitle}>Enter marks for each student</Text>
            </View>

            <FlatList
                data={students || []}
                renderItem={renderItem}
                keyExtractor={(item) => item.id.toString()}
                contentContainerStyle={styles.list}
            />

            <View style={styles.footer}>
                <Button
                    title="Submit Marks"
                    onPress={handleSubmit}
                    loading={mutation.isPending}
                />
            </View>
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
    },
    subtitle: {
        fontSize: 14,
        color: COLORS.gray[600],
        marginTop: 4,
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
        borderRadius: 8,
        marginBottom: 12,
    },
    studentInfo: {
        flex: 1,
    },
    studentName: {
        fontSize: 16,
        fontWeight: '500',
        color: COLORS.gray[900],
        marginBottom: 4,
    },
    rollNumber: {
        fontSize: 14,
        color: COLORS.gray[600],
    },
    marksInput: {
        width: 80,
        marginBottom: 0,
    },
    footer: {
        padding: 16,
        backgroundColor: COLORS.white,
        borderTopWidth: 1,
        borderTopColor: COLORS.gray[200],
    },
});

export default AddMarksScreen;