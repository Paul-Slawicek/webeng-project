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
            <input type="text" class="form-control" id="otherSalutation" v-model="otherSalutation"
              placeholder="Please specify" maxlength="30" required />
            <label for="otherSalutation">Please specify</label>
          </div>
        </div>

        <!-- First Name and Last Name -->
        <div class="row mb-2">
          <div class="col-md-6 form-floating">
            <input type="text" class="form-control" id="firstname" v-model="firstname" placeholder="First Name"
              required />
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
            <input type="password" class="form-control" id="password" v-model="password" placeholder="Password"
              required />
            <label for="password">Password</label>
          </div>
          <div class="col-md-6 form-floating">
            <input type="password" class="form-control" id="password2" v-model="password2" placeholder="Repeat Password"
              required />
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
import axios from "@/services/api";
import * as Yup from "yup";

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
      // Yup-Schema zur Validierung
      const registrationSchema = Yup.object().shape({
        salutation: Yup.string().required("Salutation is required"),
        otherSalutation: Yup.string().when("salutation", {
          is: "Other",
          then: Yup.string().required("Please specify your title"),
        }),
        firstname: Yup.string()
          .required("First name is required")
          .min(2, "First name must be at least 2 characters"),
        lastname: Yup.string()
          .required("Last name is required")
          .min(2, "Last name must be at least 2 characters"),
        address: Yup.string().required("Address is required"),
        plz: Yup.string()
          .matches(/^\d{4}$/, "Postal Code must be exactly 4 digits")
          .required("Postal Code is required"),
        city: Yup.string().required("City is required"),
        country: Yup.string().required("Country is required"),
        email: Yup.string()
          .email("Invalid email address")
          .required("Email is required"),
        username: Yup.string()
          .required("Username is required")
          .min(3, "Username must be at least 3 characters"),
        password: Yup.string()
          .required("Password is required")
          .min(8, "Password must be at least 8 characters"),
        password2: Yup.string()
          .oneOf([Yup.ref("password")], "Passwords must match")
          .required("Password confirmation is required"),
      });

      try {
        // Validierung ausführen
        await registrationSchema.validate(
          {
            salutation: this.salutation,
            otherSalutation: this.otherSalutation,
            firstname: this.firstname,
            lastname: this.lastname,
            address: this.address,
            plz: this.plz,
            city: this.city,
            country: this.country,
            email: this.email,
            username: this.username,
            password: this.password,
            password2: this.password2,
          },
          { abortEarly: false } // Sammle alle Fehler
        );

        // Wenn Salutation "Other", setze den Wert von otherSalutation
        const finalSalutation =
          this.salutation === "Other" ? this.otherSalutation : this.salutation;

        // Registrierung absenden
        const response = await axios.post("/users", {
          salutation: finalSalutation,
          firstname: this.firstname,
          lastname: this.lastname,
          address: this.address,
          plz: this.plz,
          city: this.city,
          country: this.country,
          email: this.email,
          username: this.username,
          password: this.password,
        });

        this.$root.showMessage(response.data, 2000, "success");
        this.$router.push("/login");
      } catch (error) {
        if (error.name === "ValidationError") {
          // Yup-Validierungsfehler abfangen und anzeigen
          const errorMessages = error.inner.map((err) => err.message).join("\n");
          alert("Validation Errors:\n" + errorMessages);
        } else {
          console.error("Registration failed:", error.response);
          this.$root.showMessage(
            error?.response?.data?.errors?.join("\r\n") ??
            error?.response?.data ??
            "An error occurred during registration.",
            2000,
            "error"
          );
        }
      }
    },
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
