// src/main.js
import { createApp } from 'vue';
import { createPinia } from 'pinia';
import App from './App.vue';
import router from './router/index.js'; // Import the index
import "./assets/main.css"
import 'boxicons'
import 'boxicons/css/boxicons.min.css';
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap'

const app = createApp(App);
const pinia = createPinia();

app.use(pinia);
app.use(router);
app.mount('#app');
