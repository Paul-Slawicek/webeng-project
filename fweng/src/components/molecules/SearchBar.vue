<template>
    <div class="input-group">
      <InputField
        v-model="internalQuery"
        placeholder="Search products..."
      />
    </div>
  </template>
  
  <script>
  import InputField from "@/components/atoms/InputField.vue";
  
  
  export default {
    name: "SearchBar",
    components: {InputField},
    props: {
      searchQuery: {
        type: String,
        required: true,
      },
    },
    emits: ["update:searchQuery", "search"],
    computed: {
      internalQuery: {
        get() {
          return this.searchQuery;
        },
        set(value) {
          this.$emit("update:searchQuery", value);
        },
      },
    },
  };
  </script>
  