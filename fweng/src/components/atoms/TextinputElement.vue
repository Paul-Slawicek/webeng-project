<template>
      <input
        :id="htmlFor"
        type="text"
        :placeholder="placeholder"
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
      />
  </template>
  
  <script>
  export default {
    name: "TextinputElement",
    props: {
      htmlFor: {
        type: String,
        required: true, // Ensure this prop is passed
      },
      modelValue: {
        type: String,
        default: "",
      },
      placeholder: {
        type: String,
        default: "",
      },
    },
  };
  </script>
  
  <style scoped>
  /* Add any necessary styles here */
  </style>
  