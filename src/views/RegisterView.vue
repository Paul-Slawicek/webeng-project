<template>
  <div>
    <div class="text-center mb-4 mt-3">
      <h1>Create Account</h1>
    </div>
    <div class="form-container register-container">
      <form method="post" id="registrationForm">
        <div class="row">
          <div class="col-md-6">
            <div class="form-floating mb-3">
              <select class="form-select" id="floatingSelectAnrede" name="anrede" required>
                <option value="" disabled selected hidden>Title</option>
                <option value="Herr">Mr.</option>
                <option value="Frau">Ms.</option>
              </select>
              <label for="floatingSelectAnrede">Title</label>
            </div>

            <div class="d-flex">
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="vorname" name="vorname" placeholder="First Name" required />
                <label for="vorname">First Name</label>
              </div>
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="nachname" name="nachname" placeholder="Last Name" required />
                <label for="nachname">Last Name</label>
              </div>
            </div>

            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="floatingAdress" name="adresse" placeholder="Address" required />
              <label for="floatingAdress">Address</label>
            </div>

            <div class="d-flex">
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="floatingPlz" name="plz" placeholder="Postal Code" required />
                <label for="floatingPlz">Postal Code</label>
              </div>
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="floatingOrt" name="ort" placeholder="City" required />
                <label for="floatingOrt">City</label>
              </div>
            </div>
          </div>

          <div class="col-md-6">
            <div class="form-floating mb-3">
              <input
                type="email"
                class="form-control"
                id="floatingEmail"
                name="email"
                placeholder="name@example.com"
                required
              />
              <label for="floatingEmail">Email Address</label>
            </div>

            <div class="form-floating mb-3">
              <input
                type="text"
                class="form-control"
                id="floatingUsername"
                name="username"
                placeholder="Username"
                required
              />
              <label for="floatingUsername">Username</label>
            </div>

            <div class="form-floating mb-3">
              <input
                type="password"
                class="form-control"
                id="floatingPassword"
                name="password"
                placeholder="Password"
                required
              />
              <label for="floatingPassword">Password</label>
            </div>

            <div class="form-floating mb-3">
              <input
                type="password"
                class="form-control"
                id="floatingPassword2"
                name="password2"
                placeholder="Repeat Password"
                required
              />
              <label for="floatingPassword2">Repeat Password</label>
            </div>
          </div>
        </div>

        <div class="text-center">
          <button type="submit" class="btn btn-primary" name="reg-submit">Register</button>
        </div>
      </form>
    </div>

    <div class="container mt-3 mb-2 text-center">
      Already have an account? <router-link to="/login">Log in here</router-link>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // Registration form data goes here
    };
  },
  methods: {
    submitRegistration() {
      // Handle registration form submission logic here
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

.register-container {
  max-width: 1000px;
}

.form-floating .form-control {
  height: 58px;
  padding: 1rem .75rem;
}

.d-flex .form-floating {
  flex: 1;
}

.d-flex .form-floating + .form-floating {
  margin-left: 10px;
}

.btn-primary {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.btn-primary:hover {
  transform: scale(1.01);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

.container {
  margin-top: 20px;
  text-align: center;
}
</style>
