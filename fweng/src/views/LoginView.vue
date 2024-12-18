<template>
  <div>
    <div class="text-center mb-4 mt-3">
      <h1>Login</h1>
    </div>
    <div class="form-container">
      <form @submit.prevent="submitLogin">
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="username" v-model="username" placeholder="Username" required
            ref="usernameInput" />
          <label for="username">Username</label>
        </div>

        <div class="form-floating mb-3">
          <input type="password" class="form-control" id="password" v-model="password" placeholder="Password"
            required />
          <label for="password">Password</label>
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
    };
  },
  setup() {
    const authStore = useAuthStore();
    return { authStore };
  },
  mounted() {
    this.$refs.usernameInput.focus();
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

        this.authStore.login(token);

        this.$router.push("/");

        this.$nextTick(() => {
          this.$root.showMessage("Login successful!", 2000, "success");
        });
      } catch (error) {
        console.error("Login error:", error);

        this.$root.showMessage("Username or password is incorrect.", 2000, "error");
      }
    },
  },
};
</script>