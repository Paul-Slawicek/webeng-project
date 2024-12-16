<template>
    <div class="container">
        <div class="text-center mb-4 mt-3">
            <h1>User Management</h1>
        </div>
        <!-- Suchfeld -->
        <div class="row justify-content-center mb-3">
            <div class="col-6">
                <div class="input-group">
                    <input v-model="searchQuery" type="text" class="form-control" placeholder="Search users..." />
                    <button @click="fetchUsers" class="btn btn-outline-secondary">Search</button>
                </div>
            </div>
        </div>
        <!-- Benutzerliste -->
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
                <tr v-for="user in filteredUsers" :key="user.id">
                    <td>{{ user.id }}</td>
                    <td>{{ user.username }}</td>
                    <td>{{ user.email }}</td>
                    <td>
                        <select v-model="user.status" @change="updateStatus(user)">
                            <option value="active">Active</option>
                            <option value="inactive">Inactive</option>
                        </select>
                    </td>
                    <td>
                        <button @click="deleteUser(user.id)" class="btn btn-danger btn-sm">Delete</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
import axios from "@/services/api";

export default {
    name: "AdminUserManagementView",
    data() {
        return {
            users: [],
            searchQuery: "",
        };
    },
    computed: {
        filteredUsers() {
            return this.users.filter((user) =>
                user.username.toLowerCase().includes(this.searchQuery.toLowerCase())
            );
        },
    },
    methods: {
        async fetchUsers() {
            try {
                const response = await axios.get("/users/admin");
                this.users = response.data;
            } catch (error) {
                console.error("Error fetching users:", error);
            }
        },
        async updateStatus(user) {
            try {
                await axios.put(`/users/admin/${user.id}`, { ...user });
                alert("Status updated successfully!");
            } catch (error) {
                console.error("Error updating status:", error);
            }
        },
        async deleteUser(userId) {
            if (!confirm("Are you sure you want to delete this user?")) return;

            try {
                await axios.delete(`/users/admin/${userId}`);
                this.fetchUsers();
                alert("User deleted successfully!");
            } catch (error) {
                console.error("Error deleting user:", error);
            }
        },
    },
    mounted() {
        this.fetchUsers();
    },
};
</script>

<style scoped>
.table {
    margin-top: 20px;
}

.input-group {
    max-width: 500px;
    margin: auto;
}
</style>