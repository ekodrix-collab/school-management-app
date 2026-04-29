import { useMutation } from '@tanstack/react-query';
import { useState } from 'react';
import {
    Alert,
    KeyboardAvoidingView,
    Platform,
    ScrollView,
    StyleSheet
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';
import Button from '../../components/common/Button';
import Input from '../../components/common/Input';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';
import { teacherService } from '../../services/teacherService';

const AddHomeworkScreen = ({ navigation }) => {
    const { user } = useAuth();
    const [title, setTitle] = useState('');
    const [description, setDescription] = useState('');
    const [subject, setSubject] = useState('');
    const [errors, setErrors] = useState({});

    const mutation = useMutation({
        mutationFn: teacherService.addHomework,
        onSuccess: () => {
            Alert.alert('Success', 'Homework added successfully');
            navigation.goBack();
        },
        onError: (error) => {
            Alert.alert('Error', error.response?.data?.message || 'Failed to add homework');
        },
    });

    const validate = () => {
        const newErrors = {};

        if (!subject) newErrors.subject = 'Subject is required';
        if (!title) newErrors.title = 'Title is required';
        if (!description) newErrors.description = 'Description is required';

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    const handleSubmit = () => {
        if (!validate()) return;

        const homeworkData = {
            classId: user?.classId,
            subject,
            title,
            description,
            assignedDate: new Date().toISOString(),
        };

        mutation.mutate(homeworkData);
    };

    return (
        <SafeAreaView style={styles.container}>
            <KeyboardAvoidingView
                behavior={Platform.OS === 'ios' ? 'padding' : 'height'}
                style={styles.keyboardView}
            >
                <ScrollView contentContainerStyle={styles.scrollContent}>
                    <Input
                        label="Subject"
                        value={subject}
                        onChangeText={setSubject}
                        placeholder="Enter subject"
                        error={errors.subject}
                    />

                    <Input
                        label="Title"
                        value={title}
                        onChangeText={setTitle}
                        placeholder="Enter homework title"
                        error={errors.title}
                    />

                    <Input
                        label="Description"
                        value={description}
                        onChangeText={setDescription}
                        placeholder="Enter homework description"
                        multiline
                        numberOfLines={5}
                        style={styles.textArea}
                        error={errors.description}
                    />

                    <Button
                        title="Add Homework"
                        onPress={handleSubmit}
                        loading={mutation.isPending}
                    />
                </ScrollView>
            </KeyboardAvoidingView>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: COLORS.white,
    },
    keyboardView: {
        flex: 1,
    },
    scrollContent: {
        padding: 16,
    },
    textArea: {
        height: 120,
        textAlignVertical: 'top',
    },
});

export default AddHomeworkScreen;