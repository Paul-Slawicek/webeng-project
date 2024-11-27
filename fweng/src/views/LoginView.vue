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

    <!-- Error Modal -->
    <div v-if="showErrorModal" class="modal" role="dialog" style="display: block;">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Login Error</h5>
            <button type="button" class="close" @click="closeErrorModal">&times;</button>
          </div>
          <div class="modal-body">
            <p>{{ errorMessage }}</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" @click="closeErrorModal">Close</button>
          </div>
        </div>
      </div>
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
      showErrorModal: false,
      errorMessage: "",
    };
  },
  setup() {
    const authStore = useAuthStore(); // Zugriff auf den Pinia-Store
    return { authStore }; // Zur Nutzung in Methoden verfügbar machen
  },
  methods: {
    async submitLogin() {
      try {
        const response = await axios.post("/auth/token",
          {
            username: this.username,
            password: this.password,
          }
        );

        const token = response.data.token; // JWT-Token aus der Response
        console.log("Received Token:", token);

        // Token im Pinia-Store speichern
        this.authStore.login(token, this.rememberMe);
        alert("Login successful");
        this.$router.push("/"); // Weiterleitung nach erfolgreichem Login
      } catch (error) {
        this.showErrorModal = true;
        this.errorMessage = error.response
          ? error.response.data
          : "Invalid username or password.";
        console.error("Login error:", error); // Fehler debuggen
      }
    },
    closeErrorModal() {
      this.showErrorModal = false;
      this.errorMessage = "";
    },
  },
};
</script>

<style scoped>
.modal {
  background-color: rgba(0, 0, 0, 0.5);
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-dialog {
  max-width: 500px;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 5px;
}
</style>
