<template>
  <div>
    <div class="text-center mb-4 mt-3">
      <h1>Login</h1>
    </div>
    <div class="form-container">
      <LoginForm
        :username="username"
        :password="password"
        @update:username="username = $event"
        @update:password="password = $event"
        @submitLogin="submitLogin"
      />
    </div>
    <div class="container mt-3 mb-2 text-center">
      Don't have an account yet? <router-link to="/register">Register now</router-link>
    </div>
  </div>
</template>

<script>
import LoginForm from "@/components/organisms/LoginForm.vue";
import { useAuthStore } from "@/stores/authStore";
import axios from "@/services/api";

export default {
  name: "LoginView",
  components: { LoginForm },
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
  methods: {
    async submitLogin() {
      try {
        const response = await axios.post("/auth/token", {
          username: this.username,
          password: this.password,
        });

        const token = response.data.token;

        if (!token) {
          this.$root.showMessage("Your account is inactive. Please contact the administrator.", 2000, "error");
          return;
        }

        this.authStore.login(token);
        this.$router.push("/");

        this.$nextTick(() => {
          this.$root.showMessage("Login successful!", 2000, "success");
        });
      } catch (error) {
        console.error("Login error:", error);

        if (error.response?.status === 403) {
          this.$root.showMessage("Username or password is incorrect.", 2000, "error");
        } else {
          this.$root.showMessage("An unexpected error occurred. Please try again later.", 2000, "error");
        }
      }
    },
  },
};
</script>
