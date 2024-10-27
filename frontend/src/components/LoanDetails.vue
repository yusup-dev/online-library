<template>
  <div class="loan-details-page">
    <div class="loan-details-container">
      <h3 class="loan-title">Loan Detail</h3>
      <div v-if="loan" class="loan-info">
        <span><strong>Borrower :</strong> {{ loan.user.name }}</span>
        <span><strong>Title Book :</strong> {{ loan.book.title }}</span>
        <span><strong>Borrowed At :</strong> {{ formatDate(loan.borrowedAt) }}</span>
        <span><strong>Return Date :</strong> {{ formatDate(loan.returnDate) || 'Not returned yet' }}</span>
        <span><strong>Returned : </strong> {{ loan.returnDate ? 'Returned' : 'Still on loan' }}</span>
        <span v-if="loan.isOverdue"><strong>Status : </strong> Overdue </span>
        <span v-else><strong>Status : </strong> {{ loan.returnDate ? 'Ontime' : '' }}</span>
      </div>
      <div v-else>
        <p>Loading loan details...</p>
      </div>
      <button @click="goBack" class="btn-primary mt-3">Back</button>
    </div>
  </div>
</template>

<script>
import { useLoanStore } from "@/stores/loanStore.js";
import { useRoute, useRouter } from "vue-router";
import { onMounted, ref } from "vue";
import { format } from 'date-fns';

export default {
  setup() {
    const loanStore = useLoanStore();
    const route = useRoute();
    const router = useRouter();
    const loan = ref(null);

    onMounted(async () => {
      const loanId = Number(route.params.id);
      await loanStore.getAllLoans();
      loan.value = loanStore.loans.find((l) => l.id === loanId);
    });

    const formatDate = (dateArray) => {
      if (!dateArray || dateArray.length < 7) return 'Not available';

      const date = new Date(...dateArray);

      return format(date, 'EEEE, MMMM dd yyyy');
    };

    const goBack = () => {
      router.push('/admin');
    };

    return {
      loan,
      goBack,
      formatDate
    };
  }
};
</script>

<style scoped>
.loan-details-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  padding: 20px;
  font-family: Arial, sans-serif;
  text-align: center;
}

.loan-details-container {
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

.loan-title {
  font-size: 24px;
  font-weight: bold;
  margin-bottom: 20px;
  color: #333;
}

.loan-info p {
  font-size: 16px;
  margin-bottom: 10px;
  color: #666;
}

.loan-info span {
  display: block;
  margin-bottom: 5px;
}

</style>
