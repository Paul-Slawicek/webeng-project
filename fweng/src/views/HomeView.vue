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
          <div class="card category-card" @click="navigateToProducts(category.query)">
            <img :src="category.img" class="card-img-top" :alt="category.title">
            <div class="card-body">
              <h4 class="card-title">{{ category.title }}</h4>
              <div class="card-text mb-2">{{ category.description }}</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      categories: [
        {
          query: "tv",
          img: require('@/assets/img/SmartTV1.webp'),
          title: "4K Smart TVs",
          description:
            "Experience a new dimension of TV with our 4K smart TVs. Enjoy stunning clarity and intelligent features bringing versatile entertainment to your living room."
        },
        {
          query: "laptop",
          img: require('@/assets/img/Laptop1.webp'),
          title: "High-Performance Laptops",
          description:
            "Browse our selection of high-performance laptops, perfect for both work and leisure. Whether you're a professional or student, find the ideal laptop for your needs."
        },
        {
          query: "speakers",
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

<style scoped>

.card-img-top {
  height: 250px;
  width: 100%;
  object-fit: cover;
  margin: 0;
  padding: 0;
}

.category-card .card-body {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  padding: 1.5rem;
}

.category-card {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
}
</style>
