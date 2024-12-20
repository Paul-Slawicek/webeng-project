<template>
  <div class="container mt-3 text-center mb-4">
    <h1>Products</h1>

    <!-- Search Bar -->
    <div class="row justify-content-center mb-3">
      <div class="col-6">
        <div class="input-group">
          <input v-model="searchQuery" type="text" class="form-control" placeholder="Search products..." />
          <button @click="fetchProducts" class="btn btn-outline-secondary">Search</button>
        </div>
      </div>
    </div>

    <!-- Product Cards -->
    <div class="row row-cols-1 row-cols-md-3 g-4 mt-2" id="product-container">
      <div v-for="product in filteredProducts" :key="product.id" class="col">
        <div class="card product-card" @click="navigateToProductDetails(product.id)">
          <!-- Image Container -->
          <div class="product-overview-container">
            <img :src="getImageUrl(product.picture)" class="card-img-top product-overview" :alt="product.title" />
          </div>
          <!-- Card Body -->
          <div class="card-body">
            <h5 class="card-title">{{ product.title }}</h5>
            <p class="card-text"><strong>Price:</strong> {{ product.price }} €</p>
            <p class="card-text"><strong>Category:</strong> {{ product.category }}</p>
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
      products: [],
      searchQuery: "",
    };
  },
  computed: {
    filteredProducts() {
      return this.products.filter((product) =>
        product.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        product.category.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
  },
  methods: {
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
      return require("@/assets/img/default.jpg");
    },

    navigateToProductDetails(productId) {
      this.$router.push(`/productdetails?id=${productId}`);
    },
  },
  mounted() {
    this.fetchProducts();

    if (this.$route.query.search) {
      this.searchQuery = this.$route.query.search;
      console.log("Search Query from URL:", this.searchQuery);
    }
  }
};
</script>

<style scoped>
.card {
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  cursor: pointer;
  border: none;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.card:hover {
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

.text-muted {
  font-size: 0.9rem;
}
</style>
