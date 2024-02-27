import instance from 'axios'
import {store} from '../stores/auth/authStore'

const axios = instance.create({ baseURL: import.meta.env.VITE_EMR_BACKEND_API })

axios.interceptors.request.use(
    (config) => {
        const state = store.getState()
        const token = state.emt.value.auth.token
        config.headers.Authorization = token ? `Bearer ${token}` : ''
        return config
    }
)

export default axios