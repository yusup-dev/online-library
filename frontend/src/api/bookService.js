import axios from 'axios';

const API_URL = 'http://localhost:8080/api/books';

export const bookService = {
    getAllBooks(){
        return axios.get(API_URL);
    },
    getBookByUserLoan(userId) {
        const token = localStorage.getItem('token');
        return axios.get(`${API_URL}/user/${userId}/loans`, {
            headers: {
                Authorization: `Bearer ${token}`,
                'Content-Type': 'application/json'
            }
        });
    }
};
