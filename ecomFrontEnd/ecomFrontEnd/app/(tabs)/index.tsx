import React from 'react';
import {Text, View, StyleSheet, ScrollView, TextInput} from 'react-native';
import { colorScheme, useColorScheme } from "nativewind";



//https://reactnative.dev/docs/intro-react

export default function Index() {
    let name: string = 'Bharathwajan R';

    return (
        <> // single root element
            {/* Top Rectangle (Budget and Spent) */}
            <View   style={styles.topRectangle}>
                <View style={styles.textContainer}>
                    <Text style={styles.budgetText}>Budget</Text>
                    <Text style={styles.budgetText}>$1000</Text>
                </View>
                <View style={styles.textContainer}>
                    <Text style={styles.spentText}>Spent</Text>
                    <Text style={styles.budgetText}>500</Text>
                </View>
            </View>

            <ScrollView>
                {/* Welcome Text, moved to the bottom */}
                <View style={styles.welcomeContainer}>
                    <Text style={styles.welcomeText}>Welcome {name} !</Text>
                </View>
                <TextInput
                    style={{
                        height: 40,
                        borderColor: 'gray',
                        borderWidth: 1,
                    }}
                    defaultValue="You can type in me"
                />
            </ScrollView>


        </>
    );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 16,
    },
    topRectangle: {
        flexDirection: 'row',
        justifyContent: 'space-around',
        backgroundColor: '#e0e0e0', // Light gray background
        padding: 16,
        marginBottom: 16,
        borderRadius: 8,
    },
    textContainer: {
        alignItems: 'center',
    },
    budgetText: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    spentText: {
        fontSize: 18,
        fontWeight: 'bold',
    },
    tableRectangle: {
        backgroundColor: '#f0f0f0', // Slightly lighter gray
        padding: 16,
        borderRadius: 8,
    },
    tableRow: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        marginBottom: 8,
    },
    tableHeader: {
        fontWeight: 'bold',
        flex: 1,
        textAlign: 'center',
    },
    tableCell: {
        flex: 1,
        textAlign: 'center',
    },
    welcomeContainer:{
        alignItems:'center',
        marginTop:16,
    },
    welcomeText:{
        fontSize: 24,
        fontWeight: 'bold',
        color: '#333'
    }
});