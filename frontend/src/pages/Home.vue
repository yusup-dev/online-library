<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
       <div class="home-container mt-3 mb-5">
         <div class="header-container">
           <div class="mt-3">
             <h2>Welcome to the library online</h2>
           </div>
           <div class="mt-3">
             <Logout/>
           </div>
         </div>

         <div class="borrow-container mt-5">
            <BookBorrow/>
         </div>

         <div class="list-book-container">
           <h3>Book List</h3>
           <BookList/>
         </div>
       </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import BookBorrow from "@/components/BookBorrow.vue";
import Logout from "@/components/Logout.vue";
import BookList from "@/components/BookList.vue";
</script>