<template>
  <div>
    <div class="text-center mb-4 mt-3">
      <h1>Create Account</h1>
    </div>
    <div class="form-container register-container">
      <RegisterForm :countries="countries" :salutation="salutation" :otherSalutation="otherSalutation"
        :firstname="firstname" :lastname="lastname" :address="address" :plz="plz" :city="city" :country="country"
        :email="email" :username="username" :password="password" :password2="password2"
        @update:salutation="salutation = $event" @update:otherSalutation="otherSalutation = $event"
        @update:firstname="firstname = $event" @update:lastname="lastname = $event" @update:address="address = $event"
        @update:plz="plz = $event" @update:city="city = $event" @update:country="country = $event"
        @update:email="email = $event" @update:username="username = $event" @update:password="password = $event"
        @update:password2="password2 = $event" @submitRegistration="submitRegistration" />
    </div>
  </div>
</template>

<script>
import RegisterForm from "@/components/organisms/RegisterForm.vue";
import axios from "@/services/api";
import * as Yup from "yup";

export default {
  name: "RegisterView",
  components: { RegisterForm },
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
      const registrationSchema = Yup.object().shape({
        salutation: Yup.string().required("Salutation is required"),
        otherSalutation: Yup.string().when("salutation", (salutation, schema) => {
          return salutation === "Other"
            ? schema.required("Please specify your title")
            : schema;
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
          { abortEarly: false }
        );

        const finalSalutation =
          this.salutation === "Other" ? this.otherSalutation : this.salutation;

        // Registrierung absenden
        try {
          await axios.post("/users", {
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

          this.$root.showMessage("Registration successful!", 2000, "success");
          this.$router.push("/login");
        } catch (error) {
          console.error("Registration failed:", error);
          this.$root.showMessage(
            error.response?.data || "An error occurred during registration.",
            2000,
            "error"
          );
        }
      } catch (error) {
        if (error.name === "ValidationError") {
          const errorMessages = error.inner.map((err) => err.message).join("\n");
          alert("Validation Errors:\n" + errorMessages);
        } else {
          console.error("Validation Error:", error);
        }
      }
    },
    async loadCountries() {
      try {
        const response = await axios.get("https://restcountries.com/v3.1/all");
        this.countries = response.data
          .map((country) => ({
            value: country.name.common,
            label: country.name.common,
          }))
          .sort((a, b) => a.label.localeCompare(b.label));
      } catch (error) {
        console.error("Failed to load countries:", error);
      }
    },
  },
  mounted() {
    this.loadCountries();
  },
};
</script>
