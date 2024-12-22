<template>
  <div class="container mt-3 mb-5">
    <div class="text-center mb-4">
      <h1>Add Product</h1>
    </div>
    <AddProductForm
      :form="productData"
      @submit="addProduct"
      @updateForm="updateProductData"
      @fileUpload="handleImageUpload"
    />
  </div>
</template>

<script>
import AddProductForm from "@/components/organisms/AddProductForm.vue";
import axios from "@/services/api";

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
  components: { AddProductForm },
  methods: {
    handleImageUpload(file) {
      this.file = file;
    },
    updateProductData({ key, value }) {
      this.productData[key] = value;
    },
    async addProduct() {
      try {
        const formData = new FormData();
        formData.append("product", JSON.stringify(this.productData));
        if (this.file) {
          formData.append("file", this.file);
        }

        const response = await axios.post("/products/add", formData, {
          headers: { "Content-Type": "multipart/form-data" },
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
