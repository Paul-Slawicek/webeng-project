import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';

import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';
import ProductsView from '../views/ProductsView.vue';
import ImprintView from '../views/ImprintView.vue';
import HelpView from '../views/HelpView.vue';
import ProfileView from '../views/ProfileView.vue';
import AddProductView from '../views/AddProductView.vue';
import AdminUserManagementView from '../views/AdminUserManagementView.vue';

const routes = [
  { path: '/', name: 'Home', component: HomeView },
  { path: '/login', name: 'Login', component: LoginView },
  { path: '/register', name: 'Register', component: RegisterView },
  { path: '/products', name: 'Products', component: ProductsView },
  { path: '/impressum', name: 'Impressum', component: ImprintView },
  { path: '/help', name: 'Help', component: HelpView },
  { path: '/profile', name: 'Profile', component: ProfileView, meta: { requiresAuth: true, roles: ['user', 'admin'] }, },
  { path: '/addproduct', name: 'AddProduct', component: AddProductView, meta: { requiresAuth: true, roles: ['admin'] }, },
  { path: "/admin/users", name: 'AdminUserManagement' , component: AdminUserManagementView, meta: { requiresAuth: true, roles: "admin" } },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  // Überprüfen, ob Authentifizierung benötigt wird
  if (to.meta.requiresAuth) {
    if (!authStore.isLoggedIn) {
      // Umleiten zur Login-Seite, falls nicht eingeloggt
      return next({ name: 'Login' });
    }
    if (to.meta.roles && !to.meta.roles.includes(authStore.role)) {
      // Benutzer hat nicht die erforderliche Rolle
      return next({ name: 'Home' });
    }
  }
  next(); // Zulassen, wenn alles passt
});

export default router;
