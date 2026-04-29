import { Ionicons } from '@expo/vector-icons';
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
import { COLORS } from '../../constants/colors';
import { useTheme } from '../../contexts/ThemeContext';
import { useAuth } from '../../hooks/useAuth';

/**
 * REUSABLE COMPONENT: QuickCard
 * Handles the individual menu items in the dashboard grid
 */
const QuickCard = ({ title, icon, onPress, badgeCount, color }) => {
    return (
        <TouchableOpacity
            style={styles.quickCard}
            onPress={onPress}
            activeOpacity={0.8}
        >
            <View style={[styles.iconCircle, { backgroundColor: color || COLORS.primary }]}>
                <Ionicons name={icon} size={28} color="#fff" />
            </View>
            <Text style={styles.quickCardTitle} numberOfLines={1}>{title}</Text>
            {badgeCount > 0 && (
                <View style={styles.badge}>
                    <Text style={styles.badgeText}>{badgeCount}</Text>
                </View>
            )}
        </TouchableOpacity>
    );
};

/**
 * REUSABLE COMPONENT: ActivityItem
 * Displays recent updates in a list format
 */
const ActivityItem = ({ icon, iconColor, title, subtitle, time }) => (
    <View style={styles.activityItem}>
        <View style={[styles.activityIcon, { backgroundColor: iconColor + '15' }]}>
            <Ionicons name={icon} size={20} color={iconColor} />
        </View>
        <View style={styles.activityContent}>
            <Text style={styles.activityTitle}>{title}</Text>
            <Text style={styles.activitySubtitle} numberOfLines={1}>{subtitle}</Text>
        </View>
        <Text style={styles.activityTime}>{time}</Text>
    </View>
);

