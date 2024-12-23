<template>
  <div>
    <form @submit.prevent="$emit('submitRegistration')">
      <SalutationField id="salutation" otherInputId="otherSalutation" label="Title" placeholder="Select Title"
        :options="salutationOptions" :salutation="localSalutation" :otherSalutation="localOtherSalutation"
        @update:salutation="$emit('update:salutation', $event)"
        @update:otherSalutation="$emit('update:otherSalutation', $event)" />

      <!-- First Name and Last Name -->
      <div class="row mb-2">
        <div class="col-md-6">
          <InputField type="text" id="firstname" label="First Name" placeholder="First Name"
            :modelValue="localFirstname" @update:modelValue="$emit('update:firstname', $event)" />
        </div>
        <div class="col-md-6">
          <InputField type="text" id="lastname" label="Last Name" placeholder="Last Name" :modelValue="localLastname"
            @update:modelValue="$emit('update:lastname', $event)" />
        </div>
      </div>

      <!-- Address -->
      <div class="row mb-2">
        <div class="col-md-12">
          <InputField type="text" id="address" label="Address" placeholder="Address" :modelValue="localAddress"
            @update:modelValue="$emit('update:address', $event)" />
        </div>
      </div>

      <!-- Postal Code, City, and Country -->
      <div class="row mb-2">
        <div class="col-md-4">
          <DropdownField id="country" label="Country" placeholder="Select Country" :options="countryOptions"
            :modelValue="localCountry" @update:modelValue="$emit('update:country', $event)" />
        </div>
        <div class="col-md-4">
          <InputField type="text" id="plz" label="Postal Code" placeholder="Postal Code" :modelValue="localPlz"
            @update:modelValue="$emit('update:plz', $event)" />
        </div>
        <div class="col-md-4">
          <InputField type="text" id="city" label="City" placeholder="City" :modelValue="localCity"
            @update:modelValue="$emit('update:city', $event)" />
        </div>
      </div>

      <!-- Username and Email -->
      <div class="row mb-2">
        <div class="col-md-6">
          <InputField type="text" id="username" label="Username" placeholder="Username" :modelValue="localUsername"
            @update:modelValue="$emit('update:username', $event)" />
        </div>
        <div class="col-md-6">
          <InputField type="email" id="email" label="Email Address" placeholder="Email Address" :modelValue="localEmail"
            @update:modelValue="$emit('update:email', $event)" />
        </div>
      </div>

      <!-- Password and Repeat Password -->
      <div class="row mb-2">
        <div class="col-md-6">
          <InputField type="password" id="password" label="Password" placeholder="Password" :modelValue="localPassword"
            @update:modelValue="$emit('update:password', $event)" />
        </div>
        <div class="col-md-6">
          <InputField type="password" id="password2" label="Repeat Password" placeholder="Repeat Password"
            :modelValue="localPassword2" @update:modelValue="$emit('update:password2', $event)" />
        </div>
      </div>

      <!-- Submit Button -->
      <div class="text-center mt-4">
        <ButtonField>Register</ButtonField>
      </div>
    </form>
  </div>
</template>

<script>
import DropdownField from "@/components/atoms/DropdownField.vue";
import InputField from "@/components/atoms/InputField.vue";
import ButtonField from "@/components/atoms/ButtonField.vue";
import SalutationField from "@/components/molecules/SalutationField.vue";

export default {
  name: "RegisterForm",
  components: { DropdownField, InputField, ButtonField, SalutationField },
  props: {
    countries: Array,
    salutation: String,
    otherSalutation: String,
    firstname: String,
    lastname: String,
    address: String,
    plz: String,
    city: String,
    country: String,
    email: String,
    username: String,
    password: String,
    password2: String,
  },
  data() {
    return {
      localSalutation: this.salutation,
      localOtherSalutation: this.otherSalutation,
      localFirstname: this.firstname,
      localLastname: this.lastname,
      localAddress: this.address,
      localPlz: this.plz,
      localCity: this.city,
      localCountry: this.country,
      localEmail: this.email,
      localUsername: this.username,
      localPassword: this.password,
      localPassword2: this.password2,
    };
  },
  computed: {
    salutationOptions() {
      return [
        { value: "Mr.", label: "Mr." },
        { value: "Ms.", label: "Ms." },
        { value: "Other", label: "Other" },
      ];
    },
    countryOptions() {
      return this.countries.map((country) => ({
        value: country,
        label: country,
      }));
    },
  },
};
</script>