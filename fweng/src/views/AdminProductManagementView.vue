<template>
    <div class="container">
        <div class="text-center mb-4 mt-3">
            <h1>Product Management</h1>
        </div>
        <!-- Search Field -->
        <div class="row justify-content-center mb-3">
            <div class="col-6">
                <div class="input-group">
                    <input v-model="searchQuery" type="text" class="form-control" placeholder="Search products..." />
                    <button @click="fetchProducts" class="btn btn-outline-secondary">Search</button>
                </div>
            </div>
        </div>
        <!-- Product List -->
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Price</th>
                    <th>Category</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="product in filteredProducts" :key="product.id">
                    <td>{{ product.id }}</td>
                    <td>{{ product.title }}</td>
                    <td>{{ product.price }}</td>
                    <td>{{ product.category }}</td>
                    <td>
                        <button @click="openProductModal(product)" class="btn btn-primary btn-sm">Edit</button>
                        <button @click="deleteProduct(product.id)" class="btn btn-danger btn-sm">Delete</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- Product Edit Modal -->
        <div v-if="showModal" class="modal-backdrop">
            <div class="modal-content">
                <h3>Edit Product</h3>
                <form @submit.prevent="saveProductChanges">
                    <div class="form-group">
                        <label>Title:</label>
                        <input v-model="selectedProduct.title" type="text" class="form-control" required />
                    </div>
                    <div class="form-group">
                        <label>Price:</label>
                        <input v-model="selectedProduct.price" type="number" class="form-control" required />
                    </div>
                    <div class="form-group">
                        <label>Category:</label>
                        <input v-model="selectedProduct.category" type="text" class="form-control" />
                    </div>
                    <div class="form-group">
                        <label>Description:</label>
                        <textarea v-model="selectedProduct.description" class="form-control" rows="3"></textarea>
                    </div>
                    <div class="form-group">
                        <label>Picture URL:</label>
                        <input v-model="selectedProduct.picture" type="text" class="form-control" />
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Save</button>
                        <button type="button" @click="showModal = false" class="btn btn-secondary">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "@/services/api";

export default {
    name: "AdminProductManagementView",
    data() {
        return {
            products: [],
            searchQuery: "",
            selectedProduct: null,
            showModal: false,
        };
    },
    computed: {
        filteredProducts() {
            return this.products.filter((product) =>
                product.id.toString().includes(this.searchQuery) ||
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
                this.$root.showMessage("Failed to fetch products. Please try again.", 2000, "error");
            }
        },
        async deleteProduct(productId) {
            if (!confirm("Are you sure you want to delete this product?")) return;

            try {
                await axios.delete(`/products/${productId}`);
                this.fetchProducts();
                this.$root.showMessage("Product deleted successfully!", 2000, "success");
            } catch (error) {
                console.error("Error deleting product:", error);
                this.$root.showMessage("Failed to delete product. Please try again.", 2000, "error");
            }
        },
        openProductModal(product) {
            this.selectedProduct = { ...product };
            this.showModal = true;
        },
        async saveProductChanges() {
            try {
                await axios.put(`/products/${this.selectedProduct.id}`, this.selectedProduct);
                this.showModal = false;
                this.fetchProducts();
                this.$root.showMessage("Product updated successfully!", 2000, "success");
            } catch (error) {
                console.error("Error updating product:", error);
                this.$root.showMessage("Failed to update product. Please try again.", 2000, "error");
            }
        },
    },
    mounted() {
        this.fetchProducts();
    },
};
</script>

<style scoped>
.table {
    margin-top: 20px;
}

.input-group {
    max-width: 500px;
    margin: auto;
}

.modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background: white;
    padding: 20px;
    border-radius: 5px;
    width: 600px;
    box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    max-height: 90vh;
    overflow-y: auto;
}

.modal-content form .row {
    display: flex;
    justify-content: space-between;
}

.modal-content form .col {
    flex: 1;
    margin-right: 10px;
}

.modal-content form .col:last-child {
    margin-right: 0;
}

.modal-content form div {
    margin-bottom: 15px;
}

.modal-content label {
    display: block;
    font-weight: bold;
}

.modal-content input,
.modal-content select {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.modal-footer {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
}

.btn {
    margin-right: 10px;
}

.btn:last-child {
    margin-right: 0;
}
</style>