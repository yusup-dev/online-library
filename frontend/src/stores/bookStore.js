import {defineStore} from "pinia";
import {bookService} from "@/api/bookService.js";

export const useBookStore = defineStore('book', {
    state: () =>({
        books: [],
        bookLoan: [],
        book: null,
        loading: false,
        error: null
    }),
    actions: {
        async getAllBooks(){
            this.loading = true;
            try {
                const response = await bookService.getAllBooks();
                this.books = response.data;
            }catch (error){
                this.error = "Failed to load books"
            }finally {
                this.loading = false
            }
        },
        async getBookByUserLoans(userId){
            this.loading = true;
            try {
                const response = await bookService.getBookByUserLoan(userId);
                this.bookLoan = response.data;
            }catch (error){
                this.error = "Failed to load book by user loans"
            }finally {
                this.loading = false;
            }
        }
    }
})