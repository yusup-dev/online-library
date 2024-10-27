import { defineStore } from 'pinia';
import router from "@/router/index.js";
import {authService} from "@/api/authService.js";

export const useAuthStore = defineStore('auth', {
    state: () => ({
        token: localStorage.getItem('token') || null,
        userRole: localStorage.getItem('role') || null,
        isAuthenticated: !!localStorage.getItem('token'),
        userId: localStorage.getItem('userId') || null,
        error: null,
    }),
    actions: {
        async handleLogin(loginData) {
            try {
                const response = await authService.login(loginData);
                const { accessToken, role, userId } = response.data;

                this.token = accessToken;
                this.userRole = role;
                this.userId = userId;
                this.isAuthenticated = true;

                localStorage.setItem('token', accessToken);
                localStorage.setItem('role', role);
                localStorage.setItem('userId', userId);

                if (role === 'ROLE_ADMIN') {
                    await router.push('/admin');
                } else if (role === 'ROLE_USER') {
                    await router.push('/');
                }
            } catch (error) {
                this.error = 'Login failed. Please check your credentials.';
            }
        },
        async handleRegister(registerData) {
            try {
                await authService.register(registerData);
                await router.push('/login');
            } catch (error) {
                if (error.response && error.response.data && error.response.data.message) {
                    this.error = error.response.data.message;
                } else {
                    this.error = 'Invalid email (e.g., @gmail.com, hotmail.com) or password: 8 chars, 1 uppercase, no symbols!.';
                }
            }
        },
        async logout(){
            this.token = null;
            this.userRole = null;
            this.userId = null;
            this.isAuthenticated = false;
            this.error = null;

            localStorage.removeItem('token');
            localStorage.removeItem('role');
            localStorage.removeItem('userId');

            await router.push('/login');
        }
    },
});
