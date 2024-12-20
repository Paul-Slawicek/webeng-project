<template>
    <div class="card category-card" @click="$emit('navigate', query)">
      <ImageElement :src="img" class="card-img-top" :alt="title" />
      <div class="card-body">
        <h5 class="card-title">{{ title }}</h5>
        <p class="card-text">{{ description }}</p>
      </div>
    </div>
  </template>
  
  <script>
import ImageElement from '../atoms/ImageElement.vue';

  export default {
    name: "CategoryCard",
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
  .category-card {
    border: 1px solid #ddd;
    border-radius: 8px;
    overflow: hidden;
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    cursor: pointer;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
  }
  .category-card:hover {
    transform: scale(1.05);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  }
  .card-img-top {
    height: 200px;
    object-fit: cover;
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