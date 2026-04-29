
// Helper to simulate API delay
const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

export const teacherService = {
    // Get students list for attendance
    async getStudentsForAttendance(classId) {
        /* Real API Code (Uncomment when backend is ready)
        const response = await apiClient.get(`/teacher/students/${classId}`);
        return response.data;
        */

        await delay(800); // Simulate network lag
        // Mock Data
        return [
            { id: 1, name: 'Adithya Kumar', rollNumber: '101' },
            { id: 2, name: 'Fathima Sahra', rollNumber: '102' },
            { id: 3, name: 'Rahul Nair', rollNumber: '103' },
            { id: 4, name: 'Sreya S', rollNumber: '104' },
            { id: 5, name: 'Kevin Thomas', rollNumber: '105' },
            { id: 6, name: 'Meera Jasmine', rollNumber: '106' },
        ];
    },

    // Mark attendance
    async markAttendance(attendanceData) {
        /* Real API Code
        const response = await apiClient.post('/teacher/attendance', attendanceData);
        return response.data;
        */

        await delay(1000);
        return { success: true, message: 'Attendance recorded' };
    },

    // Add homework
    async addHomework(homeworkData) {
        /* Real API Code
        const response = await apiClient.post('/teacher/homework', homeworkData);
        return response.data;
        */

        await delay(1000);
        return { success: true };
    },

    // Get exams list
    async getExams() {
        /* Real API Code
        const response = await apiClient.get('/teacher/exams');
        return response.data;
        */

        await delay(500);
        return [
            { id: 1, name: 'First Term Examination' },
            { id: 2, name: 'Monthly Test - October' },
            { id: 3, name: 'Half Yearly Examination' },
        ];
    },

    // Add marks
    async addMarks(marksData) {
        /* Real API Code
        const response = await apiClient.post('/teacher/marks', marksData);
        return response.data;
        */

        await delay(1000);
        return { success: true };
    },

    // Get Fee Summary for Nudging (New Feature)
    async getFeeSummary(classId) {
        /* Real API Code
        const response = await apiClient.get(`/teacher/fee-summary/${classId}`);
        return response.data;
        */

        await delay(700);
        return {
            totalPending: 145000,
            pendingStudents: [
                { id: 1, name: 'Adithya Kumar', amount: 5000 },
                { id: 3, name: 'Rahul Nair', amount: 1200 },
                { id: 5, name: 'Kevin Thomas', amount: 5000 },
            ]
        };
    },

    // Send nudge notifications to parents
    async nudgeParents(studentIds) {
        /* Real API Code
        const response = await apiClient.post('/teacher/nudge-fees', { studentIds });
        return response.data;
        */

        await delay(1000);
        return { success: true };
    },

    // Add to teacherService
    async getLeaveRequests(classId) {
        await delay(500);
        return [
            { id: 1, studentName: 'Adithya Kumar', reason: 'Fever and cold', days: '2 Days', date: 'Oct 28 - Oct 29' },
            { id: 2, studentName: 'Kevin Thomas', reason: 'Family Function', days: '1 Day', date: 'Oct 30' },
        ];
    },

    async getTeacherSchedule(teacherId) {
        await delay(500);
        return [
            { period: '1', time: '09:30 AM', class: '10-A', subject: 'Maths', room: '201' },
            { period: '2', time: '10:20 AM', class: '10-B', subject: 'Maths', room: '204' },
            { period: '3', time: '11:10 AM', class: '9-A', subject: 'Maths', room: '105' },
        ];
    },

    // Add to teacherService object
    async getClassOverview(classId) {
        await delay(600);
        return {
            className: "10-A",
            totalStudents: 45,
            presentToday: 42,
            absentToday: 3,
            pendingFeesCount: 5,
            leaveRequestsPending: 2,
            recentClassNotice: "Bring Science Record tomorrow."
        };
    },
};