import axios from "axios";


const instance = axios.create({
    baseURL: import.meta.env.VITE_EMR_BACKEND_API
  });

  export default instance;