<template>
    <div class="container">
        <div class="text-center mb-4 mt-3">
            <h1>Product Management</h1>
        </div>

        <div class="row justify-content-center mb-3">
            <div class="col-6">
                <SearchBar v-model:searchQuery="searchQuery" @search="fetchProducts" placeholder="Search products..."/>
            </div>
        </div>

        <!-- Product List -->
        <ProductTable :products="filteredProducts" @edit="openProductModal" @delete="deleteProduct" />

        <!-- Product Edit Modal -->
        <ProductEditModal v-if="showModal" :product="selectedProduct" @save="saveProductChanges"
            @close="closeProductModal" />
    </div>
</template>

<script>
import SearchBar from "@/components/molecules/SearchBar.vue";
import ProductTable from "@/components/organisms/ProductTable.vue";
import ProductEditModal from "@/components/organisms/ProductEditModal.vue";
import axios from "@/services/api";

export default {
    name: "AdminProductManagementView",
    components: { SearchBar, ProductTable, ProductEditModal },
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
            this.selectedProduct = {
                id: product.id || "",
                title: product.title || "",
                price: product.price || "",
                category: product.category || "",
                description: product.description || "",
                picture: product.picture || "",
            };
            this.showModal = true;
        },

        async saveProductChanges(updatedProduct) {
            try {
                await axios.put(`/products/${updatedProduct.id}`, updatedProduct);
                this.showModal = false;
                this.fetchProducts();
                this.$root.showMessage("Product updated successfully!", 2000, "success");
            } catch (error) {
                console.error("Error updating product:", error);
                this.$root.showMessage("Failed to update product. Please try again.", 2000, "error");
            }
        },
        closeProductModal() {
            this.showModal = false;
        },
    },
    mounted() {
        this.fetchProducts();
    },
};
</script>