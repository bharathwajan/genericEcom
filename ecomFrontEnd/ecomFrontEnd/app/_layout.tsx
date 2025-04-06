import { Stack } from "expo-router";
import "./globals.css"

// this is used to define layout for all tab group in this folder structure level

export default function RootLayout() {
  return (<Stack>
        <Stack.Screen
            name="(tabs)"
            options={{headerShown:false}}
        />
      </Stack>
  )
}
