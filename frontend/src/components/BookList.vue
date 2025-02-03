<template>
  <div>
    <h5 class="mt-5 mb-3">List of books</h5>
    <div v-if="bookStore.loading">Loading...</div>
    <div v-if="bookStore.error">{{ bookStore.error }}</div>
    <div v-if="paginatedBooks.length > 0">
      <div class="book-grid">
        <div class="book-container" v-for="book in paginatedBooks" :key="book.id">
          <img :src="book.imageUrl" alt="Book Cover" class="book-image">
          <div class="book-details">
            <span class="book-title">{{ book.title }}</span>
            <p class="book-author">Author : {{ book.author }}</p>
            <button @click="viewDetails(book)" class="btn-primary">Detail</button>
          </div>
        </div>
      </div>
      <div class="d-flex justify-content-end">
        <div class="pagination-controls mt-5">
          <button @click="prevPage" :disabled="currentPage === 1" class="btn btn-primary" style="width: 50px"><i class='bx bxs-chevron-left'></i></button>
          <span class="paging">Page {{ currentPage }} of {{ totalPages }}</span>
          <button @click="nextPage" :disabled="currentPage === totalPages" class="btn btn-primary" style="width: 50px"><i class='bx bxs-chevron-right'></i></button>
        </div>
      </div>
    </div>
    <div v-else>
      <p>No active loans found for this user.</p>
    </div>
  </div>
</template>

<script>
import { useBookStore } from "@/stores/bookStore.js";
import { onMounted, computed, ref } from "vue";
import router from "@/router/index.js";

export default {
  setup() {
    const bookStore = useBookStore();

    const itemsPerPage = ref(8);
    const currentPage = ref(1);

    onMounted(() => {
      bookStore.getAllBooks();
    });

    const paginatedBooks = computed(() => {
      const start = (currentPage.value - 1) * itemsPerPage.value;
      const end = start + itemsPerPage.value;
      return bookStore.books.slice(start, end);
    });

    const totalPages = computed(() => {
      return Math.ceil(bookStore.books.length / itemsPerPage.value);
    });

    const nextPage = () => {
      if (currentPage.value < totalPages.value) {
        currentPage.value++;
      }
    };

    const prevPage = () => {
      if (currentPage.value > 1) {
        currentPage.value--;
      }
    };

    const viewDetails = (book) => {
      router.push({ name: "book", params: { id: book.id } });
    };

    return {
      bookStore,
      paginatedBooks,
      currentPage,
      totalPages,
      nextPage,
      prevPage,
      viewDetails
    };
  },
};
</script>

<style>
.book-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}
.book-container {
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 1rem;
  text-align: center;
}
.book-image {
  width: 100%;
  height: auto;
  object-fit: cover;
  margin-bottom: 1rem;
}
.pagination-controls {
  display: flex;
  align-items: center;
}
.paging {
  width: 100px;
  display: flex;
  justify-content: center;
}

</style>
