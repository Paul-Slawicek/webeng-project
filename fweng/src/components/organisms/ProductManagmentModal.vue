<template>
    <div v-if="showModal" class="modal-backdrop">
        <div class="modal-content">
            <h3>Edit Product</h3>
            <form @submit.prevent="saveProductChanges">
                <div class="form-group">
                    <BadgeElement htmlFor="title" text="Title:" />
                    <TextinputElement v-model="selectedProduct.title" htmlFor="title" required />
                </div>
                <div class="form-group">
                    <BadgeElement htmlFor="price" text="Price:" />
                    <TextinputElement v-model="selectedProduct.price" htmlFor="price" type="number" required />
                </div>
                <div class="form-group">
                    <BadgeElement htmlFor="category" text="Category:" />
                    <TextinputElement v-model="selectedProduct.category" htmlFor="category" />
                </div>
                <div class="form-group">
                    <BadgeElement htmlFor="description" text="Description:" />
                    <textarea v-model="selectedProduct.description" id="description" class="form-control" rows="3"></textarea>
                </div>
                <div class="form-group">
                    <BadgeElement htmlFor="picture" text="Picture URL:" />
                    <TextinputElement v-model="selectedProduct.picture" htmlFor="picture" />
                </div>
                <div class="modal-footer">
                    <ButtonElement htmlFor="save" text="Save" @click.native="saveProductChanges" />
                    <ButtonElement htmlFor="cancel" text="Cancel" @click.native="$emit('close')" />
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import BadgeElement from "@/components/atoms/BadgeElement.vue";
import TextinputElement from "@/components/atoms/TextinputElement.vue";
import ButtonElement from "@/components/atoms/ButtonElement.vue";

export default {
    name: "ProductEditModal",
    components: {
        BadgeElement,
        TextinputElement,
        ButtonElement,
    },
    props: {
        showModal: {
            type: Boolean,
            required: true,
        },
        selectedProduct: {
            type: Object,
            required: true,
        },
    },
    emits: ["close", "save"],
    methods: {
        saveProductChanges() {
            this.$emit("save", this.selectedProduct);
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

.modal-content form .form-group {
    margin-bottom: 15px;
}

.modal-footer {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
}
</style>
