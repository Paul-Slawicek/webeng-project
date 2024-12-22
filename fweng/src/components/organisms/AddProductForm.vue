<template>
  <form @submit.prevent="onSubmit" class="mx-auto p-4" style="max-width: 700px; background-color: #f9f9ff;">
    <div class="text-center mb-4">
    </div>

    <div class="mb-4">
      <DropdownField
        id="category"
        label="Category"
        placeholder="Select a category"
        :options="categories"
        :modelValue="form.category"
        @update:modelValue="updateForm('category', $event)"
      />
    </div>

    <!-- Title and Price -->
    <div class="row g-3 mb-4">
      <div class="col-md-6">
        <InputGroup
          id="title"
          label="Title"
          placeholder="Enter title"
          :modelValue="form.title"
          required
          @update:modelValue="updateForm('title', $event)"
        />
      </div>
      <div class="col-md-6">
        <InputGroup
          id="price"
          label="Price (€)"
          type="number"
          placeholder="Enter price"
          :modelValue="form.price"
          required
          @update:modelValue="updateForm('price', $event)"
        />
      </div>
    </div>

    <!-- Description -->
    <div class="mb-4">
      <InputGroup
        id="description"
        label="Description"
        type="textarea"
        placeholder="Enter product description"
        :modelValue="form.description"
        required
        @update:modelValue="updateForm('description', $event)"
      />
    </div>

    <!-- File Upload -->
    <div class="mb-4">
      <label for="file" class="form-label fw-bold">Upload Image</label>
      <input
        type="file"
        id="file"
        class="form-control form-control-lg"
        @change="onFileUpload"
        accept="image/*"
      />
    </div>

    <!-- Submit Button -->
    <div class="text-center">
      <ButtonField type="submit" class="btn btn-primary btn-lg px-5">
        Add Product
      </ButtonField>
    </div>
  </form>
</template>

<script>
import InputGroup from "@/components/molecules/InputGroup.vue";
import DropdownField from "@/components/atoms/DropdownField.vue";
import ButtonField from "@/components/atoms/ButtonField.vue";

export default {
  name: "AddProductForm",
  props: {
    form: { type: Object, required: true },
  },
  emits: ["submit", "updateForm", "fileUpload"],
  components: { InputGroup, DropdownField, ButtonField },
  data() {
    return {
      categories: [
        { value: "TV", label: "TV" },
        { value: "Laptop", label: "Laptop" },
        { value: "Speakers", label: "Speakers" },
      ],
    };
  },
  methods: {
    onSubmit() {
      this.$emit("submit");
    },
    updateForm(key, value) {
      this.$emit("updateForm", { key, value });
    },
    onFileUpload(event) {
      this.$emit("fileUpload", event.target.files[0]);
    },
  },
};
</script>

<style scoped>
form {
  background-color: #f9f9ff;
}

h1 {
  font-size: 2rem;
  color: #2c3e50;
}

label {
  font-weight: bold;
}

button {
  background-color: #007bff;
  color: white;
}
</style>
