<template>
  <div>
     <!-- Erfolgsmeldung -->
     <div v-if="loginSuccess" class="alert alert-success text-center" role="alert">
      Login successful! Redirecting...
    </div>

    <!-- Fehlermeldung -->
    <div v-if="loginError" class="alert alert-danger text-center" role="alert">
      {{ loginError }}
    </div>
    <div class="text-center mb-4 mt-3">
      <h1>Login</h1>
    </div>
    <div class="form-container">
      <form @submit.prevent="submitLogin">
        <div class="form-floating mb-3">
          <input
            type="text"
            class="form-control"
            id="username"
            v-model="username"
            placeholder="Username"
            required
            ref="usernameInput"
          />
          <label for="username">Username</label>
        </div>

        <div class="form-floating mb-3">
          <input
            type="password"
            class="form-control"
            id="password"
            v-model="password"
            placeholder="Password"
            required
          />
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
      loginSuccess: false, // Kontrolliert den Erfolg-Banner
      loginError: "", // Speichert Fehlermeldungen
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

        // Token im Pinia Store speichern
        this.authStore.login(token);

        // Erfolgsmeldung anzeigen
        this.loginSuccess = true;
        this.loginError = ""; // Fehlermeldung zurücksetzen

        // Nach 2 Sekunden weiterleiten
        setTimeout(() => {
          this.$router.push("/");
        }, 2000);
      } catch (error) {
        console.error("Login error:", error);

        // Fehlermeldung anzeigen
        this.loginError = "Username or password is incorrect.";
        this.loginSuccess = false;
      }
    },
  },
};

</script>
