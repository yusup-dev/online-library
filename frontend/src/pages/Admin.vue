<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <div class="admin-container">
          <div class="header-container d-flex justify-content-between">
            <div class="mt-3">
              <h2>Welcome to the library online</h2>
            </div>
            <div class="mt-3">
              <Logout/>
            </div>
          </div>

          <div class="list-loan-container">
            <LoanList/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup>
import LoanList from "@/components/LoanList.vue";
import Logout from "@/components/Logout.vue";
</script>