<template>
    <img :src="src" :alt="alt" :class="className" />
  </template>
  
  <script>
  export default {
    name: "ImageElement",
    props: {
      src: {
        type: String,
        required: true,
      },
      alt: {
        type: String,
        required: true,
      },
      className: {
        type: String,
        default: "",
      },
    },
  };
  </script>
  
  <style scoped></style>
  