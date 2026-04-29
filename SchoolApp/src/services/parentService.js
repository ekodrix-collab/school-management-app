
// Helper to simulate network delay for mock data
const delay = (ms) => new Promise(resolve => setTimeout(resolve, ms));

export const parentService = {
    // Get student dashboard data
    async getDashboard(studentId) {
        /* REAL API CALL:
        const response = await apiClient.get(`/parent/dashboard/${studentId}`);
        return response.data;
        */

        // MOCK DATA:
        await delay(500);
        return {
            studentName: "John Doe",
            pendingHomework: 3,
            unreadNotifications: 5,
            attendancePercent: "95%",
            rollNumber: "101",
            className: "Class 10-A"
        };
    },

    // Get attendance
    async getAttendance(studentId, month, year) {
        /* REAL API CALL:
        const response = await apiClient.get(`/parent/attendance/${studentId}`, {
            params: { month, year },
        });
        return response.data;
        */

        // MOCK DATA:
        await delay(500);
        return {
            presentDays: 18,
            totalDays: 20,
            attendance: [
                { id: 1, date: '2023-10-25', dayName: 'Wednesday', status: 'Present' },
                { id: 2, date: '2023-10-24', dayName: 'Tuesday', status: 'Present' },
                { id: 3, date: '2023-10-23', dayName: 'Monday', status: 'Absent' },
                { id: 4, date: '2023-10-20', dayName: 'Friday', status: 'Present' },
            ]
        };
    },

    // Get homework
    async getHomework(studentId) {
        /* REAL API CALL:
        const response = await apiClient.get(`/parent/homework/${studentId}`);
        return response.data;
        */

        // MOCK DATA:
        await delay(800);
        return [
            {
                id: 1,
                subject: 'Mathematics',
                title: 'Algebra Exercise 5.2',
                description: 'Complete problems 1 to 10 from page 142 of the textbook.',
                date: '2023-10-25',
                dueDate: '2023-10-27'
            },
            {
                id: 2,
                subject: 'Science',
                title: 'Photosynthesis Diagram',
                description: 'Draw and label the process of photosynthesis in your notebook.',
                date: '2023-10-24',
                dueDate: '2023-10-26'
            },
            {
                id: 3,
                subject: 'English',
                title: 'Essay Writing',
                description: 'Write a 200-word essay on the importance of sports in a students life.',
                date: '2023-10-23',
                dueDate: '2023-10-25'
            }
        ];
    },

    // Get marks
    async getMarks(studentId) {
        /* REAL API CALL:
        const response = await apiClient.get(`/parent/marks/${studentId}`);
        return response.data;
        */

        // MOCK DATA (Grouped by Exam for SectionList)
        await delay(600);
        return [
            {
                title: 'First Term Examination',
                data: [
                    { id: 1, subject: 'Mathematics', marksObtained: 92, totalMarks: 100 },
                    { id: 2, subject: 'Science', marksObtained: 85, totalMarks: 100 },
                    { id: 3, subject: 'English', marksObtained: 88, totalMarks: 100 },
                    { id: 4, subject: 'Social Science', marksObtained: 79, totalMarks: 100 },
                ]
            },
            {
                title: 'Monthly Test - September',
                data: [
                    { id: 5, subject: 'Mathematics', marksObtained: 23, totalMarks: 25 },
                    { id: 6, subject: 'Science', marksObtained: 20, totalMarks: 25 },
                ]
            }
        ];
    },

    // Get fees
    async getFees(studentId) {
        /* REAL API CALL:
        const response = await apiClient.get(`/parent/fees/${studentId}`);
        return response.data;
        */

        // MOCK DATA:
        await delay(700);
        return {
            totalFees: 15000,
            paidFees: 10000,
            pendingFees: 5000,
            fees: [
                { id: 1, feeType: 'Tuition Fee - Term 1', amount: 5000, status: 'Paid', dueDate: '2023-06-10', paidDate: '2023-06-05' },
                { id: 2, feeType: 'Tuition Fee - Term 2', amount: 5000, status: 'Paid', dueDate: '2023-09-10', paidDate: '2023-09-08' },
                { id: 3, feeType: 'Tuition Fee - Term 3', amount: 5000, status: 'Unpaid', dueDate: '2023-12-10' },
                { id: 4, feeType: 'Bus Fee - Oct', amount: 1500, status: 'Unpaid', dueDate: '2023-10-10' },
            ]
        };
    },

    // Get notifications
    async getNotifications() {
        /* REAL API CALL:
        const response = await apiClient.get('/parent/notifications');
        return response.data;
        */

        // MOCK DATA:
        await delay(500);
        return [
            { id: 1, title: 'School Closed', message: 'School will be closed tomorrow for Pooja holidays.', date: '2023-10-25', isRead: false },
            { id: 2, title: 'Exam Schedule', message: 'The second term exam schedule for Class 10 is now available in the downloads section.', date: '2023-10-20', isRead: true },
            { id: 3, title: 'PTM Meeting', message: 'Parent Teacher Meeting is scheduled for this Saturday at 10:00 AM.', date: '2023-10-18', isRead: true },
        ];
    },

    // Get Timetable
    async getTimetable(studentId) {
        /* REAL API CALL:
        const response = await apiClient.get(`/parent/timetable/${studentId}`);
        return response.data;
        */

        await delay(500);
        return [
            { day: 'Monday', periods: ['Math', 'English', 'PT', 'Malayalam', 'Science', 'Social', 'Hindi'] },
            { day: 'Tuesday', periods: ['English', 'Math', 'Science', 'Malayalam', 'IT', 'Social', 'Arts'] },
            { day: 'Wednesday', periods: ['Hindi', 'English', 'Math', 'Science', 'Malayalam', 'Social', 'Lib'] },
            { day: 'Thursday', periods: ['Science', 'Social', 'Math', 'English', 'PT', 'Malayalam', 'IT'] },
            { day: 'Friday', periods: ['Malayalam', 'Math', 'English', 'Science', 'Social', 'Hindi', 'PT'] },
        ];
    },

    // Get Diary Remarks
    async getDiaryRemarks(studentId) {
        /* REAL API CALL:
        const response = await apiClient.get(`/parent/diary/${studentId}`);
        return response.data;
        */

        await delay(500);
        return [
            { id: 1, date: '2023-10-26', teacher: 'Mrs. Bindu', note: 'Excellent performance in today’s Science quiz!', type: 'Appreciation' },
            { id: 2, date: '2023-10-24', teacher: 'Mr. Rajesh', note: 'Please ensure he completes the pending Math homework.', type: 'Warning' },
        ];
    },

    // Get Syllabus Tracker
    async getSyllabus(studentId) {
        /* REAL API CALL:
        const response = await apiClient.get(`/parent/syllabus/${studentId}`);
        return response.data;
        */

        await delay(500);
        return [
            { subject: 'Mathematics', currentChapter: 'Chapter 5: Decimals', status: 'In Progress' },
            { subject: 'English', currentChapter: 'Unit 3: The Little Prince', status: 'Completed' },
            { subject: 'Malayalam', currentChapter: 'Chapter 4: Poetry', status: 'In Progress' },
        ];
    },

    // Get Kerala Holiday Calendar
    async getHolidays() {
        /* REAL API CALL:
        const response = await apiClient.get('/parent/holidays');
        return response.data;
        */

        await delay(300);
        return [
            { id: 1, name: 'Mahanavami', date: '2023-10-23' },
            { id: 2, name: 'Vijayadashami', date: '2023-10-24' },
            { id: 3, name: 'Deepavali', date: '2023-11-12' },
            { id: 4, name: 'Christmas', date: '2023-12-25' },
        ];
    },

    // Submit Leave Request (New Feature)
    async submitLeaveRequest(leaveData) {
        /* REAL API CALL:
        const response = await apiClient.post('/parent/leave-request', leaveData);
        return response.data;
        */

        await delay(1000);
        return { success: true };
    },

    // Get Live Bus Location (New Feature)
    async getBusLocation(busId) {
        /* REAL API CALL:
        const response = await apiClient.get(`/parent/bus-location/${busId}`);
        return response.data;
        */

        await delay(400);
        return {
            latitude: 10.8505,
            longitude: 76.2711,
            nextStop: 'Aluva Junction',
            eta: '4:15 PM',
            status: 'Moving'
        };
    }
};