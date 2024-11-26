import { createRouter, createWebHistory } from 'vue-router';
import HomeView from '../views/HomeView.vue';
import LoginView from '../views/LoginView.vue';
import RegisterView from '../views/RegisterView.vue';
import ProductsView from '../views/ProductsView.vue';
import ImprintView from '../views/ImprintView.vue';
import HelpView from '../views/HelpView.vue';
import ProfileView from '../views/ProfileView.vue';
import AddProductView from '../views/AddProductView.vue';


const routes = [
  { path: '/', name: 'Home', component: HomeView },
  { path: '/login', name: 'Login', component: LoginView },
  { path: '/register', name: 'Register', component: RegisterView },
  { path: '/products', name: 'Products', component: ProductsView },
  { path: '/impressum', name: 'Impressum', component: ImprintView },
  { path: '/help', name: 'Help', component: HelpView },
  { path: '/profile', name: 'Profile', component: ProfileView },
  { path: '/addproduct', name: 'AddProduct', component: AddProductView },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
