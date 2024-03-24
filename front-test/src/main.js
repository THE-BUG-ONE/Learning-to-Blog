
import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router/router.js'
import 'element-plus/dist/index.css'
import axios from "axios"

import 'element-plus/theme-chalk/dark/css-vars.css'

const app = createApp(App)

axios.defaults.baseURL = 'http://localhost:7777'

app.use(createPinia())
app.use(router)

app.mount('#app')
