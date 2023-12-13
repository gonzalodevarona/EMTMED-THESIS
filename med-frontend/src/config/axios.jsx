import axios from "axios";


const instance = axios.create({
    baseURL: import.meta.env.VITE_MED_BACKEND_API
  });

  export default instance;


//   import instance from 'axios'
// import {store} from '../stores/auth/authStore'

// const axios = instance.create({ baseURL: import.meta.env.VITE_URL_API })

// axios.interceptors.request.use(
//     (config) => {
//         const state = store.getState()
//         const token = state.emt.value.auth.token
//         config.headers.Authorization = token ? `Bearer ${token}` : ''
//         return config
//     }
// )

// export default axios