<template>
  <div class="d-flex flex-column">
    <div class="container mt-3 text-center">
      <h1>Welcome to Basseno: Discover Our Exclusive Product Selection!</h1>
    </div>
    <div class="container mt-3 text-center">
      <h2>Explore Our Product Categories:</h2>
    </div>

    <div class="container mt-4">
      <div class="row">
        <div class="col-md-4" v-for="category in categories" :key="category.query">
          <CategoryCard :query="category.query" :img="category.img" :title="category.title"
            :description="category.description" @navigate="navigateToProducts" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import CategoryCard from "@/components/molecules/CategoryCard.vue";

export default {
  components: {
    CategoryCard,
  },
  data() {
    return {
      categories: [
        {
          query: "Tv",
          img: require('@/assets/img/SmartTV1.webp'),
          title: "4K Smart TVs",
          description:
            "Experience a new dimension of TV with our 4K smart TVs. Enjoy stunning clarity and intelligent features bringing versatile entertainment to your living room."
        },
        {
          query: "Laptop",
          img: require('@/assets/img/Laptop1.webp'),
          title: "High-Performance Laptops",
          description:
            "Browse our selection of high-performance laptops, perfect for both work and leisure. Whether you're a professional or student, find the ideal laptop for your needs."
        },
        {
          query: "Speakers",
          img: require('@/assets/img/Speakers1.webp'),
          title: "Wireless Sound Systems",
          description:
            "Enjoy unparalleled sound with our premium wireless speakers. They deliver crystal-clear audio and innovative features to elevate your listening experience."
        }
      ]
    };
  },
  methods: {
    navigateToProducts(query) {
      this.$router.push({ path: `/products`, query: { search: query } });
    }
  }
};
</script>