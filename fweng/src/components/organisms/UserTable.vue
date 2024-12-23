<template>
    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Email</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="user in users" :key="user.id">
                <td>{{ user.id }}</td>
                <td>{{ user.username }}</td>
                <td>{{ user.email }}</td>
                <td>
                    <select v-model="user.status" @change="$emit('update-status', user)">
                        <option value="active">Active</option>
                        <option value="inactive">Inactive</option>
                    </select>
                </td>
                <td>
                    <ButtonField @click="$emit('edit', user)" class="btn btn-primary btn-sm me-2">Edit</ButtonField>
                    <ButtonField @click="$emit('delete', user.id)" class="btn btn-danger btn-sm">Delete</ButtonField>
                </td>
            </tr>
        </tbody>
    </table>
</template>

<script>
import ButtonField from "@/components/atoms/ButtonField.vue";

export default {
    name: "UserTable",
    components: { ButtonField },
    props: {
        users: {
            type: Array,
            required: true,
        },
    },
    emits: ["edit", "delete", "update-status"],
};
</script>

<style scoped>
.btn:last-child {
    margin-right: 0;
}
</style>
