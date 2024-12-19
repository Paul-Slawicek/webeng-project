<template>
    <div class="card product-card" @click="$emit('navigate', productId)">
      <div class="product-overview-container">
        <ImageElement :src="img" class="card-img-top product-overview" :alt="title" />
      </div>
      <div class="card-body">
        <h5 class="card-title">{{ title }}</h5>
        <p class="card-text"><strong>Price:</strong> {{ price }} €</p>
        <p class="card-text"><strong>Category:</strong> {{ category }}</p>
      </div>
    </div>
  </template>
  
  <script>
import ImageElement from '../atoms/ImageElement.vue';

  export default {
    name: "ProductCard",
    props: {
      productId: {
        type: [String, Number],
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
      price: {
        type: Number,
        required: true,
      },
      category: {
        type: String,
        required: true,
      },
    },
    emits: ["navigate"],
  };
  </script>
  
  <style scoped>
  .product-card {
    transition: transform 0.3s ease, box-shadow 0.3s ease;
    cursor: pointer;
    border: none;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    height: 100%;
  }
  .product-card:hover {
    transform: scale(1.03);
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
  }
  .product-overview-container {
    height: 200px;
    display: flex;
    justify-content: center;
    align-items: center;
    overflow: hidden;
  }
  .product-overview {
    object-fit: cover;
    width: 100%;
    height: 100%;
  }
  .card-body {
    text-align: center;
    padding: 1rem;
  }
  .card-title {
    font-size: 1.25rem;
    font-weight: bold;
    margin-bottom: 10px;
  }
  .card-text {
    font-size: 1rem;
    margin-bottom: 5px;
  }
  </style>
  