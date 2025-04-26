import React from "react"
import {Stack, Tabs} from "expo-router";
import {Ionicons} from "@expo/vector-icons";
import {images} from "@/constants/images";


// This is used to define the layout for all the files in this folder strcuture.
// below we had defined tabs layout where we defined 3 tabs

const _layout = () => {
    return (
        <Tabs> // Creates a tab navigation container with all the files in
            // the current directory not need to add the below tags mandatorily they are just just confs
            <Tabs.Screen
                name="index"
                options={{
                    title: 'Home',
                    headerShown: false,
                    tabBarIcon: ({color, size}) => (
                        <Ionicons name="home" size={size} color={color}/>
                    ),
                }}/>
            <Tabs.Screen
                name="budget_definer"
                options={{
                    title: 'Budget',
                    headerShown: false,
                    tabBarIcon: ({color, size}) => (
                        <Ionicons name="cash" size={size} color={color}/>
                    ),
                }}/>
            <Tabs.Screen
                name="settings" // this is not corresponding to file name or anything
                options={{
                    title: 'Settings', // this is not corresponding to file name or anything
                    headerShown: false,
                    tabBarIcon: ({color, size}) => (
                        <Ionicons name="settings" size={size} color={color}/>
                    ),
                }}/>
        </Tabs>
    )
}


export default _layout;