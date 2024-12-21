<template>
    <form @submit.prevent="$emit('submitLogin')">
      <InputField
        type="text"
        id="username"
        :modelValue="username"
        @update:modelValue="$emit('update:username', $event)"
        placeholder="Username"
        label="Username"
      />
      <InputField
        type="password"
        id="password"
        :modelValue="password"
        @update:modelValue="$emit('update:password', $event)"
        placeholder="Password"
        label="Password"
      />
      <div class="text-center">
        <ButtonField>Login</ButtonField>
      </div>
    </form>
  </template>
  
  <script>
  import InputField from "@/components/atoms/InputField.vue";
  import ButtonField from "@/components/atoms/ButtonField.vue";
  
  export default {
    name: "LoginForm",
    components: {
      InputField,
      ButtonField,
    },
    props: {
      username: String,
      password: String,
    },
    emits: ["update:username", "update:password", "submitLogin"],
  };
  </script>
  