<template>
  <div>
    <div class="text-center mb-4 mt-3">
      <h1>Create Account</h1>
    </div>
    <div class="form-container register-container">
      <form @submit.prevent="submitRegistration" method="post" id="registrationForm">
        <!-- Title -->
        <div class="row mb-2">
          <div class="col-md-12 form-floating">
            <select class="form-select" id="salutation" v-model="salutation" @change="handleSalutationChange" required>
              <option value="" disabled selected hidden>Select Title</option>
              <option value="Herr">Mr.</option>
              <option value="Frau">Ms.</option>
              <option value="Other">Other</option>
            </select>
            <label for="salutation">Title</label>
          </div>
        </div>

        <!-- Additional Input for "Other" Title -->
        <div v-if="salutation === 'Other'" class="row mb-2">
          <div class="col-md-12 form-floating">
            <input
              type="text"
              class="form-control"
              id="otherSalutation"
              v-model="otherSalutation"
              placeholder="Please specify"
              maxlength="30"
              required
            />
            <label for="otherSalutation">Please specify</label>
          </div>
        </div>

        <!-- First Name and Last Name -->
        <div class="row mb-2">
          <div class="col-md-6 form-floating">
            <input type="text" class="form-control" id="firstname" v-model="firstname" placeholder="First Name" required />
            <label for="firstname">First Name</label>
          </div>
          <div class="col-md-6 form-floating">
            <input type="text" class="form-control" id="lastname" v-model="lastname" placeholder="Last Name" required />
            <label for="lastname">Last Name</label>
          </div>
        </div>

        <!-- Address -->
        <div class="row mb-2">
          <div class="col-md-12 form-floating">
            <input type="text" class="form-control" id="address" v-model="address" placeholder="Address" required />
            <label for="address">Address</label>
          </div>
        </div>

        <!-- Postal Code, City, and Country -->
        <div class="row mb-2">
          <div class="col-md-4 form-floating">
            <select class="form-select dropdown-toggle" id="country" v-model="country" required>
              <option value="" disabled selected hidden>Select Country</option>
              <option v-for="(countryItem, index) in countries" :key="index" :value="countryItem.name">
                {{ countryItem.name }}
              </option>
            </select>
            <label for="country">Country</label>
          </div>
          <div class="col-md-4 form-floating">
            <input type="text" class="form-control" id="plz" v-model="plz" placeholder="Postal Code" required />
            <label for="plz">Postal Code</label>
          </div>
          <div class="col-md-4 form-floating">
            <input type="text" class="form-control" id="city" v-model="city" placeholder="City" required />
            <label for="city">City</label>
          </div>
        </div>

        <!-- Email and Username -->
        <div class="row mb-2">
          <div class="col-md-6 form-floating">
            <input type="text" class="form-control" id="username" v-model="username" placeholder="Username" required />
            <label for="username">Username</label>
          </div>
          <div class="col-md-6 form-floating">
            <input type="email" class="form-control" id="email" v-model="email" placeholder="Email Address" required />
            <label for="email">Email Address</label>
          </div>
        </div>

        <!-- Password and Repeat Password -->
        <div class="row mb-2">
          <div class="col-md-6 form-floating">
            <input type="password" class="form-control" id="password" v-model="password" placeholder="Password" required />
            <label for="password">Password</label>
          </div>
          <div class="col-md-6 form-floating">
            <input
              type="password"
              class="form-control"
              id="password2"
              v-model="password2"
              placeholder="Repeat Password"
              required
            />
            <label for="password2">Repeat Password</label>
          </div>
        </div>

        <!-- Submit Button -->
        <div class="text-center">
          <button type="submit" class="btn btn-primary" name="reg-submit">Register</button>
        </div>
      </form>
    </div>

    <!-- Login Link -->
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
      salutation: "",
      otherSalutation: "",
      firstname: "",
      lastname: "",
      address: "",
      plz: "",
      city: "",
      country: "",
      email: "",
      username: "",
      password: "",
      password2: "",
      countries: [],
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
        otherSalutation: this.otherSalutation,
        country: this.country
      });
      // Überprüfen, ob die Passwörter übereinstimmen
      if (this.password !== this.password2) {
        alert("Passwords do not match!");
        return;
      }
      if (this.salutation === "Other") {
        this.salutation = this.otherSalutation;
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
          country: this.country
        });
        alert(response.data); // Zeigt "User registered successfully" an
        this.$router.push('/login');
      } catch (error) {
        console.log(error.response);
        alert(error?.response?.data?.errors?.join('\r\n') ?? error?.response?.data ?? "An error occurred during registration.");
      }
    },
    // Fetch countries and prioritize DACH countries
    async loadCountries() {
      try {
        const response = await axios.get("https://restcountries.com/v3.1/all");
        const allCountries = response.data.map((country) => ({
          name: country.name.common,
        }));

        const dachCountries = allCountries.filter((c) =>
          ["Austria", "Germany", "Switzerland"].includes(c.name)
        );

        const otherCountries = allCountries
          .filter((c) => !["Austria", "Germany", "Switzerland"].includes(c.name))
          .sort((a, b) => a.name.localeCompare(b.name));

        this.countries = [...dachCountries, ...otherCountries];
      } catch (error) {
        console.error("Error loading countries:", error);
      }
    },
    handleSalutationChange() {
      if (this.salutation !== "Other") {
        this.otherSalutation = "";
      }
    },
  },
  mounted() {
    this.loadCountries();
  },
};

</script>

<style scoped>
.form-floating {
  margin-bottom: 0.75rem;
}

.form-select,
.form-control {
  box-sizing: border-box;
  overflow: visible;
}

.dropdown-toggle {
  display: block;
  position: relative;
}
</style>
