import { useQuery } from '@tanstack/react-query';
import {
    Dimensions,
    SectionList,
    StyleSheet,
    Text,
    View
} from 'react-native';
import { LineChart } from "react-native-chart-kit";
import { SafeAreaView } from 'react-native-safe-area-context';

// Custom Components & Constants
import Card from '../../components/common/Card';
import Loader from '../../components/common/Loader';
import { COLORS } from '../../constants/colors';
import { useAuth } from '../../hooks/useAuth';
import { parentService } from '../../services/parentService';

const MarksScreen = () => {
    const { user } = useAuth();

    const { data, isLoading } = useQuery({
        queryKey: ['marks', user?.studentId],
        queryFn: () => parentService.getMarks(user?.studentId),
    });

    /**
     * MOCK DATA FOR CHART
     * In production, this can be derived from the 'data' returned by the API
     */
    const chartData = {
        labels: ["Unit 1", "Unit 2", "Quarterly", "Half Yearly"],
        datasets: [
            {
                data: [75, 82, 78, 92], // Percentage scores for previous exams
                color: (opacity = 1) => `rgba(37, 99, 235, ${opacity})`, // Primary Blue
                strokeWidth: 3
            }
        ],
        legend: ["Term Progress (%)"]
    };

    /**
     * Component to render at the top of the list
     */
    const renderHeader = () => (
        <View style={styles.headerContainer}>
            <Text style={styles.chartTitle}>Academic Performance Analytics</Text>
            <Card style={styles.chartCard}>
                <LineChart
                    data={chartData}
                    width={Dimensions.get("window").width - 50} // Responsive width
                    height={200}
                    chartConfig={{
                        backgroundColor: "#ffffff",
                        backgroundGradientFrom: "#ffffff",
                        backgroundGradientTo: "#ffffff",
                        decimalPlaces: 0,
                        color: (opacity = 1) => `rgba(37, 99, 235, ${opacity})`,
                        labelColor: (opacity = 1) => `rgba(100, 116, 139, ${opacity})`,
                        style: {
                            borderRadius: 16
                        },
                        propsForDots: {
                            r: "6",
                            strokeWidth: "2",
                            stroke: "#2563eb"
                        }
                    }}
                    bezier // Makes the line smooth
                    style={styles.chartStyle}
                />
            </Card>
            <Text style={styles.sectionHeaderLabel}>Exam-wise Breakdown</Text>
        </View>
    );

    const renderItem = ({ item }) => (
        <Card style={styles.subjectCard}>
            <View style={styles.row}>
                <Text style={styles.subject}>{item.subject}</Text>
                <View style={styles.marksContainer}>
                    <Text style={styles.marks}>
                        {item.marksObtained}/{item.totalMarks}
                    </Text>
                    <Text style={styles.percentage}>
                        {Math.round((item.marksObtained / item.totalMarks) * 100)}%
                    </Text>
                </View>
            </View>
        </Card>
    );

    const renderSectionHeader = ({ section: { title } }) => (
        <View style={styles.sectionHeader}>
            <Text style={styles.sectionTitle}>{title}</Text>
        </View>
    );

    if (isLoading) return <Loader />;

    return (
        <SafeAreaView style={styles.container} edges={['bottom']}>
            <SectionList
                sections={data || []}
                renderItem={renderItem}
                renderSectionHeader={renderSectionHeader}
                ListHeaderComponent={renderHeader} // Adds chart to the top
                keyExtractor={(item, index) => (item.id ? item.id.toString() : index.toString())}
                contentContainerStyle={styles.list}
                stickySectionHeadersEnabled={false}
                ListEmptyComponent={
                    <Text style={styles.emptyText}>No marks available yet.</Text>
                }
            />
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: COLORS.gray[50],
    },
    list: {
        padding: 16,
        paddingBottom: 30,
    },
    headerContainer: {
        marginBottom: 20,
    },
    chartTitle: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#1e293b',
        marginBottom: 12,
        paddingLeft: 4,
    },
    chartCard: {
        padding: 10,
        alignItems: 'center',
        borderRadius: 16,
        backgroundColor: '#fff',
    },
    chartStyle: {
        marginVertical: 8,
        borderRadius: 16,
    },
    sectionHeaderLabel: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#1e293b',
        marginTop: 20,
        marginBottom: 5,
        paddingLeft: 4,
    },
    sectionHeader: {
        backgroundColor: COLORS.gray[50],
        paddingVertical: 12,
    },
    sectionTitle: {
        fontSize: 16,
        fontWeight: '600',
        color: COLORS.primary, // Highlights the Exam name (e.g. First Term)
    },
    subjectCard: {
        marginTop: 8,
        borderRadius: 12,
    },
    row: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
    subject: {
        fontSize: 15,
        fontWeight: '500',
        color: COLORS.gray[900],
    },
    marksContainer: {
        alignItems: 'flex-end',
    },
    marks: {
        fontSize: 16,
        fontWeight: 'bold',
        color: COLORS.gray[900],
    },
    percentage: {
        fontSize: 13,
        color: COLORS.success,
        fontWeight: '600',
        marginTop: 2,
    },
    emptyText: {
        textAlign: 'center',
        color: COLORS.gray[500],
        marginTop: 40,
        fontSize: 14,
    },
});

export default MarksScreen;