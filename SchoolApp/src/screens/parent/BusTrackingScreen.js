import { Ionicons } from '@expo/vector-icons';
import { useEffect, useRef } from 'react';
import { Animated, Dimensions, Easing, StyleSheet, Text, View } from 'react-native';
import MapView, { Marker, PROVIDER_GOOGLE } from 'react-native-maps';
import { COLORS } from '../../constants/colors';

const { width } = Dimensions.get('window');
const ROAD_WIDTH = width - 120; // Padding for the visual road track

const BusTrackingScreen = () => {
    // Animation Value for the Bus (0 to 1)
    const moveAnim = useRef(new Animated.Value(0)).current;

    useEffect(() => {
        // Continuous Loop Animation simulating School -> Home
        Animated.loop(
            Animated.timing(moveAnim, {
                toValue: 1,
                duration: 6000, // 6 seconds for one journey
                easing: Easing.bezier(0.4, 0, 0.2, 1),
                useNativeDriver: true,
            })
        ).start();
    }, []);

    // Interpolate animation to move from Left to Right
    const translateX = moveAnim.interpolate({
        inputRange: [0, 1],
        outputRange: [0, ROAD_WIDTH],
    });

    return (
        <View style={styles.container}>
            {/* Real Map Component */}
            <MapView
                provider={PROVIDER_GOOGLE}
                style={styles.map}
                initialRegion={{
                    latitude: 10.8505,
                    longitude: 76.2711,
                    latitudeDelta: 0.03,
                    longitudeDelta: 0.03,
                }}
            >
                <Marker coordinate={{ latitude: 10.8505, longitude: 76.2711 }}>
                    <View style={styles.mapMarker}>
                        <Ionicons name="bus" size={18} color="#fff" />
                    </View>
                </Marker>
            </MapView>

            {/* Journey Status Card (Floating) */}
            <View style={styles.journeyCard}>
                <Text style={styles.cardHeader}>Evening Trip: School to Home</Text>

                {/* Visual Road Tracker */}
                <View style={styles.roadContainer}>
                    <View style={styles.point}>
                        <Ionicons name="school" size={22} color={COLORS.primary} />
                        <Text style={styles.pointLabel}>School</Text>
                    </View>

                    <View style={styles.track}>
                        {/* Animated Bus Icon */}
                        <Animated.View style={[styles.busIcon, { transform: [{ translateX }] }]}>
                            <Ionicons name="bus" size={28} color="#ea580c" />
                            <View style={styles.busShadow} />
                        </Animated.View>
                        {/* The Road Line */}
                        <View style={styles.line} />
                    </View>

                    <View style={styles.point}>
                        <Ionicons name="home" size={22} color="#22c55e" />
                        <Text style={styles.pointLabel}>Home</Text>
                    </View>
                </View>

                {/* Arrival Info */}
                <View style={styles.infoRow}>
                    <View style={styles.infoItem}>
                        <Text style={styles.infoLabel}>Status</Text>
                        <View style={styles.statusBadge}>
                            <View style={styles.dot} />
                            <Text style={styles.statusText}>On the way</Text>
                        </View>
                    </View>
                    <View style={styles.infoItem}>
                        <Text style={styles.infoLabel}>ETA</Text>
                        <Text style={styles.etaTime}>4:15 PM (12 mins)</Text>
                    </View>
                </View>
            </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: { flex: 1 },
    map: { flex: 1 },
    mapMarker: { backgroundColor: '#ea580c', padding: 6, borderRadius: 15, borderWidth: 2, borderColor: '#fff', elevation: 5 },

    journeyCard: {
        position: 'absolute',
        bottom: 30,
        left: 15,
        right: 30, // Margin from screen edges
        width: width - 30,
        backgroundColor: '#fff',
        borderRadius: 20,
        padding: 20,
        elevation: 10,
        shadowColor: '#000',
        shadowOpacity: 0.1,
        shadowRadius: 10,
    },
    cardHeader: { fontSize: 13, fontWeight: 'bold', color: '#64748b', textAlign: 'center', marginBottom: 20, textTransform: 'uppercase' },

    roadContainer: { flexDirection: 'row', alignItems: 'center', justifyContent: 'space-between', marginBottom: 25 },
    point: { alignItems: 'center', width: 50 },
    pointLabel: { fontSize: 10, marginTop: 4, fontWeight: 'bold', color: '#475569' },

    track: { flex: 1, height: 40, justifyContent: 'center', marginHorizontal: 5 },
    line: { height: 4, backgroundColor: '#f1f5f9', borderRadius: 2, width: '100%', position: 'absolute' },
    busIcon: { zIndex: 2, position: 'absolute', left: -5 },
    busShadow: { width: 20, height: 4, backgroundColor: 'rgba(0,0,0,0.1)', borderRadius: 10, alignSelf: 'center', marginTop: -2 },

    infoRow: { flexDirection: 'row', justifyContent: 'space-between', borderTopWidth: 1, borderTopColor: '#f8fafc', paddingTop: 15 },
    infoItem: { flex: 1 },
    infoLabel: { fontSize: 11, color: '#94a3b8', marginBottom: 4, textTransform: 'uppercase' },
    statusBadge: { flexDirection: 'row', alignItems: 'center' },
    dot: { width: 8, height: 8, borderRadius: 4, backgroundColor: '#22c55e', marginRight: 6 },
    statusText: { fontSize: 14, fontWeight: 'bold', color: '#22c55e' },
    etaTime: { fontSize: 14, fontWeight: 'bold', color: '#1e293b' }
});

export default BusTrackingScreen;