<template>
  <div class="container mt-3 text-center mb-4">
    <h1>Products</h1>
    <div class="row row-cols-1 row-cols-md-3 g-4 mt-2" id="product-container">
      <div v-for="product in products" :key="product.id" class="col">
        <div class="card">
          <img :src="getImageUrl(product.picture)" class="card-img-top" :alt="product.title" />
          <div class="card-body">
            <h5 class="card-title">{{ product.title }}</h5>
            <p class="card-text">{{ product.description }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/services/api";

export default {
  name: "ProductsView",
  data() {
    return {
      products: [], // Initialize as an empty array
    };
  },
  methods: {
    // Fetch products from the backend API
    async fetchProducts() {
      try {
        const response = await axios.get("/products/loadAll");
        this.products = response.data;
        console.log("Loaded products:", this.products);
      } catch (error) {
        console.error("Error fetching products:", error);
        alert("Failed to load products. Please try again later.");
      }
    },

    getImageUrl(picture) {
      if (picture) {
        return `http://localhost:8080/uploads/${picture}`;
      }
      return require("@/assets/img/default.jpg"); // Fallback image
    },
  },
  mounted() {
    this.fetchProducts();
  },
};
</script>

<style scoped>
.card-img-top {
  height: 200px;
  object-fit: cover;
}
</style>
