import { createRouter, createWebHistory } from 'vue-router';
import Login from "@/pages/Login.vue";
import Register from "@/pages/Register.vue";
import Home from "@/pages/Home.vue";
import Admin from "@/pages/Admin.vue";
import { useAuthStore } from "@/stores/authStore";
import BookDetails from "@/components/BookDetails.vue";
import LoanDetails from "@/components/LoanDetails.vue";
import Profile from "@/pages/Profile.vue";

const routes = [
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/', component: Home, meta: { requiresAuth: true } },
    { path: '/admin', component: Admin, meta: { requiresAuth: true } },
    { path: '/my-book', name: 'my-book', component: Profile, meta: {requiresAuth: true}},
    { path: '/book/:id', name: 'book', component: BookDetails, meta: { requiresAuth: true } },
    { path: '/admin/loan/:id', name: 'loan', component: LoanDetails, meta: { requiresAuth: true } }
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const authStore = useAuthStore();
    console.log("Navigating to:", to.path);
    console.log("isAuthenticated:", authStore.isAuthenticated);

    if (to.meta.requiresAuth && !authStore.isAuthenticated) {
        console.log("Redirecting to login...");
        next('/login');
    } else {
        next();
    }
});

export default router;
