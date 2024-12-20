<template>
    <div class="mb-6">
      <BadgeElement :htmlFor="id" :text="labelText" />
      <Input
        :id="id"
        class="form-control"
        type="password"
        :placeholder="placeholder"
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
      />
    </div>
  </template>
  
  <script>
  import BadgeElement from "@/components/atoms/BadgeElement.vue";
  import Input from '@/components/atoms/PasswordElement.vue';

  export default {
    name: "FormPasswordElement",
    components: {
      BadgeElement,
      Input,
    },
    props: {
      id: {
        type: String,
        required: true,
      },
      modelValue: {
        type: String,
        default: "",
      },
      placeholder: {
        type: String,
        default: "",
      },
      labelText: {
        type: String,
        required: true,
      },
    },
  };
  </script>
  
  <style scoped>
  /* Add any necessary styles here */
  </style>
  