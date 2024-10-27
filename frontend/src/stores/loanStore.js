// Inside useLoanStore.js
import {defineStore} from "pinia";
import {loanService} from "@/api/loanService.js";

export const useLoanStore = defineStore('loan', {
    state: () => ({
        loans: [],
        overdueLoans: [],
        userLoan: [],
        loading: false,
        error: null
    }),
    actions: {
        async getAllLoans() {
            this.loading = true;
            try {
                const response = await loanService.getAllLoans();
                this.loans = response.data;
            } catch (error) {
                this.error = "Failed to load loans";
            } finally {
                this.loading = false;
            }
        },
        async createLoan(userId, bookId) {
            this.loading = true;
            try {
                const response = await loanService.createLoan(userId, bookId);
                this.loans.push(response.data);
                alert("Borrowing Successfully!.")
            } catch (error) {
                alert("You must return the book you borrowed before borrowing this book.");
            } finally {
                this.loading = false;
            }
        },
        async returnBook(userId) {
            this.loading = true;
            this.error = null;

            try {
                await loanService.returnBook(userId);
                // Update the user's loan list after returning a book
                this.userLoan = this.userLoan.filter(loan => loan.userId !== userId || loan.isReturned);
            } catch (error) {
                this.error = "Failed to return book";
                console.error("Error returning book:", error);
            } finally {
                this.loading = false;
            }
        },
        async getOverdueLoans(userId) {
            this.loading = true;
            this.error = null;
            try {
                const response = await loanService.getOverdueLoans(userId);
                this.overdueLoans = response.data;
            } catch (error) {
                if (error.response && error.response.status === 401) {
                    console.error("Unauthorized access. Please log in again.");
                    // Redirect to login page or show an alert
                }
                this.error = "Failed to load overdue loans";
                console.error("Error loading overdue loans:", error);
            }finally {
                this.loading = false;
            }
        },
        getCombinedLoans() {
            return this.loans.map(loan => {
                const overdueLoan = this.overdueLoans.find(oLoan => oLoan.id === loan.id);
                return {
                    ...loan,
                    isOverdue: !!overdueLoan
                };
            });
        }
    }
});
