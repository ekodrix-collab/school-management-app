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
     */
    const { data: classStats } = useQuery({
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

                {/* --- 1. CLEAN HEADER (BLUE AREA) --- */}
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

                {/* --- 2. CLASS TEACHER SUMMARY (BELOW BLUE AREA) --- */}
                {user?.isClassTeacher && (
                    <View style={styles.classSection}>
                        <View style={styles.summaryCard}>
                            <View style={styles.summaryHeader}>
                                <Ionicons name="stats-chart" size={18} color={COLORS.primary} />
                                <Text style={styles.summaryTitle}>{t('teacher.my_class_summary')}</Text>
                            </View>

                            <View style={styles.statsGrid}>
                                <View style={[styles.statBox, { backgroundColor: '#f0fdf4' }]}>
                                    <Text style={[styles.statVal, { color: '#16a34a' }]}>{classStats?.presentToday || '42'}</Text>
                                    <Text style={styles.statLab}>{t('dashboard.attendance')}</Text>
                                </View>

                                <View style={[styles.statBox, { backgroundColor: '#fef2f2' }]}>
                                    <Text style={[styles.statVal, { color: '#dc2626' }]}>{classStats?.absentToday || '3'}</Text>
                                    <Text style={styles.statLab}>Absent</Text>
                                </View>

                                <TouchableOpacity
                                    style={[styles.statBox, { backgroundColor: '#fff7ed' }]}
                                    onPress={() => navigation.navigate('LeaveApproval')}
                                >
                                    <Text style={[styles.statVal, { color: '#ea580c' }]}>{classStats?.leaveRequestsPending || '2'}</Text>
                                    <Text style={styles.statLab}>Leaves</Text>
                                </TouchableOpacity>
                            </View>

                            <TouchableOpacity
                                style={styles.portfolioBtn}
                                onPress={() => navigation.navigate('MyClassStudents')}
                            >
                                <Text style={styles.portfolioBtnText}>{t('teacher.view_portfolios')}</Text>
                                <Ionicons name="arrow-forward" size={16} color={COLORS.primary} />
                            </TouchableOpacity>
                        </View>
                    </View>
                )}

                {/* --- 3. ACADEMIC TASKS GRID --- */}
                <View style={styles.section}>
                    <Text style={styles.sectionTitle}>{t('dashboard.quick_access')}</Text>
                    <View style={styles.cardsContainer}>
                        <DashboardCard title={t('dashboard.attendance')} icon="checkmark-circle-outline" onPress={() => navigation.navigate('MarkAttendance')} />
                        <DashboardCard title={t('dashboard.homework')} icon="create-outline" onPress={() => navigation.navigate('AddHomework')} />
                        <DashboardCard title={t('dashboard.marks')} icon="trophy-outline" onPress={() => navigation.navigate('AddMarks')} />
                        <DashboardCard title={t('dashboard.timetable')} icon="time-outline" onPress={() => navigation.navigate('MySchedule')} />
                        <DashboardCard title={t('dashboard.fees')} icon="wallet-outline" onPress={() => navigation.navigate('FeeManagement')} />
                        <DashboardCard title={t('common.notifications')} icon="notifications-outline" onPress={() => navigation.navigate('Notifications')} />
                    </View>
                </View>

                {/* --- 4. BOTTOM TIP --- */}
                <View style={styles.tipCard}>
                    <Ionicons name="information-circle" size={20} color={COLORS.primary} />
                    <Text style={styles.tipText}>
                        Tap on 'Leaves' in the summary box to quickly approve pending requests.
                    </Text>
                </View>

            </ScrollView>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#f1f5f9' },
    scrollContent: { paddingBottom: 40 },

    // Header
    header: {
        paddingHorizontal: 20,
        paddingTop: 20,
        paddingBottom: 30,
        borderBottomLeftRadius: 25,
        borderBottomRightRadius: 25
    },
    headerTop: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center' },
    greeting: { fontSize: 13, color: 'rgba(255,255,255,0.8)' },
    teacherName: { fontSize: 22, fontWeight: 'bold', color: '#fff' },
    roleBadge: { backgroundColor: 'rgba(255,255,255,0.2)', paddingHorizontal: 10, paddingVertical: 4, borderRadius: 10, alignSelf: 'flex-start', marginTop: 8 },
    roleText: { color: '#fff', fontSize: 11, fontWeight: 'bold' },
    logoutBtn: { width: 40, height: 40, borderRadius: 20, backgroundColor: 'rgba(255,255,255,0.2)', justifyContent: 'center', alignItems: 'center' },

    // Class Teacher Summary Card
    classSection: { paddingHorizontal: 20, marginTop: 20 },
    summaryCard: {
        backgroundColor: '#fff',
        borderRadius: 20,
        padding: 20,
        elevation: 4,
        shadowColor: '#000',
        shadowOpacity: 0.05,
        shadowRadius: 10
    },
    summaryHeader: { flexDirection: 'row', alignItems: 'center', marginBottom: 15 },
    summaryTitle: { fontSize: 15, fontWeight: 'bold', color: '#1e293b', marginLeft: 8 },

    statsGrid: { flexDirection: 'row', justifyContent: 'space-between' },
    statBox: {
        width: '31%',
        paddingVertical: 15,
        borderRadius: 15,
        alignItems: 'center'
    },
    statVal: { fontSize: 20, fontWeight: 'bold' },
    statLab: { fontSize: 10, color: '#64748b', fontWeight: 'bold', marginTop: 4, textTransform: 'uppercase' },

    portfolioBtn: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        marginTop: 20,
        paddingTop: 15,
        borderTopWidth: 1,
        borderTopColor: '#f1f5f9'
    },
    portfolioBtnText: { color: COLORS.primary, fontWeight: 'bold', fontSize: 14, marginRight: 5 },

    // Tasks Grid
    section: { paddingHorizontal: 20, marginTop: 30 },
    sectionTitle: { fontSize: 17, fontWeight: 'bold', color: '#1e293b', marginBottom: 15 },
    cardsContainer: { flexDirection: 'row', flexWrap: 'wrap', justifyContent: 'space-between' },

    // Tip Card
    tipCard: { margin: 20, padding: 15, backgroundColor: '#e0f2fe', borderRadius: 15, flexDirection: 'row', alignItems: 'center' },
    tipText: { fontSize: 12, color: '#0369a1', marginLeft: 10, flex: 1 }
});

export default TeacherDashboard;