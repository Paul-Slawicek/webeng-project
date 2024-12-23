<template>
    <div class="container">
        <div class="text-center mb-4 mt-3">
            <h1>User Management</h1>
        </div>

        <div class="row justify-content-center mb-3">
            <div class="col-6">
                <SearchBar v-model:searchQuery="searchQuery" @search="fetchUsers" placeholder="Search users..." />
            </div>
        </div>

        <UserTable :users="filteredUsers" @edit="openUserModal" @delete="deleteUser" @update-status="updateStatus" @update-role="updateRole" />

        <UserEditModal v-if="showModal" :user="selectedUser" @save="saveUserChanges" @close="closeUserModal" />
    </div>
</template>

<script>
import SearchBar from "@/components/molecules/SearchBar.vue";
import UserTable from "@/components/organisms/UserTable.vue";
import UserEditModal from "@/components/organisms/UserEditModal.vue";
import axios from "@/services/api";

export default {
    name: "AdminUserManagementView",
    components: { SearchBar, UserTable, UserEditModal },
    data() {
        return {
            users: [],
            searchQuery: "",
            selectedUser: null,
            showModal: false,
        };
    },
    computed: {
        filteredUsers() {
            return this.users.filter((user) =>
                user.id.toString().includes(this.searchQuery) ||
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
        async updateRole(user) {
            try {
                await axios.put(`/users/admin/${user.id}`, { ...user });
                this.$root.showMessage("Role updated successfully!", 2000, "success");
            } catch (error) {
                console.error("Error updating role:", error);
                this.$root.showMessage("Failed to update user role. Please try again.", 2000, "error");
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
        async saveUserChanges(updatedUser) {
            try {
                await axios.put(`/users/admin/${updatedUser.id}`, updatedUser);
                this.showModal = false;
                this.fetchUsers();
                this.$root.showMessage("User updated successfully!", 2000, "success");
            } catch (error) {
                console.error("Error updating user:", error);
                this.$root.showMessage("Failed to update user. Please try again.", 2000, "error");
            }
        },
        closeUserModal() {
            this.showModal = false;
        },
    },
    mounted() {
        this.fetchUsers();
    },
};
</script>
