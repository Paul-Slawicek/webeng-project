<template>
    <div class="container mt-3 mb-5">
        <!-- Page Header -->
        <div class="text-center mb-4">
            <h1 class="text-dark">Product Details</h1>
        </div>

        <!-- Product Section -->
        <div class="product-details p-4 rounded shadow-sm">
            <div class="row g-4 align-items-center">
                <!-- Product Image -->
                <div class="col-md-5 text-center">
                    <img :src="getImageUrl(product.picture)" alt="Product Image"
                        class="img-fluid rounded shadow-sm border" />
                </div>

                <!-- Product Info -->
                <div class="col-md-7">
                    <div class="p-3">
                        <h3 class="fw-normal mb-3">{{ product.title }}</h3>

                        <p class="fs-5 mb-3">
                            <strong>Category:</strong> {{ product.category }}
                        </p>

                        <p class="text-muted mb-4 text-justify" style="line-height: 1.8; text-align: justify;">
                            {{ product.description }}
                        </p>

                        <!-- Horizontal Rule -->
                        <hr />

                        <!-- Price, Quantity, and Order Button -->
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="price-box">
                                <p class="fs-4 fw-bold text-success mb-0">
                                    Price: <span class="text-dark">{{ product.price }} €</span>
                                </p>
                            </div>
                            <input type="number" v-model="quantity" min="1" class="form-control me-3"
                                style="width: 100px;" />
                            <button @click="orderProduct" class="btn btn-lg btn-success px-4 rounded-pill shadow-sm">
                                Place Order
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "@/services/api";
import { useAuthStore } from "@/stores/authStore";

export default {
    name: "ProductDetailsView",
    data() {
        return {
            product: {},
            quantity: 1,
        };
    },
    setup() {
        const authStore = useAuthStore();
        return { authStore };
    },
    methods: {
        async fetchProductDetails() {
            const productId = this.$route.query.id;
            if (!productId) {
                console.error("Product ID is missing.");
                return;
            }
            try {
                const response = await axios.get(`/products/${productId}`);
                this.product = response.data;
            } catch (error) {
                console.error("Error fetching product details:", error);
                alert("Failed to load product details.");
            }
        },
        getImageUrl(picture) {
            return picture
                ? `http://localhost:8080/uploads/${picture}`
                : require("@/assets/img/default.jpg");
        },
        async orderProduct() {
            if (this.quantity < 1) {
                alert("Please enter a valid quantity.");
                return;
            }

            // Check if the user is logged in
            if (!this.authStore.isLoggedIn) {
                alert("You must be logged in to place an order.");
                return;
            }

            const orderPayload = {
                productId: this.product.id,
                quantity: this.quantity,
            };

            try {
                // Access the token from authStore
                const token = this.authStore.token;

                const response = await axios.post("/orders/place", orderPayload, {
                    headers: {
                        Authorization: `Bearer ${token}`, // Use token from authStore
                    },
                });
                alert(response.data); // Show success message
            } catch (error) {
                console.error("Error placing order:", error);
                if (error.response?.status === 403) {
                    alert("You must be logged in to place an order.");
                } else {
                    alert("Failed to place the order. Please try again.");
                }
            }
        },
    },
    mounted() {
        this.fetchProductDetails();
    },
};
</script>

<style scoped>
.product-details {
    background-color: white;
    border-radius: 12px;
    box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
    transition: box-shadow 0.3s ease;
}

.product-details:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

img.img-fluid {
    max-height: 400px;
    object-fit: contain;
    border: 1px solid #ddd;
    border-radius: 8px;
}

h1 {
    font-size: 2rem;
    margin-top: 1rem;
    margin-bottom: 1.5rem;
    font-weight: normal;
}

.price-box p {
    font-size: 1.5rem;
    color: #28a745;
}

.btn-success {
    font-size: 1rem;
    transition: all 0.3s ease;
}

.btn-success:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
}

hr {
    border-top: 1px solid #ddd;
    margin-top: 1.5rem;
    margin-bottom: 1.5rem;
}
</style>
