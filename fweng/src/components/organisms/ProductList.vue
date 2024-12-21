<template>
    <div class="row row-cols-1 row-cols-md-3 g-4 mt-2" id="product-container">
      <ProductCard
        v-for="product in products"
        :key="product.id"
        :product="product"
        :imageUrl="getImageUrl(product.picture)"
        @navigate="$emit('navigate', $event)"
      />
    </div>
  </template>
  
  <script>
  import ProductCard from "@/components/molecules/ProductCard.vue";
  
  export default {
    name: "ProductList",
    components: { ProductCard },
    props: {
      products: {
        type: Array,
        required: true,
      },
    },
    methods: {
      getImageUrl(picture) {
        if (picture) {
          return `http://localhost:8080/uploads/${picture}`;
        }
        return require("@/assets/img/default.jpg");
      },
    },
    emits: ["navigate"],
  };
  </script>
  