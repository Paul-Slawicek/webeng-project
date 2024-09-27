<template>
    <div>
      <div class="text-center mb-4 mt-3">
        <h1>Login</h1>
      </div>
      <div class="form-container">
        <form @submit.prevent="submitLogin">
          <div class="form-floating mb-3">
            <input
              type="text"
              class="form-control"
              id="floatingEmailOrUsername"
              v-model="emailOrUsername"
              placeholder="Email oder Username"
              required
            />
            <label for="floatingEmailOrUsername">Email oder Username</label>
          </div>
  
          <div class="form-floating mb-3">
            <input
              type="password"
              class="form-control"
              id="floatingPassword"
              v-model="password"
              placeholder="Passwort"
              required
            />
            <label for="floatingPassword">Passwort</label>
          </div>
  
          <div class="mb-3">
            <input
              type="checkbox"
              id="remember"
              v-model="rememberMe"
            />
            <label for="remember" class="ms-1">Remember me</label>
          </div>
  
          <div class="text-center">
            <button type="submit" class="btn btn-primary">Login</button>
          </div>
        </form>
      </div>
  
      <div class="container mt-3 mb-2 text-center">
        Sie haben noch kein Konto? <router-link to="/register">Jetzt registrieren</router-link>
      </div>
    </div>
</template>
  
<script>
  export default {
    data() {
      return {
        emailOrUsername: '',
        password: '',
        rememberMe: false
      };
    },
    methods: {
      submitLogin() {
        // Handle login logic here
        console.log('Login submitted:', {
          emailOrUsername: this.emailOrUsername,
          password: this.password,
          rememberMe: this.rememberMe
        });
      }
    }
  };
</script>
  
<style scoped>
  body {
    background-color: ghostwhite;
  }
  
  .form-container {
    max-width: 360px;
    margin: 0 auto;
    padding: 20px;
    border-radius: 10px;
  }
  
  .form-floating .form-control {
    height: 58px;
    padding: 1rem .75rem;
  }
  
  input[type="checkbox"] {
    margin-right: 10px;
  }
  
  .btn-primary {
    transition: transform 0.3s ease, box-shadow 0.3s ease;
  }
  
  .btn-primary:hover {
    transform: scale(1.01);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
  }
</style>
