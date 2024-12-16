/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    './src/main/resources/templates/**/*.html', // Templates Thymeleaf
    './src/**/*.java' // Se usar classes com strings no HTML
  ],
  theme: {
    extend: {},
  },
  plugins: [],
};
