<template>
  <div class="container mt-5">
    <h1 class="text-center mb-4">My Orders</h1>
    <div v-if="orders.length === 0" class="alert alert-info text-center">
      No orders found.
    </div>
    <div v-else>
      <table class="table table-bordered">
        <thead class="table-light">
          <tr>
            <th>Order ID</th>
            <th>Product ID</th>
            <th>Quantity</th>
            <th>Total Price (€)</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="order in orders" :key="order.id">
            <td>{{ order.id }}</td>
            <td>{{ order.productId }}</td>
            <td>{{ order.quantity }}</td>
            <td>{{ order.totalPrice.toFixed(2) }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import axios from "@/services/api";
import { useAuthStore } from "@/stores/authStore";

export default {
  name: "OrdersView",
  data() {
    return {
      orders: [],
    };
  },
  async mounted() {
    try {
      const authStore = useAuthStore();
      const response = await axios.get("/orders/my-orders", {
        headers: {
          Authorization: `Bearer ${authStore.token}`,
        },
      });
      this.orders = response.data;
    } catch (error) {
      console.error("Error fetching orders:", error);
      alert("Failed to fetch orders. Please try again.");
    }
  },
};
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  font-size: 2rem;
  margin-bottom: 1.5rem;
}
</style>