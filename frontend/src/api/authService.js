import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth';

export const authService = {
    login(loginData) {
        return axios.post(`${API_URL}/login`, loginData);
    },
    register(registerData) {
        return axios.post(`${API_URL}/register`, registerData);
    }
};
