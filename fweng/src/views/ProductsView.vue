<template>
  <div class="container mt-3 text-center mb-4">
    <h1>Products</h1>

    <div class="row justify-content-center mb-3">
      <div class="col-6">
        <SearchBar v-model:searchQuery="searchQuery" placeholder="Search products..." @search="fetchProducts" />
      </div>
    </div>

    <ProductList :products="filteredProducts" @navigate="navigateToProductDetails" />
  </div>
</template>

<script>
import SearchBar from "@/components/molecules/SearchBar.vue";
import ProductList from "@/components/organisms/ProductList.vue";
import axios from "@/services/api";

export default {
  name: "ProductsView",
  components: { SearchBar, ProductList },
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
      } catch (error) {
        console.error("Error fetching products:", error);
        alert("Failed to load products. Please try again later.");
      }
    },
    navigateToProductDetails(productId) {
      this.$router.push(`/productdetails?id=${productId}`);
    },
  },
  mounted() {
    this.fetchProducts();

    if (this.$route.query.search) {
      this.searchQuery = this.$route.query.search;
    }
  },
};
</script>