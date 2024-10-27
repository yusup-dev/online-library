<template>
  <div class="book-details-page">
    <div v-if="book" class="book-details-container">
      <img :src="book.imageUrl" alt="Book Cover" class="book-image">
      <h3 class="book-title">{{ book.title }}</h3>
      <span class="book-author">Author : {{ book.author }}</span>
      <span v-if="book.available" class="book-status available">Status: Available</span>
      <span v-else class="book-status unavailable">Status: Not Available</span>
      <button v-if="book.available" @click="borrowBook" class="btn-primary">Borrow</button>
    </div>
  </div>
</template>

<script>
import {useBookStore} from "@/stores/bookStore.js";
import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {useLoanStore} from "@/stores/loanStore.js";
import {useAuthStore} from "@/stores/authStore.js";

export default {
  setup() {
    const bookStore = useBookStore();
    const loanStore = useLoanStore();
    const authStore = useAuthStore();
    const route = useRoute();
    const router = useRouter();
    const book = ref(null);
    const borrowError = ref(null);

    onMounted(async () => {
      const bookId = Number(route.params.id);
      await bookStore.getAllBooks();
      book.value = bookStore.books.find(b => b.id === bookId);
    });

    const goBack = () => {
      router.push('/');
    };

    const borrowBook = async () => {
      if (!authStore.isAuthenticated) {
        borrowError.value = "You need to be logged in to borrow a book.";
        return;
      }

      try {
        const userId = authStore.userId;
        await loanStore.createLoan(userId, book.value.id);
        goBack();
      } catch (error) {
        console.log(error)
      }
    };

    return {
      book,
      goBack,
      borrowBook
    };
  }
};
</script>

<style scoped>
.book-details-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  padding: 20px;
  font-family: Arial, sans-serif;
  text-align: center;
}

.book-details-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  max-width: 400px;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

.book-image {
  width: 100%;
  height: 350px;
  border-radius: 8px;
  margin-bottom: 20px;
}

.book-title {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
  color: #333;
}

.book-author {
  font-size: 16px;
  margin-bottom: 15px;
  color: #666;
}

.book-status {
  font-size: 16px;
  margin-bottom: 15px;
}

.book-status.available {
  color: green;
}

.book-status.unavailable {
  color: red;
}
</style>
