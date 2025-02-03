<template>
  <nav class="navbar navbar-light bg-light">
    <div class="container d-flex justify-content-between align-items-center">
      <!-- Logo -->
      <a class="navbar-brand d-flex align-items-center" href="#">
        <img src="../assets/images/yondex.png" width="35" height="30" alt="yondex">
      </a>

      <!-- Right Section -->
      <div class="d-flex align-items-center gap-3">
        <Logout />
      </div>
    </div>
  </nav>
</template>

<script setup>
import Logout from "@/components/Logout.vue";
</script>
