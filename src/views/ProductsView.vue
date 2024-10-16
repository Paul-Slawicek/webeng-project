<template>
  <div class="container mt-3 text-center">
    <h1>Products</h1>
    <div class="row row-cols-1 row-cols-md-3 g-4" id="product-container">
      <div v-for="product in products" :key="product.id" class="col">
        <div class="card">
          <img :src="product.img" class="card-img-top" :alt="product.title">
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
export default {
  data() {
    return {
      products: [
        {
          id: 1,
          img: require('@/assets/testimg/TV1.jpeg'), // Image for TV1
          title: "Samsung 4K Smart TV",
          description: "A top-notch 4K TV offering brilliant clarity and smart features."
        },
        {
          id: 2,
          img: require('@/assets/testimg/TV2.jpeg'), // Image for TV2
          title: "LG OLED TV",
          description: "An OLED TV with stunning picture quality and an immersive viewing experience."
        },
        {
          id: 3,
          img: require('@/assets/testimg/laptop.jpg'), // Image for Laptop1
          title: "MacBook Pro",
          description: "High-performance laptop for professionals."
        },
        {
          id: 4,
          img: require('@/assets/testimg/Laptop2.jpg'), // Image for Laptop2
          title: "Dell XPS 13",
          description: "Sleek, lightweight, and powerful laptop for all your needs."
        },
        {
          id: 5,
          img: require('@/assets/testimg/Speaker1.jpeg'), // Image for Speaker1
          title: "Bose Wireless Speakers",
          description: "Premium sound quality with wireless convenience."
        },
        {
          id: 6,
          img: require('@/assets/testimg/Speaker2.jpeg'), // Image for Speaker2
          title: "Sony Bluetooth Speakers",
          description: "Portable, high-quality sound for on-the-go entertainment."
        }
      ]
    };
  }
};
</script>