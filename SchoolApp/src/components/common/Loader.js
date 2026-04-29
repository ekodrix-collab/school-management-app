import { ActivityIndicator, StyleSheet, View } from 'react-native';

const Loader = () => (
    <View style={styles.container}>
        <ActivityIndicator size="large" color="#2563eb" />
    </View>
);

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
    },
});

export default Loader;
