<template>
  <Navbar/>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
       <div class="home-container mt-3 mb-5">
         <div class="header-container">
           <div class="mt-3">
             <h3>Welcome to the library online</h3>
           </div>
         </div>

         <div class="mt-2">
           <a href="/my-book" class="text-decoration-none text-dark">
             <i class='bx bx-book'></i>
             My Book
           </a>
         </div>

         <div class="list-book-container">
           <BookList/>
         </div>
       </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import BookBorrow from "@/components/BookBorrow.vue";
import BookList from "@/components/BookList.vue";
import Navbar from "@/components/Navbar.vue";
</script>