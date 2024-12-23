<template>
  <div class="modal-backdrop">
    <div class="modal-content">
      <h3 class="modal-title">Edit Product</h3>
      <form @submit.prevent="handleSave">
        <div class="form-group">
          <InputField type="text" id="title" label="Title" v-model="localProduct.title" placeholder="Enter title" />
        </div>
        <div class="form-group">
          <InputField type="number" id="price" label="Price" v-model="localProduct.price" placeholder="Enter price" />
        </div>
        <div class="form-group">
          <InputField type="text" id="category" label="Category" v-model="localProduct.category"
            placeholder="Enter category" />
        </div>
        <div class="form-group">
          <InputField type="textarea" id="description" label="Description" v-model="localProduct.description"
            placeholder="Enter description" />
        </div>
        <div class="form-group">
          <InputField type="text" id="picture" label="Picture URL" v-model="localProduct.picture"
            placeholder="Enter picture URL" />
        </div>
        <div class="modal-footer">
          <ButtonField type="submit" class="btn-save">Save</ButtonField>
          <ButtonField @click="$emit('close')" class="btn-cancel">Cancel</ButtonField>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import InputField from "@/components/atoms/InputField.vue";
import ButtonField from "@/components/atoms/ButtonField.vue";

export default {
  name: "ProductEditModal",
  components: { InputField, ButtonField },
  props: {
    product: {
      type: Object,
      required: true,
    },
  },
  emits: ["save", "close"],
  data() {
    return {
      localProduct: { ...this.product },
    };
  },
  methods: {
    handleSave() {
      if (this.localProduct.picture === "") {
        this.localProduct.picture = null;
      }
      this.$emit("save", this.localProduct);
    },
  },
};
</script>

<style scoped>
.modal-backdrop {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
}

.modal-content {
  background: #ffffff;
  padding: 20px;
  border-radius: 10px;
  width: 600px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-title {
  font-size: 24px;
  font-weight: bold;
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.modal-label {
  font-weight: bold;
  margin-bottom: 5px;
  display: block;
}


.form-group input:focus,
.form-group textarea:focus {
  border-color: #007bff;
  outline: none;
}

.modal-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.btn-save {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
}

.btn-save:hover {
  background-color: #218838;
}

.btn-cancel {
  background-color: #6c757d;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
}

.btn-cancel:hover {
  background-color: #5a6268;
}
</style>
