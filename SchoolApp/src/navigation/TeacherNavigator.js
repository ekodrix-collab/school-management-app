import { Ionicons } from '@expo/vector-icons';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { useTheme } from '../contexts/ThemeContext';

// Screens - Common
import NotificationsScreen from '../screens/common/NotificationsScreen';
import ProfileScreen from '../screens/common/ProfileScreen';

// Screens - Teacher
import AddHomeworkScreen from '../screens/teacher/AddHomeworkScreen';
import AddMarksScreen from '../screens/teacher/AddMarksScreen';
import FeeManagementScreen from '../screens/teacher/FeeManagementScreen';
import MarkAttendanceScreen from '../screens/teacher/MarkAttendanceScreen';
import TeacherDashboard from '../screens/teacher/TeacherDashboard';

// NEW SCREENS - CRITICAL IMPORTS ADDED BELOW
import LeaveApprovalScreen from '../screens/teacher/LeaveApprovalScreen';
import MyClassStudentsScreen from '../screens/teacher/MyClassStudentsScreen';
import TeacherScheduleScreen from '../screens/teacher/TeacherScheduleScreen';

const Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();

const DashboardStack = () => {
    return (
        <Stack.Navigator>
            <Stack.Screen
                name="Dashboard"
                component={TeacherDashboard}
                options={{ headerShown: false }}
            />
            <Stack.Screen
                name="MarkAttendance"
                component={MarkAttendanceScreen}
                options={{
                    title: 'Mark Attendance',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="AddHomework"
                component={AddHomeworkScreen}
                options={{
                    title: 'Add Homework',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="AddMarks"
                component={AddMarksScreen}
                options={{
                    title: 'Add Marks',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="FeeManagement"
                component={FeeManagementScreen}
                options={{
                    title: 'Fee Management',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="LeaveApproval"
                component={LeaveApprovalScreen}
                options={{ title: 'Leave Requests' }}
            />
            <Stack.Screen
                name="MySchedule"
                component={TeacherScheduleScreen}
                options={{ title: 'My Timetable' }}
            />
            <Stack.Screen
                name="MyClassStudents"
                component={MyClassStudentsScreen}
                options={{ title: 'Student Portfolios' }}
            />
        </Stack.Navigator>
    );
};

const TeacherNavigator = () => {
    const { theme } = useTheme();

    return (
        <Tab.Navigator
            screenOptions={({ route }) => ({
                headerShown: false,
                tabBarIcon: ({ focused, color, size }) => {
                    let iconName;

                    if (route.name === 'Home') {
                        iconName = focused ? 'home' : 'home-outline';
                    } else if (route.name === 'Notifications') {
                        iconName = focused ? 'notifications' : 'notifications-outline';
                    } else if (route.name === 'Profile') {
                        iconName = focused ? 'person' : 'person-outline';
                    }

                    return <Ionicons name={iconName} size={size} color={color} />;
                },
                tabBarActiveTintColor: theme.primaryColor,
                tabBarInactiveTintColor: 'gray',
                tabBarStyle: {
                    height: 65,
                    paddingBottom: 8,
                    paddingTop: 8,
                }
            })}
        >
            <Tab.Screen
                name="Home"
                component={DashboardStack}
                options={{ title: 'Home' }}
            />
            <Tab.Screen
                name="Notifications"
                component={NotificationsScreen}
                options={{
                    title: 'Notifications',
                    headerShown: true,
                    headerTitle: 'Staff Notifications'
                }}
            />
            <Tab.Screen
                name="Profile"
                component={ProfileScreen}
                options={{
                    title: 'Profile',
                    headerShown: true,
                    headerTitle: 'Teacher Profile'
                }}
            />
        </Tab.Navigator>
    );
};

export default TeacherNavigator;