<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <div class="auth-container">
          <div class="auth-logo">
            <img class="auth-logo-img" src="../assets/images/yondex.png" alt="Your Company" />
            <h2 class="auth-title">Sign Up</h2>
          </div>

          <div class="auth-form-container">
            <form class="auth-form" @submit.prevent="submitRegister">
              <div class="form-group">
                <label for="name" class="form-label">Name</label>
                <input id="name" name="name" type="text" v-model="name" required class="form-control" />
              </div>

              <div class="form-group">
                <label for="email" class="form-label">Email</label>
                <input id="email" name="email" type="email" v-model="email" required class="form-control" />
              </div>

              <div class="form-group">
                <label for="password" class="form-label">Password</label>
                <input id="password" name="password" type="password" v-model="password" required class="form-control" />
              </div>

              <div>
                <button type="submit" class="btn-primary">Sign up</button>
              </div>
            </form>

            <div v-if="error" class="error mt-2 text-danger">
              {{ error }}
            </div>

            <p class="auth-footer-text">
              Already have an account?
              <router-link :to="{ path: '/login' }" class="link">Sign in here</router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '../stores/authStore';
import { ref, computed, watch } from 'vue';

export default {
  setup() {
    const authStore = useAuthStore();
    const name = ref('');
    const email = ref('');
    const password = ref('');

    const submitRegister = () => {
      authStore.handleRegister({name: name.value, email: email.value, password: password.value});
    };

    const error = computed(() => authStore.error);

    watch([name, email, password], () => {
      authStore.error = null;
    });

    return {
      name,
      email,
      password,
      submitRegister,
      error
    };
  },
};
</script>