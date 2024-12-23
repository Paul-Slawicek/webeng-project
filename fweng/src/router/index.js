import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@/stores/authStore';

import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';
import ProductsView from '../views/ProductsView.vue';
import ImprintView from '../views/ImprintView.vue';
import HelpView from '../views/HelpView.vue';
import ProfileEditView from '../views/ProfileEditView.vue';
import AddProductView from '../views/AddProductView.vue';
import AdminUserManagementView from '../views/AdminUserManagementView.vue';
import ProductDetailsView from "@/views/ProductDetailsView.vue";
import ProfileView from '../views/ProfileView.vue';
import AdminProductManagementView from '../views/AdminProductManagementView.vue';
import OrderOverview from '../views/OrderOverview.vue';
import AdminOrderManagementView from '../views/AdminOrderManagementView.vue';

const routes = [
  { path: '/', name: 'Home', component: HomeView },
  { path: '/login', name: 'Login', component: LoginView },
  { path: '/register', name: 'Register', component: RegisterView },
  { path: '/products', name: 'Products', component: ProductsView },
  { path: '/imprint', name: 'Imprint', component: ImprintView },
  { path: '/help', name: 'Help', component: HelpView },
  { path: '/profile', name: 'Profile', component: ProfileView, meta: { requiresAuth: true, roles: ['user', 'admin'] }, },
  { path: '/addproduct', name: 'Add Product', component: AddProductView, meta: { requiresAuth: true, roles: ['admin'] }, },
  { path: "/admin/users", name: 'Admin User Management', component: AdminUserManagementView, meta: { requiresAuth: true, roles: "admin" } },
  { path: "/productdetails", name: "Product Details", component: ProductDetailsView },
  { path: '/profileEdit', name: 'Edit Profile', component: ProfileEditView, meta: { requiresAuth: true, roles: ['user', 'admin'] }, },
  { path: '/admin/products', name: 'Admin Product Management', component: AdminProductManagementView, meta: { requiresAuth: true, roles: 'admin' }, },
  { path: '/orders', name: 'Orders', component: OrderOverview, meta: { requiresAuth: true, roles: ['user', 'admin'] }, },
  { path: '/admin/orders', name: 'Admin Order Management', component: AdminOrderManagementView, meta: { requiresAuth: true, roles: ['admin'] } },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  const authStore = useAuthStore();

  const baseTitle = 'Basseno';
  const routeTitle = to.name || 'Default Title';

  document.title = `${routeTitle} | ${baseTitle}`;

  if (to.meta.requiresAuth) {
    if (!authStore.isLoggedIn) {
      return next({ name: 'Login' });
    }
    if (to.meta.roles && !to.meta.roles.includes(authStore.role)) {
      return next({ name: 'Home' });
    }
  }
  next();
});

export default router;
