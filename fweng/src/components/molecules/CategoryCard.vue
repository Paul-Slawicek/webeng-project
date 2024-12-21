<template>
    <CardAtom @cardClick="$emit('navigate', query)">
      <ImageElement :src="img" :alt="title" class="card-img-top" />
      <div class="card-body">
        <h5 class="card-title">{{ title }}</h5>
        <p class="card-text">{{ description }}</p>
      </div>
    </CardAtom>
  </template>
  
  <script>
  import CardAtom from "../atoms/CardElement.vue";
  import ImageElement from "../atoms/ImageElement.vue";
  
  export default {
    name: "CategoryCard",
    components: {
      CardAtom,
      ImageElement,
    },
    props: {
      query: {
        type: String,
        required: true,
      },
      img: {
        type: String,
        required: true,
      },
      title: {
        type: String,
        required: true,
      },
      description: {
        type: String,
        required: true,
      },
    },
    emits: ["navigate"],
  };
  </script>
  
  <style scoped>
  .card-img-top {
    height: 200px;
  }
  .card-body {
    padding: 1rem;
    text-align: center;
  }
  .card-title {
    font-size: 1.25rem;
    font-weight: bold;
    margin-bottom: 0.5rem;
  }
  .card-text {
    font-size: 1rem;
    color: #555;
  }
  </style>
  