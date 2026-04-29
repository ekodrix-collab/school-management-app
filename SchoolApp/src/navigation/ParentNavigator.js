import { Ionicons } from '@expo/vector-icons';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';
import { createNativeStackNavigator } from '@react-navigation/native-stack';
import { useTheme } from '../contexts/ThemeContext';

// Screens
import NotificationsScreen from '../screens/common/NotificationsScreen';
import ProfileScreen from '../screens/common/ProfileScreen';
import AttendanceScreen from '../screens/parent/AttendanceScreen';
import BusTrackingScreen from '../screens/parent/BusTrackingScreen'; // Added
import DiaryScreen from '../screens/parent/DiaryScreen';
import FeesScreen from '../screens/parent/FeesScreen';
import HomeworkScreen from '../screens/parent/HomeworkScreen';
import LeaveRequestScreen from '../screens/parent/LeaveRequestScreen'; // Added
import MarksScreen from '../screens/parent/MarksScreen';
import ParentDashboard from '../screens/parent/ParentDashboard';
import TimetableScreen from '../screens/parent/TimetableScreen';

const Stack = createNativeStackNavigator();
const Tab = createBottomTabNavigator();

/**
 * HomeStack handles all screens that are reachable from the Dashboard
 */
const HomeStack = () => {
    return (
        <Stack.Navigator>
            <Stack.Screen
                name="ParentHome"
                component={ParentDashboard}
                options={{ headerShown: false }}
            />
            <Stack.Screen
                name="Attendance"
                component={AttendanceScreen}
                options={{
                    title: 'Attendance',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="Homework"
                component={HomeworkScreen}
                options={{
                    title: 'Homework',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="Marks"
                component={MarksScreen}
                options={{
                    title: 'Exam Marks',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="Fees"
                component={FeesScreen}
                options={{
                    title: 'Fee Details',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="Diary"
                component={DiaryScreen}
                options={{
                    title: 'School Diary',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="Timetable"
                component={TimetableScreen}
                options={{
                    title: 'Class Timetable',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="LeaveRequest"
                component={LeaveRequestScreen}
                options={{
                    title: 'Apply for Leave',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
            <Stack.Screen
                name="BusTracking"
                component={BusTrackingScreen}
                options={{
                    title: 'Live Bus Tracking',
                    headerStyle: { backgroundColor: '#fff' },
                    headerTintColor: '#1e293b',
                }}
            />
        </Stack.Navigator>
    );
};

/**
 * Main Parent Tab Navigator (Home, Notifications, Profile)
 */
const ParentNavigator = () => {
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

                    return (
                        <Ionicons
                            name={iconName}
                            size={size}
                            color={color}
                        />
                    );
                },
                tabBarActiveTintColor: theme.primaryColor,
                tabBarInactiveTintColor: '#94a3b8',
                tabBarStyle: {
                    backgroundColor: '#fff',
                    borderTopWidth: 1,
                    borderTopColor: '#f1f5f9',
                    paddingBottom: 8,
                    paddingTop: 8,
                    height: 65,
                },
                tabBarLabelStyle: {
                    fontSize: 12,
                    fontWeight: '500',
                    marginBottom: 5
                },
            })}
        >
            <Tab.Screen
                name="Home"
                component={HomeStack}
                options={{ title: 'Home' }}
            />
            <Tab.Screen
                name="Notifications"
                component={NotificationsScreen}
                options={{
                    title: 'Notifications',
                    headerShown: true,
                    headerTitle: 'School Notifications',
                }}
            />
            <Tab.Screen
                name="Profile"
                component={ProfileScreen}
                options={{
                    title: 'Profile',
                    headerShown: true,
                    headerTitle: 'Parent Profile',
                }}
            />
        </Tab.Navigator>
    );
};

export default ParentNavigator;