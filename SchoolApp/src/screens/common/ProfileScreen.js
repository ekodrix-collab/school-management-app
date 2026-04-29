import { Ionicons } from '@expo/vector-icons';
import { useTranslation } from 'react-i18next';
import {
    Alert,
    ScrollView,
    StyleSheet,
    Switch,
    Text,
    View
} from 'react-native';
import { SafeAreaView } from 'react-native-safe-area-context';

// Components & Theme
import Button from '../../components/common/Button';
import Card from '../../components/common/Card';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';

const ProfileScreen = () => {
    const { user, logout } = useAuth();
    const { t, i18n } = useTranslation();

    const handleLogout = () => {
        Alert.alert(
            t('common.logout'),
            t('profile.logout_confirm'),
            [
                { text: t('common.cancel'), style: 'cancel' },
                { text: t('common.logout'), onPress: logout, style: 'destructive' },
            ]
        );
    };

    const toggleLanguage = () => {
        const newLang = i18n.language === 'en' ? 'ml' : 'en';
        i18n.changeLanguage(newLang);
    };

    const InfoRow = ({ label, value, icon }) => (
        <View style={styles.infoRow}>
            <View style={styles.labelContainer}>
                {icon && <Ionicons name={icon} size={18} color={COLORS.gray[500]} style={styles.rowIcon} />}
                <Text style={styles.label}>{label}</Text>
            </View>
            <Text style={styles.value}>{value}</Text>
        </View>
    );

    return (
        <SafeAreaView style={styles.container} edges={['bottom']}>
            <ScrollView showsVerticalScrollIndicator={false}>
                {/* --- HEADER SECTION --- */}
                <View style={styles.header}>
                    <View style={styles.avatarContainer}>
                        <View style={styles.avatarCircle}>
                            <Ionicons name="person" size={50} color={COLORS.primary} />
                        </View>
                        <View style={styles.editBadge}>
                            <Ionicons name="camera" size={14} color="#fff" />
                        </View>
                    </View>
                    <Text style={styles.name}>{user?.name}</Text>
                    <View style={styles.roleBadge}>
                        <Text style={styles.roleText}>{user?.role}</Text>
                    </View>
                </View>

                <View style={styles.content}>
                    {/* --- LANGUAGE SETTINGS --- */}
                    <Text style={styles.sectionTitle}>{t('profile.settings')}</Text>
                    <Card style={styles.card}>
                        <View style={styles.infoRow}>
                            <View style={styles.labelContainer}>
                                <Ionicons name="language" size={20} color={COLORS.primary} style={styles.rowIcon} />
                                <View>
                                    <Text style={styles.label}>App Language</Text>
                                    <Text style={styles.subLabel}>മലയാളം / English</Text>
                                </View>
                            </View>
                            <Switch
                                value={i18n.language === 'ml'}
                                onValueChange={toggleLanguage}
                                trackColor={{ false: "#d1d5db", true: COLORS.primary }}
                                thumbColor="#f4f3f4"
                            />
                        </View>
                    </Card>

                    {/* --- ACCOUNT INFO --- */}
                    <Text style={styles.sectionTitle}>{t('profile.account_info')}</Text>
                    <Card style={styles.card}>
                        <InfoRow
                            label={t('profile.mobile')}
                            value={user?.mobileNumber}
                            icon="call-outline"
                        />
                        <InfoRow
                            label={t('profile.email')}
                            value={user?.email || t('profile.not_provided')}
                            icon="mail-outline"
                        />
                    </Card>

                    {/* --- STUDENT INFO (FOR PARENTS) --- */}
                    {user?.role === 'PARENT' && (
                        <>
                            <Text style={styles.sectionTitle}>{t('profile.student_details')}</Text>
                            <Card style={styles.card}>
                                <InfoRow
                                    label={t('profile.student_name')}
                                    value={user?.studentName}
                                    icon="person-outline"
                                />
                                <InfoRow
                                    label={t('profile.class')}
                                    value={user?.className}
                                    icon="school-outline"
                                />
                            </Card>
                        </>
                    )}

                    {/* --- LOGOUT BUTTON --- */}
                    <View style={styles.actions}>
                        <Button
                            title={t('common.logout')}
                            onPress={handleLogout}
                            variant="secondary"
                            style={styles.logoutBtn}
                        />
                        <Text style={styles.version}>Version 1.0.0 (Production)</Text>
                    </View>
                </View>
            </ScrollView>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f8fafc',
    },
    header: {
        alignItems: 'center',
        paddingVertical: 30,
        backgroundColor: COLORS.white,
        borderBottomLeftRadius: 30,
        borderBottomRightRadius: 30,
        elevation: 2,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.05,
        shadowRadius: 10,
    },
    avatarContainer: {
        marginBottom: 16,
        position: 'relative',
    },
    avatarCircle: {
        width: 100,
        height: 100,
        borderRadius: 50,
        backgroundColor: '#f1f5f9',
        justifyContent: 'center',
        alignItems: 'center',
        borderWidth: 2,
        borderColor: '#e2e8f0',
    },
    editBadge: {
        position: 'absolute',
        bottom: 5,
        right: 5,
        backgroundColor: COLORS.primary,
        width: 28,
        height: 28,
        borderRadius: 14,
        justifyContent: 'center',
        alignItems: 'center',
        borderWidth: 2,
        borderColor: '#fff',
    },
    name: {
        fontSize: 22,
        fontWeight: 'bold',
        color: COLORS.gray[900],
        marginBottom: 6,
    },
    roleBadge: {
        backgroundColor: '#e0f2fe',
        paddingHorizontal: 12,
        paddingVertical: 4,
        borderRadius: 20,
    },
    roleText: {
        fontSize: 12,
        fontWeight: 'bold',
        color: COLORS.primary,
        textTransform: 'uppercase',
        letterSpacing: 1,
    },
    content: {
        padding: 20,
    },
    sectionTitle: {
        fontSize: 14,
        fontWeight: 'bold',
        color: COLORS.gray[500],
        marginBottom: 10,
        marginLeft: 4,
        textTransform: 'uppercase',
        letterSpacing: 0.5,
    },
    card: {
        marginBottom: 20,
        paddingHorizontal: 16,
        paddingVertical: 8,
    },
    infoRow: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingVertical: 14,
        borderBottomWidth: 1,
        borderBottomColor: '#f1f5f9',
    },
    labelContainer: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    rowIcon: {
        marginRight: 12,
    },
    label: {
        fontSize: 15,
        color: COLORS.gray[600],
        fontWeight: '500',
    },
    subLabel: {
        fontSize: 11,
        color: COLORS.gray[400],
    },
    value: {
        fontSize: 15,
        fontWeight: '600',
        color: COLORS.gray[900],
    },
    actions: {
        marginTop: 10,
        alignItems: 'center',
    },
    logoutBtn: {
        width: '100%',
        backgroundColor: '#fee2e2',
        borderWidth: 0,
    },
    version: {
        marginTop: 20,
        fontSize: 12,
        color: COLORS.gray[400],
    },
});

export default ProfileScreen;