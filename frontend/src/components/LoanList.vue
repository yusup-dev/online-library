<template>
  <div>
    <h3 class="mt-5 mb-3">List of Loans</h3>
    <div class="loan-grid">
      <div class="loan-container" v-for="loan in combinedLoans" :key="loan.id">
        <span><strong>Borrower : </strong> {{ loan.user.name }}</span>
        <span><strong>Title Book : </strong> {{ loan.book.title }}</span>
        <span v-if="loan.isOverdue"><strong>Status : </strong> Overdue </span>
        <span v-else><strong>Status : </strong> {{ loan.returnDate ? 'Ontime' : 'Book not returned' }}</span>
        <button @click="viewDetails(loan)" class="btn-primary">Detail</button>
      </div>
    </div>
  </div>
</template>

<script>
import { onMounted, computed } from "vue";
import { useLoanStore } from "@/stores/loanStore.js";
import router from "@/router/index.js";

export default {
  setup() {
    const loanStore = useLoanStore();

    onMounted(() => {
      loanStore.getAllLoans();
    });

    const combinedLoans = computed(() => loanStore.getCombinedLoans());

    const viewDetails = (loan) => {
      router.push({ name: 'loan', params: { id: loan.id } });
    };

    return {
      combinedLoans,
      viewDetails
    };
  }
};
</script>
