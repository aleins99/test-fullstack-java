/** @type {import('tailwindcss').Config} */
export default {
    content: ["./index.html", "./src/**/*.{js,ts,jsx,tsx}"],
    theme: {
        extend: {
            keyframes: {
                wiggle: {
                    "100%": { opacity: "1" },
                    "50%": { opcacity: ".5" },
                    "0%": { opacity: "0" },
                },
            },
            animation: {
                blunded: "wiggle .5s ease-in-out",
            },
        },
    },
    plugins: [],
};
