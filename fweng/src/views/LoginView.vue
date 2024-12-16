<template>
  <div>
    <div class="text-center mb-4 mt-3">
      <h1>Login</h1>
    </div>
    <div class="form-container">
      <form @submit.prevent="submitLogin">
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="username" v-model="username" placeholder="Username" required />
          <label for="username">Username</label>
        </div>

        <div class="form-floating mb-3">
          <input type="password" class="form-control" id="password" v-model="password" placeholder="Password"
            required />
          <label for="password">Password</label>
        </div>

        <div class="mb-3 text-start">
          <input type="checkbox" id="remember" v-model="rememberMe" />
          <label for="remember" class="ms-2">Remember me</label>
        </div>

        <div class="text-center">
          <button type="submit" class="btn btn-primary">Login</button>
        </div>
      </form>
    </div>

    <div class="container mt-3 mb-2 text-center">
      Don't have an account yet? <router-link to="/register">Register now</router-link>
    </div>
  </div>
</template>

<script>
import axios from "@/services/api";
import { useAuthStore } from "@/stores/authStore";

export default {
  name: "LoginView",
  data() {
    return {
      username: "",
      password: "",
      rememberMe: false,
    };
  },
  setup() {
    const authStore = useAuthStore();
    return { authStore };
  },
  methods: {
    async submitLogin() {
      try {
        const response = await axios.post("/auth/token", {
          username: this.username,
          password: this.password,
        });

        const token = response.data.token;
        console.log("Received Token:", token);

        // Save the token in the Pinia store
        this.authStore.login(token, this.rememberMe);
        alert("Login successful");
        this.$router.push("/"); // Redirect after successful login
      } catch (error) {
        const errorMessage = error.response
          ? error.response.data
          : "Invalid username or password.";
        console.error("Login error:", error);
        alert(`Login failed: ${errorMessage}`);
      }
    },
  },
};
</script>
