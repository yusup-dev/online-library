<template>
  <div>
    <h5 class="mt-5 mb-3">List of books</h5>
    <div v-if="bookStore.loading">Loading...</div>
    <div v-if="bookStore.error">{{ bookStore.error }}</div>
    <div v-if="bookStore.books.length > 0">
      <div class="book-grid">
        <div class="book-container" v-for="book in bookStore.books" :key="book.id">
          <img :src="book.imageUrl" alt="Book Cover" class="book-image">
          <div class="book-details">
            <span class="book-title">{{ book.title }}</span>
            <p class="book-author">Author : {{ book.author }}</p>
            <button @click="viewDetails(book)" class="btn-primary">Detail</button>
          </div>
        </div>
      </div>
    </div>
    <div v-else>
      <p>No active loans found for this user.</p>
    </div>
  </div>
</template>

<script>
import {useBookStore} from "@/stores/bookStore.js";
import {onMounted} from "vue";
import router from "@/router/index.js";

export default {
  setup() {
    const bookStore = useBookStore();

    onMounted(() => {
      bookStore.getAllBooks();
    });

    const viewDetails = (book) => {
      router.push({name: 'book', params: {id: book.id}})
    }

    return {
      bookStore,
      viewDetails
    }
  }
}
</script>