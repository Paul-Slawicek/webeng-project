<template>
    <div v-if="showModal" class="modal-backdrop">
        <div class="modal-content">
            <h3>Edit Order</h3>
            <form @submit.prevent="saveOrderChanges">
                <div class="form-group">
                    <BadgeElement htmlFor="id" text="Order ID:" />
                    <TextinputElement v-model="selectedOrder.id" htmlFor="id" readonly />
                </div>
                <div class="form-group">
                    <BadgeElement htmlFor="userId" text="User ID:" />
                    <TextinputElement v-model="selectedOrder.userId" htmlFor="userId" readonly />
                </div>
                <div class="form-group">
                    <BadgeElement htmlFor="productId" text="Product ID:" />
                    <TextinputElement v-model="selectedOrder.productId" htmlFor="productId" readonly />
                </div>
                <div class="form-group">
                    <BadgeElement htmlFor="quantity" text="Quantity:" />
                    <TextinputElement v-model="selectedOrder.quantity" htmlFor="quantity" type="number" />
                </div>
                <div class="form-group">
                    <BadgeElement htmlFor="totalPrice" text="Total Price:" />
                    <TextinputElement v-model="selectedOrder.totalPrice" htmlFor="totalPrice" type="number" readonly />
                </div>
                <div class="form-group">
                    <BadgeElement htmlFor="status" text="Status:" />
                    <select v-model="selectedOrder.status" id="status" class="form-control">
                        <option value="pending">Pending</option>
                        <option value="shipped">Shipped</option>
                        <option value="delivered">Delivered</option>
                        <option value="cancelled">Cancelled</option>
                    </select>
                </div>
                <div class="modal-footer">
                    <ButtonElement htmlFor="save" text="Save" @click.native="saveOrderChanges" />
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
    name: "OrderEditModal",
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
        selectedOrder: {
            type: Object,
            required: true,
        },
    },
    emits: ["close", "save"],
    methods: {
        saveOrderChanges() {
            this.$emit("save", this.selectedOrder);
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
