import axios from 'axios';

const API_URL = 'http://localhost:8080/api/loans';
const getAuthHeaders = () => ({
    Authorization: `Bearer ${localStorage.getItem('token')}`,
    'Content-Type': 'application/json'
});

export const loanService = {
    getAllLoans() {
        return axios.get(API_URL, { headers: getAuthHeaders() });
    },

    getOverdueLoans(userId) {
        return axios.get(`${API_URL}/overdue/${userId}`, { headers: getAuthHeaders() });
    },

    createLoan(userId, bookId) {
        return axios.post(`${API_URL}?userId=${userId}&bookId=${bookId}`, {}, { headers: getAuthHeaders() });
    },

    returnBook(userId) {
        return axios.post(`${API_URL}/return?userId=${userId}`, {}, { headers: getAuthHeaders() });
    }
};