const ParentDashboard = ({ navigation }) => {
    const { user, logout } = useAuth();
    const { theme } = useTheme();
    const { t } = useTranslation();

    /**
     * Menu Configuration
     * Includes all 8 core features for the Parent Dashboard
     */
    const quickActions = [
        { id: 1, title: t('dashboard.attendance'), icon: 'calendar-outline', screen: 'Attendance', color: '#3b82f6', badge: 0 },
        { id: 2, title: t('dashboard.homework'), icon: 'book-outline', screen: 'Homework', color: '#8b5cf6', badge: 3 },
        { id: 3, title: t('dashboard.marks'), icon: 'medal-outline', screen: 'Marks', color: '#10b981', badge: 0 },
        { id: 4, title: t('dashboard.fees'), icon: 'wallet-outline', screen: 'Fees', color: '#f43f5e', badge: 0 },
        { id: 5, title: t('dashboard.diary'), icon: 'journal-outline', screen: 'Diary', color: '#06b6d4', badge: 1 },
        { id: 6, title: t('dashboard.timetable'), icon: 'time-outline', screen: 'Timetable', color: '#6366f1', badge: 0 },
        { id: 7, title: t('dashboard.bus_tracking'), icon: 'bus-outline', screen: 'BusTracking', color: '#f59e0b', badge: 0 },
        { id: 8, title: t('dashboard.leave_letter'), icon: 'mail-unread-outline', screen: 'LeaveRequest', color: '#ec4899', badge: 0 },
    ];

    return (
        <SafeAreaView style={styles.container} edges={['top']}>
            <StatusBar barStyle="light-content" />
            <ScrollView
                showsVerticalScrollIndicator={false}
                contentContainerStyle={styles.scrollContent}
            >
                {/* --- HEADER SECTION --- */}
                <View style={[styles.header, { backgroundColor: theme.primaryColor || '#2563eb' }]}>
                    <View style={styles.headerTop}>
                        <View>
                            <Text style={styles.greeting}>{t('dashboard.greeting')} 👋</Text>
                            <Text style={styles.userName}>{user?.name || 'Parent'}</Text>
                        </View>
                        <TouchableOpacity
                            style={styles.logoutBtn}
                            onPress={logout}
                        >
                            <Ionicons name="log-out-outline" size={24} color="#fff" />
                        </TouchableOpacity>
                    </View>

                    {/* Student Identity Card */}
                    <View style={styles.studentCard}>
                        <View style={styles.studentAvatar}>
                            <Ionicons name="person" size={32} color={theme.primaryColor} />
                        </View>
                        <View style={styles.studentInfo}>
                            <Text style={styles.studentName}>
                                {user?.studentName || 'John Doe'}
                            </Text>
                            <Text style={styles.studentClass}>
                                {user?.className || 'Class 10-A'} | Roll No: 101
                            </Text>
                        </View>
                        <View style={styles.attendanceBadge}>
                            <Text style={styles.attendancePercent}>95%</Text>
                            <Text style={styles.attendanceLabel}>{t('dashboard.attendance')}</Text>
                        </View>
                    </View>
                </View>

                {/* --- FLASH NEWS SECTION --- */}
                <View style={styles.flashBanner}>
                    <View style={styles.flashBadge}>
                        <Ionicons name="megaphone" size={14} color="#fff" />
                        <Text style={styles.flashBadgeText}>{t('dashboard.news')}</Text>
                    </View>
                    <Text style={styles.flashText} numberOfLines={1}>
                        School closed tomorrow due to heavy rain warnings in the district.
                    </Text>
                </View>

                {/* --- MAIN MENU GRID --- */}
                <View style={styles.section}>
                    <Text style={styles.sectionTitle}>{t('dashboard.quick_access')}</Text>
                    <View style={styles.cardsGrid}>
                        {quickActions.map((action) => (
                            <QuickCard
                                key={action.id}
                                title={action.title}
                                icon={action.icon}
                                color={action.color}
                                badgeCount={action.badge}
                                onPress={() => navigation.navigate(action.screen)}
                            />
                        ))}
                    </View>
                </View>

                {/* --- RECENT ACTIVITY SECTION --- */}
                <View style={[styles.section, { marginBottom: 30 }]}>
                    <Text style={styles.sectionTitle}>{t('dashboard.recent_updates')}</Text>
                    <View style={styles.activityCard}>
                        <ActivityItem
                            icon="checkmark-circle"
                            iconColor="#22c55e"
                            title="Attendance Marked"
                            subtitle="Student was marked 'Present' today"
                            time="10:00 AM"
                        />
                        <ActivityItem
                            icon="book"
                            iconColor="#8b5cf6"
                            title="New Homework"
                            subtitle="Maths: Exercise 5.2 (Fractions)"
                            time="Yesterday"
                        />
                    </View>
                </View>
            </ScrollView>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#f8fafc' },
    scrollContent: { flexGrow: 1, paddingBottom: 80 },
    header: { paddingHorizontal: 20, paddingTop: 20, paddingBottom: 55, borderBottomLeftRadius: 35, borderBottomRightRadius: 35, elevation: 10 },
    headerTop: { flexDirection: 'row', justifyContent: 'space-between', alignItems: 'center', marginBottom: 25 },
    greeting: { fontSize: 14, color: 'rgba(255,255,255,0.85)', fontWeight: '500' },
    userName: { fontSize: 24, fontWeight: 'bold', color: '#fff' },
    logoutBtn: { width: 44, height: 44, borderRadius: 22, backgroundColor: 'rgba(255,255,255,0.25)', justifyContent: 'center', alignItems: 'center' },
    studentCard: { backgroundColor: '#fff', borderRadius: 20, padding: 18, flexDirection: 'row', alignItems: 'center', elevation: 8 },
    studentAvatar: { width: 54, height: 54, borderRadius: 27, backgroundColor: '#f0f9ff', justifyContent: 'center', alignItems: 'center', marginRight: 15 },
    studentInfo: { flex: 1 },
    studentName: { fontSize: 18, fontWeight: 'bold', color: '#1e293b' },
    studentClass: { fontSize: 13, color: '#64748b' },
    attendanceBadge: { alignItems: 'center', backgroundColor: '#f0fdf4', paddingHorizontal: 12, paddingVertical: 8, borderRadius: 14 },
    attendancePercent: { fontSize: 17, fontWeight: 'bold', color: '#22c55e' },
    attendanceLabel: { fontSize: 9, color: '#166534', fontWeight: 'bold', textTransform: 'uppercase' },
    flashBanner: { backgroundColor: '#fff', marginHorizontal: 20, marginTop: -25, borderRadius: 15, padding: 12, flexDirection: 'row', alignItems: 'center', elevation: 6, borderWidth: 1, borderColor: '#fee2e2' },
    flashBadge: { backgroundColor: '#ef4444', paddingHorizontal: 10, paddingVertical: 5, borderRadius: 8, flexDirection: 'row', alignItems: 'center', marginRight: 12 },
    flashBadgeText: { color: '#fff', fontSize: 10, fontWeight: 'bold', marginLeft: 4 },
    flashText: { color: '#475569', fontSize: 13, fontWeight: '600', flex: 1 },
    section: { paddingHorizontal: 20, marginTop: 30 },
    sectionTitle: { fontSize: 19, fontWeight: 'bold', color: '#1e293b' },
    cardsGrid: { flexDirection: 'row', flexWrap: 'wrap', justifyContent: 'space-between' },
    quickCard: { width: '47.5%', backgroundColor: '#fff', borderRadius: 22, paddingVertical: 22, paddingHorizontal: 15, alignItems: 'center', marginBottom: 16, elevation: 3 },
    iconCircle: { width: 56, height: 56, borderRadius: 20, justifyContent: 'center', alignItems: 'center', marginBottom: 14 },
    quickCardTitle: { fontSize: 14, fontWeight: '700', color: '#334155', textAlign: 'center' },
    badge: { position: 'absolute', top: 12, right: 12, backgroundColor: '#ef4444', borderRadius: 11, minWidth: 22, height: 22, justifyContent: 'center', alignItems: 'center', borderWidth: 2, borderColor: '#fff' },
    badgeText: { color: '#fff', fontSize: 10, fontWeight: 'bold' },
    activityCard: { backgroundColor: '#fff', borderRadius: 22, overflow: 'hidden', elevation: 3 },
    activityItem: { flexDirection: 'row', alignItems: 'center', padding: 16, borderBottomWidth: 1, borderBottomColor: '#f1f5f9' },
    activityIcon: { width: 42, height: 42, borderRadius: 12, justifyContent: 'center', alignItems: 'center', marginRight: 15 },
    activityContent: { flex: 1 },
    activityTitle: { fontSize: 15, fontWeight: '700', color: '#1e293b' },
    activitySubtitle: { fontSize: 12, color: '#64748b' },
    activityTime: { fontSize: 11, color: '#94a3b8' },
});

export default ParentDashboard;