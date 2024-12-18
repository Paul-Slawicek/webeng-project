<template>
    <div class="container">
        <div class="text-center mb-4 mt-3">
            <h1>User Management</h1>
        </div>
        <!-- Search Field -->
        <div class="row justify-content-center mb-3">
            <div class="col-6">
                <div class="input-group">
                    <input v-model="searchQuery" type="text" class="form-control" placeholder="Search users..." />
                    <button @click="fetchUsers" class="btn btn-outline-secondary">Search</button>
                </div>
            </div>
        </div>
        <!-- User List -->
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
                        <button @click="openUserModal(user)" class="btn btn-primary btn-sm">Edit</button>
                        <button @click="deleteUser(user.id)" class="btn btn-danger btn-sm">Delete</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <!-- User Edit Modal -->
        <div v-if="showModal" class="modal-backdrop">
            <div class="modal-content">
                <h3>Edit User</h3>
                <form @submit.prevent="saveUserChanges">
                    <div class="row">
                        <!-- Column 1 -->
                        <div class="col">
                            <div>
                                <label>Username:</label>
                                <input v-model="selectedUser.username" type="text" required />
                            </div>
                            <div>
                                <label>Password:</label>
                                <input v-model="selectedUser.newPassword" type="password"
                                    placeholder="Enter new password or leave blank" />
                            </div>
                            <div>
                                <label>Email:</label>
                                <input v-model="selectedUser.email" type="email" required />
                            </div>
                            <div>
                                <label>Role:</label>
                                <select v-model="selectedUser.role">
                                    <option value="user">User</option>
                                    <option value="admin">Admin</option>
                                </select>
                            </div>
                            <div>
                                <label>Salutation:</label>
                                <input v-model="selectedUser.salutation" type="text" placeholder="Enter salutation" />
                            </div>
                        </div>

                        <!-- Column 2 -->
                        <div class="col">
                            <div>
                                <label>First Name:</label>
                                <input v-model="selectedUser.firstname" type="text" />
                            </div>
                            <div>
                                <label>Last Name:</label>
                                <input v-model="selectedUser.lastname" type="text" />
                            </div>
                            <div>
                                <label>Address:</label>
                                <input v-model="selectedUser.address" type="text" />
                            </div>
                            <div>
                                <label>City:</label>
                                <input v-model="selectedUser.city" type="text" />
                            </div>
                            <div>
                                <label>PLZ:</label>
                                <input v-model="selectedUser.plz" type="text" />
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success">Save</button>
                        <button type="button" @click="showModal = false" class="btn btn-secondary">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
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
            selectedUser: null, // Currently selected user for editing
            showModal: false, // Modal visibility control
        };
    },
    computed: {
        filteredUsers() {
            return this.users.filter((user) =>
                    user.username.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
                    user.email.toLowerCase().includes(this.searchQuery.toLowerCase())
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
                this.$root.showMessage("Failed to fetch users. Please try again.", 2000, "error");
            }
        },
        async updateStatus(user) {
            try {
                await axios.put(`/users/admin/${user.id}`, { ...user });
                this.$root.showMessage("Status updated successfully!", 2000, "success");
            } catch (error) {
                console.error("Error updating status:", error);
                this.$root.showMessage("Failed to update user status. Please try again.", 2000, "error");
            }
        },
        async deleteUser(userId) {
            if (!confirm("Are you sure you want to delete this user?")) return;

            try {
                await axios.delete(`/users/admin/${userId}`);
                this.fetchUsers();
                this.$root.showMessage("User deleted successfully!", 2000, "success");
            } catch (error) {
                console.error("Error deleting user:", error);
                this.$root.showMessage("Failed to delete user. Please try again.", 2000, "error");
            }
        },
        openUserModal(user) {
            this.selectedUser = { ...user };
            this.showModal = true;
        },
        async saveUserChanges() {
            try {
                await axios.put(`/users/admin/${this.selectedUser.id}`, this.selectedUser);
                this.showModal = false;
                this.fetchUsers();
                this.$root.showMessage("User updated successfully!", 2000, "success");
            } catch (error) {
                console.error("Error updating user:", error);
                this.$root.showMessage("Failed to update user. Please try again.", 2000, "error");
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

.modal-content form .row {
    display: flex;
    justify-content: space-between;
}

.modal-content form .col {
    flex: 1;
    margin-right: 10px;
}

.modal-content form .col:last-child {
    margin-right: 0;
}

.modal-content form div {
    margin-bottom: 15px;
}

.modal-content label {
    display: block;
    font-weight: bold;
}

.modal-content input,
.modal-content select {
    width: 100%;
    padding: 8px;
    margin-top: 5px;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.modal-footer {
    display: flex;
    justify-content: flex-end;
    margin-top: 20px;
}

.btn {
    margin-right: 10px;
}

.btn:last-child {
    margin-right: 0;
}
</style>