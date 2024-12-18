<template>
  <div class="container mt-3 mb-5">
    <div class="text-center mb-4">
      <h1>Add Product</h1>
    </div>
    <form @submit.prevent="addProduct" class="mx-auto" style="max-width: 700px;">
      <!-- Category -->
      <div class="mb-4">
        <label for="category" class="form-label fw-bold">Category</label>
        <select
          id="category"
          v-model="productData.category"
          class="form-select form-control-lg"
          required
        >
          <option value="" disabled selected hidden>Select a category</option>
          <option value="TV">TV</option>
          <option value="Laptop">Laptop</option>
          <option value="Speakers">Speakers</option>
        </select>
      </div>

      <!-- Title and Price -->
      <div class="row g-4 mb-4">
        <div class="col-md-6">
          <label for="title" class="form-label fw-bold">Title</label>
          <input
            type="text"
            id="title"
            v-model="productData.title"
            class="form-control form-control-lg"
            placeholder="Enter title"
            required
          />
        </div>
        <div class="col-md-6">
          <label for="price" class="form-label fw-bold">Price (€)</label>
          <input
            type="number"
            id="price"
            v-model="productData.price"
            class="form-control form-control-lg"
            placeholder="Enter price"
            step="1"
            required
          />
        </div>
      </div>

      <!-- Description -->
      <div class="mb-4">
        <label for="description" class="form-label fw-bold">Description</label>
        <textarea
          id="description"
          v-model="productData.description"
          class="form-control form-control-lg"
          placeholder="Enter product description"
          rows="5"
          required
        ></textarea>
      </div>

      <!-- File Upload -->
      <div class="mb-5">
        <label for="file" class="form-label fw-bold">Upload Image</label>
        <input
          type="file"
          id="file"
          @change="handleImageUpload"
          class="form-control form-control-lg"
          accept="image/*"
        />
      </div>

      <!-- Submit Button -->
      <div class="text-center">
        <button type="submit" class="btn btn-primary btn-lg px-5">
          Add Product
        </button>
      </div>
    </form>
  </div>
</template>

<script>
import axios from "@/services/api";
import { useAuthStore } from "@/stores/authStore";

export default {
  name: "AddProductView",
  data() {
    return {
      productData: {
        title: "",
        price: "",
        description: "",
        category: "",
      },
      file: null,
    };
  },
  computed: {
    authStore() {
      return useAuthStore();
    },
  },
  methods: {
    handleImageUpload(event) {
      const file = event.target.files[0];
      if (file) {
        this.file = file;
      }
    },
    async addProduct() {
      try {
        const formData = new FormData();
        formData.append("product", JSON.stringify(this.productData));
        if (this.file) {
          formData.append("file", this.file);
        }

        const response = await axios.post("/products/add", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

        if (response.status === 201) {
          this.resetForm();

          this.$root.showMessage("Product added successfully!", 2000, "success");
        }
      } catch (error) {
        console.error("Error adding product:", error);

        this.$root.showMessage("An error occurred. Please try again later.", 2000, "error");
      }
    },
    resetForm() {
      this.productData = {
        title: "",
        price: "",
        category: "",
        description: "",
      };
      this.file = null;

      const fileInput = document.getElementById("file");
      if (fileInput) fileInput.value = "";
    },
  },
};
</script>

<style scoped>
textarea {
  resize: none;
}
</style>
