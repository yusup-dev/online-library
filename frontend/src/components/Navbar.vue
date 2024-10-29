<template>
  <nav class="navbar navbar-light bg-light">
    <div class="container d-flex justify-content-between">
      <a class="navbar-brand" href="#">
        <img src="../assets/images/yondex.png" width="35" height="30" alt="yondex">
        Library
      </a>
      <div>
        <Logout/>
      </div>
    </div>
  </nav>
</template>
<script setup>
import Logout from "@/components/Logout.vue";
</script>