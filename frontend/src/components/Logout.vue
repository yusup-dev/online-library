<template>
  <div>
    <button class="btn-primary" v-if="authStore.isAuthenticated" @click="logout"><i class='bx bx-log-out-circle'></i></button>
  </div>
</template>
<script>
import { useAuthStore } from '@/stores/authStore';

export default {
  setup() {
    const authStore = useAuthStore();

    const logout = () => {
      authStore.logout();
    };

    return {
      authStore,
      logout,
    };
  },
};
</script>

<style>
nav {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}
</style>
