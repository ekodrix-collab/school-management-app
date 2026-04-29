import { Ionicons } from '@expo/vector-icons';
import { useQuery } from '@tanstack/react-query';
import { useTranslation } from 'react-i18next';
import {
    ScrollView,
    StatusBar,
    StyleSheet,
    Text,
    TouchableOpacity,
    View
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

// Internal Imports
import DashboardCard from '../../components/cards/DashboardCard';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';
import { teacherService } from '../../services/teacherService';
import { getGreeting } from '../../utils/helpers';

const TeacherDashboard = ({ navigation }) => {
    const { user, logout } = useAuth();
    const { t } = useTranslation();

    /**
     * FETCH CLASS STATS
     * Only runs if the user is a 'Class Teacher'
     */
    const { data: classStats, isLoading: statsLoading } = useQuery({
        queryKey: ['classOverview', user?.classId],
        queryFn: () => teacherService.getClassOverview(user?.classId),
        enabled: !!user?.isClassTeacher,
    });

    return (
        <SafeAreaView style={styles.container} edges={['top']}>
            <StatusBar barStyle="light-content" />
            <ScrollView
                contentContainerStyle={styles.scrollContent}
                showsVerticalScrollIndicator={false}
            >

                {/* --- 1. PREMIUM HEADER --- */}
                <View style={[styles.header, { backgroundColor: COLORS.primary || '#2563eb' }]}>
                    <View style={styles.headerTop}>
                        <View>
                            <Text style={styles.greeting}>{getGreeting()} 👋</Text>
                            <Text style={styles.teacherName}>{user?.name}</Text>
                            <View style={styles.roleBadge}>
                                <Text style={styles.roleText}>
                                    {user?.isClassTeacher
                                        ? `${t('teacher.class_teacher')}: ${user.className}`
                                        : t('teacher.subject_teacher')}
                                </Text>
                            </View>
                        </View>
                        <TouchableOpacity style={styles.logoutBtn} onPress={logout}>
                            <Ionicons name="log-out-outline" size={22} color="#fff" />
                        </TouchableOpacity>
                    </View>
                </View>

                {/* --- 2. CLASS TEACHER CONSOLE (Conditional) --- */}
                {user?.isClassTeacher && (
                    <View style={styles.classSection}>
                        <Text style={styles.sectionTitle}>{t('teacher.my_class_summary')}</Text>

                        {/* STATS GRID - Updated with more spacing */}
                        <View style={styles.statsGrid}>
                            <View style={[styles.statBox, { borderLeftColor: '#22c55e' }]}>
                                <Text style={styles.statVal}>{classStats?.presentToday || '42'}</Text>
                                <Text style={styles.statLab}>{t('dashboard.attendance')}</Text>
                            </View>

                            <View style={[styles.statBox, { borderLeftColor: '#ef4444' }]}>
                                <Text style={styles.statVal}>{classStats?.absentToday || '3'}</Text>
                                <Text style={styles.statLab}>Absent</Text>
                            </View>

                            <TouchableOpacity
                                style={[styles.statBox, { borderLeftColor: '#f59e0b' }]}
                                onPress={() => navigation.navigate('LeaveApproval')}
                                activeOpacity={0.7}
                            >
                                <Text style={styles.statVal}>{classStats?.leaveRequestsPending || '2'}</Text>
                                <Text style={styles.statLab}>Leaves</Text>
                                <View style={styles.actionDot} />
                            </TouchableOpacity>
                        </View>

                        <TouchableOpacity
                            style={styles.primaryActionBtn}
                            onPress={() => navigation.navigate('MyClassStudents')}
                        >
                            <Ionicons name="people-circle" size={24} color="#fff" />
                            <Text style={styles.primaryActionText}>{t('teacher.view_portfolios')}</Text>
                        </TouchableOpacity>
                    </View>
                )}

                {/* --- 3. ACADEMIC TASKS GRID (For All Teachers) --- */}
                <View style={styles.section}>
                    <Text style={styles.sectionTitle}>{t('dashboard.quick_access')}</Text>
                    <View style={styles.cardsContainer}>
                        <DashboardCard
                            title={t('dashboard.attendance')}
                            icon="checkmark-circle-outline"
                            onPress={() => navigation.navigate('MarkAttendance')}
                        />
                        <DashboardCard
                            title={t('dashboard.homework')}
                            icon="create-outline"
                            onPress={() => navigation.navigate('AddHomework')}
                        />
                        <DashboardCard
                            title={t('dashboard.marks')}
                            icon="trophy-outline"
                            onPress={() => navigation.navigate('AddMarks')}
                        />
                        <DashboardCard
                            title={t('dashboard.timetable')}
                            icon="time-outline"
                            onPress={() => navigation.navigate('MySchedule')}
                        />
                        <DashboardCard
                            title={t('dashboard.fees')}
                            icon="wallet-outline"
                            onPress={() => navigation.navigate('FeeManagement')}
                        />
                        <DashboardCard
                            title={t('common.notifications')}
                            icon="notifications-outline"
                            onPress={() => navigation.navigate('Notifications')}
                        />
                    </View>
                </View>

                {/* --- 4. TEACHER TIP --- */}
                <View style={styles.tipCard}>
                    <Ionicons name="bulb" size={20} color="#f59e0b" />
                    <Text style={styles.tipText}>
                        Tip: You can now approve leave requests directly from the Class Summary section.
                    </Text>
                </View>

            </ScrollView>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f8fafc'
    },
    scrollContent: {
        paddingBottom: 40
    },

    // Header Styling
    header: {
        paddingHorizontal: 20,
        paddingTop: 20,
        paddingBottom: 65,
        borderBottomLeftRadius: 35,
        borderBottomRightRadius: 35,
        elevation: 5,
    },
    headerTop: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center'
    },
    greeting: {
        fontSize: 13,
        color: 'rgba(255,255,255,0.8)',
        fontWeight: '600'
    },
    teacherName: {
        fontSize: 22,
        fontWeight: 'bold',
        color: '#fff',
        marginTop: 2
    },
    roleBadge: {
        backgroundColor: 'rgba(255,255,255,0.2)',
        paddingHorizontal: 10,
        paddingVertical: 4,
        borderRadius: 12,
        alignSelf: 'flex-start',
        marginTop: 8
    },
    roleText: {
        color: '#fff',
        fontSize: 11,
        fontWeight: 'bold',
        textTransform: 'uppercase'
    },
    logoutBtn: {
        width: 42,
        height: 42,
        borderRadius: 21,
        backgroundColor: 'rgba(255,255,255,0.2)',
        justifyContent: 'center',
        alignItems: 'center'
    },

    // Class Teacher Summary Section
    classSection: {
        paddingHorizontal: 20,
        marginTop: -35
    },
    sectionTitle: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#1e293b',
        marginBottom: 15,
        marginLeft: 5
    },
    statsGrid: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginBottom: 25 // ADDED: More margin bottom to separate from the button below
    },
    statBox: {
        backgroundColor: '#fff',
        width: '31%',
        paddingVertical: 18, // UPDATED: More vertical padding for better look
        paddingHorizontal: 10,
        borderRadius: 18,
        elevation: 8,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowRadius: 10,
        borderLeftWidth: 5,
        position: 'relative'
    },
    statVal: {
        fontSize: 22, // Slightly larger
        fontWeight: 'bold',
        color: '#1e293b',
        textAlign: 'center'
    },
    statLab: {
        fontSize: 10,
        color: '#64748b',
        fontWeight: 'bold',
        marginTop: 4,
        textTransform: 'uppercase',
        textAlign: 'center'
    },
    actionDot: {
        position: 'absolute',
        top: 8,
        right: 8,
        width: 6,
        height: 6,
        borderRadius: 3,
        backgroundColor: '#f59e0b'
    },
    primaryActionBtn: {
        backgroundColor: '#1e293b',
        padding: 18,
        borderRadius: 20,
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center',
        elevation: 5,
        marginTop: 5 // Spacing from grid
    },
    primaryActionText: {
        color: '#fff',
        fontWeight: 'bold',
        marginLeft: 10,
        fontSize: 15
    },

    // Daily Tasks Grid
    section: {
        paddingHorizontal: 20,
        marginTop: 35 // ADDED: More space between Class Summary and Tasks
    },
    cardsContainer: {
        flexDirection: 'row',
        flexWrap: 'wrap',
        justifyContent: 'space-between'
    },

    // Bottom Tip Card
    tipCard: {
        margin: 20,
        padding: 15,
        backgroundColor: '#fffbeb',
        borderRadius: 15,
        flexDirection: 'row',
        alignItems: 'center',
        borderWidth: 1,
        borderColor: '#fef3c7'
    },
    tipText: {
        fontSize: 12,
        color: '#92400e',
        marginLeft: 10,
        flex: 1,
        lineHeight: 18
    }
});

export default TeacherDashboard;