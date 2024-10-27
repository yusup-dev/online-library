import axios from 'axios';

const API_URL = 'http://localhost:8080/api/loans';

export const loanService = {
    getAllLoans() {
        return axios.get(API_URL, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        });
    },
    async getOverdueLoans(userId) {
        const token = localStorage.getItem('authToken');
        const headers = {
            Authorization: `Bearer ${token}`,
        };
        return await axios.get(`${API_URL}/overdue/${userId}`, { headers });
    },
    createLoan(userId, bookId, token){
        return axios.post(`${API_URL}?userId=${userId}&bookId=${bookId}`, {}, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        });
    },
    updateLoan(id, loan) {
        return axios.put(`${API_URL}/${id}`, loan, {
            headers: { 'Content-Type': 'application/json' }
        });
    },
    deleteLoan(id) {
        return axios.delete(`${API_URL}/${id}`);
    },
    returnBook(userId) {
        return axios.post(`${API_URL}/return?userId=${userId}`, {}, {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
                'Content-Type': 'application/json'
            }
        });
    }


};
