<template>
  <nav class="navbar navbar-expand-lg navbar-dark pl-1 pr-1">
    <router-link to="/" class="navbar-brand">
      <img src="../assets/img/basseno.png" class="bassenoBild" alt="Basseno Home Logo" />
    </router-link>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
      aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <router-link class="nav-link" to="/products">Products</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/impressum">Imprint</router-link>
        </li>
        <li class="nav-item">
          <router-link class="nav-link" to="/help">Help</router-link>
        </li>
      </ul>
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
          <router-link class="nav-link" to="/profile">Profile</router-link>
        </li>
        <li class="nav-item" v-if="authStore.isLoggedIn && authStore.role === 'admin'">
          <router-link class="nav-link" to="/admin/users">User Management</router-link>
        </li>
        <li class="nav-item" v-if="authStore.isLoggedIn">
          <button class="btn btn-link nav-link" to="/" @click="logout">Logout</button>
        </li>
        <li class="nav-item">
          <router-link class="nav-link position-relative" to="" data-bs-toggle="tooltip" title="Shopping Cart">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-cart"
              viewBox="0 0 16 16">
              <path
                d="M0 1.5A.5.5 0 0 1 .5 1H2a.5.5 0 0 1 .485.379L2.89 3H14.5a.5.5 0 0 1 .491.592l-1.5 8A.5.5 0 0 1 13 12H4a.5.5 0 0 1-.491-.408L2.01 3.607 1.61 2H.5a.5.5 0 0 1-.5-.5M3.102 4l1.313 7h8.17l1.313-7zM5 12a2 2 0 1 0 0 4 2 2 0 0 0 0-4m7 0a2 2 0 1 0 0 4 2 2 0 0 0 0-4m-7 1a1 1 0 1 1 0 2 1 1 0 0 1 0-2m7 0a1 1 0 1 1 0 2 1 1 0 0 1 0-2" />
            </svg>
          </router-link>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import router from "@/router";
import { useAuthStore } from "@/stores/authStore";

export default {
  name: "NavBar",
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
.navbar {
  padding: 10px;
  background: linear-gradient(to bottom, lightblue, ghostwhite);
}

.bassenoBild {
  width: 200px;
}

.nav-item {
  margin-right: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.nav-link {
  color: black;
  font-size: 16px;
  text-decoration: none;
}

.nav-link:focus,
.nav-link:active {
  color: black;
}

.nav-item.dropdown .dropdown-menu {
  top: 100%;
  transform: translateY(0);
}

.custom-translate {
  transform: translate(-80%, -20%);
}

#cart-count {
  width: 20px;
  height: 20px;
  padding: 4px;
  color: white;
  background-color: red;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 12px;
  font-weight: bold;
}
</style>
