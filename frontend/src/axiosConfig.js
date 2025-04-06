import axios from "axios";

const axiosInstance = axios.create({
    headers: {
        'Content-Type': 'application/json',
    },
    withCredentials: true, 
});

axiosInstance.interceptors.request.use(
    (config) => {
        const username = localStorage.getItem('username');
        const password = localStorage.getItem('password');
        
        if (username && password) {
            config.headers['Authorization'] = 'Basic ' + btoa(username + ':' + password);
        }

        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default axiosInstance;
