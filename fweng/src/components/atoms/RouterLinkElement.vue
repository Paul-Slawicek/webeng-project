<template>
    <router-link :to="to" class="router-link-element">
      <slot />
    </router-link>
  </template>
  
  <script>
  export default {
    name: "RouterLinkElement",
    props: {
      to: {
        type: [String, Object],
        required: true,
      },
    },
  };
  </script>
  
  <style scoped>
  .router-link-element {
    text-decoration: none;
    color: inherit;
    transition: color 0.2s ease;
  }
  .router-link-element:hover {
    color: var(--primary-hover-color, #007bff);
  }
  </style>
  