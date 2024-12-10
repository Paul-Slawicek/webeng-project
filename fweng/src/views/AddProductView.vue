<template>
  <div class="container mt-5 d-flex justify-content-center align-items-center">
    <div>
      <div class="text-center mb-4">
        <h1>Produkt hinzufügen</h1>
      </div>
      <div>
        <form @submit.prevent="addProduct">
          <div class="row justify-content-center">
            <!-- Kategorie -->
            <div class="col-md-6 mb-2">
              <label for="category" class="form-label">Kategorie</label>
              <select id="category" v-model="productData.category" class="form-select" required>
                <option value="" disabled>Kategorie auswählen</option>
                <option value="TV">TV</option>
                <option value="Laptop">Laptop</option>
                <option value="Sound">Sound</option>
              </select>
            </div>
          </div>
          <div class="row">
            <!-- Titel -->
            <div class="col-md-6 mb-2">
              <label for="title" class="form-label">Titel</label>
              <input type="text" id="title" v-model="productData.title" class="form-control"
                placeholder="Titel eingeben" required />
            </div>

            <!-- Preis -->
            <div class="col-md-6 mb-2">
              <label for="price" class="form-label">Preis (€)</label>
              <input type="number" id="price" v-model="productData.price" class="form-control"
                placeholder="Preis eingeben" step="0.01" required />
            </div>
          </div>

          <!-- Beschreibung -->
          <div class="form-group mb-2">
            <label for="description" class="form-label">Beschreibung</label>
            <textarea id="description" v-model="productData.description" class="form-control"
              placeholder="Produktbeschreibung eingeben" rows="4" required></textarea>
          </div>

          <!-- Bild hochladen -->
          <div class="form-group mb-4">
            <label for="file" class="form-label">Bild hochladen</label>
            <input type="file" id="file" @change="handleImageUpload" class="form-control" accept="image/*" />
          </div>

          <div class="text-center">
            <button type="submit" class="btn btn-primary">
              Produkt hinzufügen
            </button>
          </div>
        </form>
      </div>
    </div>
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

        console.log("FormData:", [...formData]); // Debugging

        const response = await axios.post("/products/add", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });

        if (response.status === 201) {
          alert("Produkt erfolgreich hinzugefügt!");
          this.resetForm();
        }
      } catch (error) {
        console.error("Fehler beim Hinzufügen des Produkts:", error);
        alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
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
      document.getElementById("file").value = ""; // Reset file input
    },
  },
};
</script>

<style scoped>
.container {
  max-width: 800px;
}

textarea {
  resize: none;
}
</style>