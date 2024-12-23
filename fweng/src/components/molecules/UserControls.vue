<template>
    <ul class="navbar-nav ms-auto">
        <li class="nav-item" v-if="authStore.role === 'admin'">
            <router-link class="nav-link" to="/addproduct">Add new product</router-link>
        </li>
        <li class="nav-item" v-if="!authStore.isLoggedIn">
            <router-link class="nav-link" to="/login">Login</router-link>
        </li>
        <li class="nav-item" v-if="!authStore.isLoggedIn">
            <router-link class="nav-link" to="/register">Register</router-link>
        </li>
        <li class="nav-item" v-if="authStore.isLoggedIn && authStore.role !== 'admin'">
            <router-link class="nav-link" to="/orders">Orders</router-link>
        </li>
        <li class="nav-item" v-if="authStore.isLoggedIn && authStore.role !== 'admin'">
            <router-link class="nav-link" to="/profile">Profile</router-link>
        </li>
        <li class="nav-item" v-if="authStore.role === 'admin'">
            <router-link class="nav-link" to="/admin/orders">Order Management</router-link>
        </li>
        <li class="nav-item" v-if="authStore.role === 'admin'">
            <router-link class="nav-link" to="/admin/products">Product Management</router-link>
        </li>
        <li class="nav-item" v-if="authStore.isLoggedIn && authStore.role === 'admin'">
            <router-link class="nav-link" to="/admin/users">User Management</router-link>
        </li>
        <li class="nav-item" v-if="authStore.isLoggedIn">
            <button class="btn btn-link nav-link" to="/" @click="logout">Logout</button>
        </li>
    </ul>
</template>

<script>
import router from "@/router";
import { useAuthStore } from "@/stores/authStore";

export default {
    name: "UserControls",
    setup() {
        const authStore = useAuthStore();

        const logout = () => {
            authStore.logout();
            router.push("/");
        };

        return {
            authStore,
            logout,
        };
    },
};
</script>

<style scoped>
.nav-link {
    color: black;
    font-size: 16px;
    text-decoration: none;
}

.nav-link:focus,
.nav-link:active {
    color: black;
}
</style>