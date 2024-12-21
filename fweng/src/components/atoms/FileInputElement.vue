<template>
    <div class="mb-6">
      <BadgeElement :htmlFor="id" :text="labelText" />
      <input
        :id="id"
        class="form-control"
        type="file"
        @change="handleFileChange"
        :accept="accept"
      />
    </div>
  </template>
  
  <script>
  import BadgeElement from "@/components/atoms/BadgeElement.vue";
  
  export default {
    name: "FileInputElement",
    components: {
      BadgeElement,
    },
    props: {
      id: {
        type: String,
        required: true,
      },
      labelText: {
        type: String,
        required: true,
      },
      accept: {
        type: String,
        default: "image/*",
      },
    },
    methods: {
      handleFileChange(event) {
        const file = event.target.files[0];
        this.$emit("update:file", file);
      },
    },
  };
  </script>
  
  <style scoped>
  /* Add any necessary styles */
  </style>
  