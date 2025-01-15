<template>
    <div class="container">
        <div class="text-center my-4">
            <h1>Order Management</h1>
        </div>

        <div class="row justify-content-center mb-3">
            <div class="col-md-6">
                <SearchBar v-model:searchQuery="searchQuery" @search="fetchOrders" placeholder="Search orders..." />
            </div>
        </div>

        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>User ID</th>
                        <th>Product ID</th>
                        <th>Quantity</th>
                        <th>Total Price</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="order in filteredOrders" :key="order.id">
                        <td>{{ order.id }}</td>
                        <td>{{ order.userId }}</td>
                        <td>{{ order.productId }}</td>
                        <td>{{ order.quantity }}</td>
                        <td>{{ order.totalPrice }} €</td>
                        <td>
                            <select v-model="order.status" @change="updateOrderStatus(order)"
                                class="form-select form-select-sm">
                                <option value="pending">Pending</option>
                                <option value="shipped">Shipped</option>
                                <option value="delivered">Delivered</option>
                                <option value="cancelled">Cancelled</option>
                            </select>
                        </td>
                        <td>
                            <button @click="deleteOrder(order.id)" class="btn btn-danger btn-sm">
                                Delete
                            </button>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</template>

<script>
import axios from "@/services/api";
import SearchBar from "@/components/molecules/SearchBar.vue";

export default {
    name: "AdminOrderManagementView",
    components: { SearchBar },
    data() {
        return {
            orders: [],
            searchQuery: "",
        };
    },
    computed: {
        filteredOrders() {
            return this.orders.filter(
                (order) =>
                    order.id.toString().includes(this.searchQuery) ||
                    order.userId.toString().includes(this.searchQuery) ||
                    order.productId.toString().includes(this.searchQuery)
            );
        },
    },
    methods: {
        async fetchOrders() {
            try {
                const response = await axios.get("/orders/all");
                this.orders = response.data.sort((a, b) => a.id - b.id);
            } catch (error) {
                console.error("Error fetching orders:", error);
                this.$root.showMessage("Failed to fetch orders. Please try again.", 2000, "error");
            }
        },
        async updateOrderStatus(order) {
            try {
                await axios.put(`/orders/${order.id}`, { status: order.status });
                this.$root.showMessage("Order status updated successfully!", 2000, "success");
            } catch (error) {
                console.error("Error updating order status:", error);
                this.$root.showMessage("Failed to update order status. Please try again.", 2000, "error");
            }
        },
        async deleteOrder(orderId) {
            if (!confirm("Are you sure you want to delete this order?")) return;

            try {
                await axios.delete(`/orders/${orderId}`);
                this.fetchOrders();
                this.$root.showMessage("Order deleted successfully!", 2000, "success");
            } catch (error) {
                console.error("Error deleting order:", error);
                this.$root.showMessage("Failed to delete order. Please try again.", 2000, "error");
            }
        },
    },
    mounted() {
        this.fetchOrders();
    },
};
</script>
