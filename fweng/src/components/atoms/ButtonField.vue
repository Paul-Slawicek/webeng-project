<template>
    <button type="submit" class="btn btn-primary">
      <slot />
    </button>
  </template>
  
  <script>
  export default {
    name: "ButtonField",
  };
  </script>

<style scoped>
.btn {
  padding: 0.5rem 1rem; 
  font-size: 1rem; 
  border-radius: 0.25rem;
}
</style>
  