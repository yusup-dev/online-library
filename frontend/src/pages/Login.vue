<template>
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <div class="auth-container">
          <div class="auth-logo">
            <img class="auth-logo-img" src="../assets/images/yondex.png" alt="Your Company" />
            <h2 class="auth-title">Sign in</h2>
          </div>

          <div class="auth-form-container">
            <form class="auth-form" @submit.prevent="submitLogin">
              <div class="form-group">
                <label for="email" class="form-label">Email</label>
                <input id="email" name="email" type="email" v-model="email" placeholder="Enter your email" required class="form-control" />
              </div>

              <div class="form-group">
                <label for="password" class="form-label">Password</label>
                <input id="password" name="password" type="password" v-model="password" placeholder="Enter your password" required class="form-control" />
              </div>
              <div>
                <button type="submit" class="btn-primary">Sign in</button>
              </div>

              <div v-if="authStore.error" class="error text-danger">
                {{ authStore.error }}
              </div>
            </form>

            <p class="auth-footer-text">
              Don’t have an account?
              <router-link :to="{ path: '/register' }" class="link">Sign up here</router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useAuthStore } from '../stores/authStore';
import { ref, watch } from 'vue';

export default {
  setup() {
    const authStore = useAuthStore();
    const email = ref('');
    const password = ref('');

    const submitLogin = () => {
      authStore.handleLogin({ email: email.value, password: password.value });
    };
    
    watch([email, password], () => {
      authStore.error = null;
    });

    return {
      authStore,
      email,
      password,
      submitLogin,
    };
  },
};
</script>
