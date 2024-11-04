import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth';

export const authService = {
    login(data) {
        return axios.post(`${API_URL}/login`, data);
    },

    register(data) {
        return axios.post(`${API_URL}/register`, data);
    }
};
