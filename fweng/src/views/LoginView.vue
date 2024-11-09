<template>
  <div>
    <div class="text-center mb-4 mt-3">
      <h1>Login</h1>
    </div>
    <div class="form-container">
      <form @submit.prevent="submitLogin">
        <div class="form-floating mb-3">
          <input type="text" class="form-control" id="emailOrUsername" v-model="emailOrUsername"
            placeholder="Email or Username" required />
          <label for="emailOrUsername">Email or Username</label>
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
    <div v-if="showErrorModal" class="modal"  role="dialog" style="display: block;">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Login Error</h5>
            <button type="button" class="close" @click="closeErrorModal">&times;</button>
          </div>
          <div class="modal-body">
            <p>User not found. Please check your credentials and try again.</p>
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
import axios from 'axios';

export default {
  data() {
    return {
      username: '', // or email if needed
      password: '',
    };
  },
  methods: {
    async submitLogin() {
      try {
        const response = await axios.post('http://localhost:8080/api/auth/login', {
          username: this.username, // Use 'email' if your backend supports login with email
          password: this.password,
        });
        alert(response.data); // Zeigt "Login successful" an
        this.$router.push('/'); // Weiterleitung
      } catch (error) {
        console.error(error);
        alert(error.response ? error.response.data : "Invalid username or password.");
      }
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
