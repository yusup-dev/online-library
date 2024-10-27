<template>
  <div>
    <h5 class="my-4">The book you borrowed</h5>
    <div v-if="bookStore.loading">Loading...</div>
    <div class="text-danger" v-if="bookStore.error">{{ bookStore.error }}</div>
    <div v-if="bookStore.bookLoan.length > 0">
      <div class="book-grid">
        <div class="book-container" v-for="book in bookStore.bookLoan" :key="book.id">
          <img :src="book.imageUrl" alt="Book Cover" class="book-image">
          <div class="book-details">
            <span class="book-title">{{ book.title }}</span>
            <p class="book-author">Author : {{ book.author }}</p>
            <button @click="returnBook(book.id)" class="btn-primary">Return Book</button>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <p class="text-danger">You have not borrowed any books yet.</p>
    </div>
  </div>
</template>

<script>
import { onMounted, ref } from "vue";
import { useAuthStore } from "@/stores/authStore.js";
import { useBookStore } from "@/stores/bookStore.js";
import { useLoanStore } from "@/stores/loanStore.js";

export default {
  setup() {
    const bookStore = useBookStore();
    const authStore = useAuthStore();
    const loanStore = useLoanStore();
    const returnError = ref(null);
    const userId = authStore.userId;

    onMounted(() => {
      bookStore.getBookByUserLoans(userId);
    });

    const returnBook = async () => {
      try {
        await loanStore.returnBook(userId);
        alert('Book returned successfully!');
        await bookStore.getBookByUserLoans(userId);
      } catch (error) {
        if (error.response && error.response.status === 500) {
          alert("An internal server error occurred while returning the book.");
        } else {
          returnError.value = "Failed to return the book. Please try again.";
        }
      }
    };

    return {
      bookStore,
      returnBook,
      returnError
    };
  }
};
</script>

<style scoped>

</style>
