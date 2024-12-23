<template>
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
            <tr v-for="product in products" :key="product.id">
                <td>{{ product.id }}</td>
                <td>{{ product.title }}</td>
                <td>{{ product.price }}</td>
                <td>{{ product.category }}</td>
                <td>
                    <ButtonField @click="$emit('edit', product)" class="btn btn-primary btn-sm me-2">
                        Edit
                    </ButtonField>
                    <ButtonField @click="$emit('delete', product.id)" class="btn btn-danger btn-sm">
                        Delete
                    </ButtonField>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script>
import ButtonField from "@/components/atoms/ButtonField.vue";

export default {
    name: "ProductTable",
    components: { ButtonField },
    props: {
        products: {
            type: Array,
            required: true,
        },
    },
    emits: ["edit", "delete"],
};
</script>

<style scoped>
.btn:last-child {
    margin-right: 0;
}
</style>