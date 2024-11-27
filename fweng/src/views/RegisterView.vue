<template>
  <div>
    <div class="text-center mb-4 mt-3">
      <h1>Create Account</h1>
    </div>
    <div class="form-container register-container">
      <form @submit.prevent="submitRegistration" method="post" id="registrationForm">
        <div class="row">
          <div class="col-md-6">
            <div class="form-floating mb-3">
              <select class="form-select" id="salutation" v-model="salutation" required>
                <option value="" disabled selected hidden>Title</option>
                <option value="Herr">Mr.</option>
                <option value="Frau">Ms.</option>
              </select>
              <label for="floatingSelectAnrede">Title</label>
            </div>

            <div class="d-flex">
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="firstname" v-model="firstname" placeholder="First Name"
                  required />
                <label for="vorname">First Name</label>
              </div>
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="lastname" v-model="lastname" placeholder="Last Name"
                  required />
                <label for="nachname">Last Name</label>
              </div>
            </div>

            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="address" v-model="address" placeholder="Address" required />
              <label for="address">Address</label>
            </div>

            <div class="d-flex">
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="plz" v-model="plz" placeholder="Postal Code" required />
                <label for="plz">Postal Code</label>
              </div>
              <div class="col-md-6 form-floating mb-3">
                <input type="text" class="form-control" id="city" v-model="city" placeholder="City" required />
                <label for="city">City</label>
              </div>
            </div>
          </div>

          <div class="col-md-6">
            <div class="form-floating mb-3">
              <input type="email" class="form-control" id="email" v-model="email" placeholder="name@example.com"
                required />
              <label for="email">Email Address</label>
            </div>

            <div class="form-floating mb-3">
              <input type="text" class="form-control" id="username" v-model="username" placeholder="Username"
                required />
              <label for="username">Username</label>
            </div>

            <div class="form-floating mb-3">
              <input type="password" class="form-control" id="password" v-model="password" placeholder="Password"
                required />
              <label for="password">Password</label>
            </div>

            <div class="form-floating mb-3">
              <input type="password" class="form-control" id="password2" v-model="password2"
                placeholder="Repeat Password" required />
              <label for="password2">Repeat Password</label>
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
import axios from '@/services/api';

export default {
  data() {
    return {
      salutation: '',
      firstname: '',
      lastname: '',
      address: '',
      plz: '',
      city: '',
      email: '',
      username: '',
      password: '',
      password2: '',
    };
  },
  methods: {
    async submitRegistration() {
      console.log({
        salutation: this.salutation,
        firstname: this.firstname,
        lastname: this.lastname,
        address: this.address,
        plz: this.plz,
        city: this.city,
        email: this.email,
        username: this.username,
        password: this.password,
      });
      // Überprüfen, ob die Passwörter übereinstimmen
      if (this.password !== this.password2) {
        alert("Passwords do not match!");
        return;
      }

      try {
        const response = await axios.post('/users', {
          salutation: this.salutation,
          firstname: this.firstname,
          lastname: this.lastname,
          address: this.address,
          plz: this.plz,
          city: this.city,
          email: this.email,
          username: this.username,
          password: this.password,
        });
        alert(response.data); // Zeigt "User registered successfully" an
        this.$router.push('/login');
      } catch (error) {
        console.log(error.response);
        alert(error?.response?.data?.errors?.join('\r\n') ?? error?.response?.data ?? "An error occurred during registration.");
      }
    },
  },
};
</script>
