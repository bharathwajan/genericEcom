/** @type {import('tailwindcss').Config} */
module.exports = {
  // NOTE: Update this to include the paths to all of your component files.
  darkMode: "class",
  content: ["./app/**/*.{js,jsx,ts,tsx}"],
  presets: [require("nativewind/preset")],
  theme: {
    extend: {
      colors: {
        primary: {
          DEFAULT: '#800080', // Purple (you can adjust the shade)
          light: '#9370DB', // Lighter purple
          dark: '#4B0082', // Darker purple
        },
        secondary: {
          DEFAULT: '#FFD700', // Yellow (you can adjust the shade)
          light: '#FFE4B5', // Lighter yellow
          dark: '#FFC125', // Darker yellow
        },
      },
    },
  },
  plugins: [],
};