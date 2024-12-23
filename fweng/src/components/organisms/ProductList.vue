<template>
  <div class="container mt-3">
    <div class="row">
      <div class="col-md-4" v-for="product in products" :key="product.id">
        <ProductCard :product="product" :imageUrl="getImageUrl(product.picture)"
          @navigate="$emit('navigate', $event)" />
      </div>
    </div>
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