<template>
    <form @submit.prevent="submitLogin" class="form-container">
      <!-- Username Field -->
      <FormTextInputElement
        id="username"
        labelText="Username"
        placeholder="Enter your username"
        :modelValue="username"
        @update:modelValue="updateUsername"
      />
  
      <!-- Password Field -->
      <FormPasswordElement
        id="password"
        labelText="Password"
        placeholder="Enter your password"
        :modelValue="password"
        @update:modelValue="updatePassword"
      />
  
      <!-- Submit Button -->
      <div class="text-center mt-4">
        <ButtonElement htmlFor="submit" text="Login" />
      </div>
    </form>
  </template>
  
  <script>
  import FormTextInputElement from "@/components/molecules/FormTextInputElement.vue";
  import FormPasswordElement from "@/components/molecules/FormPasswordElement.vue";
  import ButtonElement from "@/components/atoms/ButtonElement.vue";
  
  export default {
    name: "LoginForm",
    components: {
      FormTextInputElement,
      FormPasswordElement,
      ButtonElement,
    },
    props: {
      username: {
        type: String,
        required: true,
      },
      password: {
        type: String,
        required: true,
      },
    },
    emits: ["update:username", "update:password", "submitLogin"],
    methods: {
      updateUsername(value) {
        this.$emit("update:username", value);
      },
      updatePassword(value) {
        this.$emit("update:password", value);
      },
      submitLogin() {
        this.$emit("submitLogin");
      },
    },
  };
  </script>
  
  <style scoped>
  .form-container {
    max-width: 400px;
    margin: 0 auto;
  }
  </style>
  