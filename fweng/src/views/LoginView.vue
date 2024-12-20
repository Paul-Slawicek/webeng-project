<template>
  <div>
    <HeaderElement text="Login" /> 

    <div class="form-container">
      <LoginForm
        :username="username"
        :password="password"
        @submitLogin="submitLogin"
        @update:username="username = $event"
        @update:password="password = $event"
      />
    </div>

    <div class="container mt-3 mb-2 text-center">
      Don't have an account yet? <router-link to="/register">Register now</router-link>
    </div>
  </div>
</template>

<script>
import HeaderElement from "@/components/atoms/HeaderElement.vue";
import LoginForm from "@/components/organisms/LoginForm.vue";
import axios from "@/services/api";
import { useAuthStore } from "@/stores/authStore";

export default {
  name: "LoginView",
  components: {
    HeaderElement,
    LoginForm,
  },
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
