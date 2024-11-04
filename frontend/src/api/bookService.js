import axios from 'axios';

const API_URL = 'http://localhost:8080/api/books';
const getAuthHeaders = () => ({
    Authorization: `Bearer ${localStorage.getItem('token')}`,
    'Content-Type': 'application/json'
});

export const bookService = {
    getAllBooks() {
        return axios.get(API_URL);
    },

    getBookByUserLoan(userId) {
        return axios.get(`${API_URL}/user/${userId}/loans`, { headers: getAuthHeaders() });
    }
};
