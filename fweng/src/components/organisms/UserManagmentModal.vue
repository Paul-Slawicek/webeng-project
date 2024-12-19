<template>
  <div class="container">
      <div class="text-center mb-4 mt-3">
          <h1>User Management</h1>
      </div>
      <!-- Search Field -->
      <div class="row justify-content-center mb-3">
          <div class="col-6">
              <div class="input-group">
                  <TextinputElement v-model="searchQuery" htmlFor="searchUsers" placeholder="Search users..." />
                  <ButtonElement text="Search" htmlFor="searchUsers" @click="fetchUsers" />
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
                      <ButtonElement text="Edit" htmlFor="editUser" class="btn-primary btn-sm" @click="openUserModal(user)" />
                      <ButtonElement text="Delete" htmlFor="deleteUser" class="btn-danger btn-sm" @click="deleteUser(user.id)" />
                  </td>
              </tr>
          </tbody>
      </table>

      <!-- User Edit Modal -->
      <UserManagementModal
          v-if="showModal"
          :showModal="showModal"
          :selectedUser="selectedUser"
          @save="saveUserChanges"
          @close="showModal = false"
      />
  </div>
</template>

<script>
import axios from "@/services/api";
import TextinputElement from "@/components/atoms/TextinputElement.vue";
import ButtonElement from "@/components/atoms/ButtonElement.vue";
import UserManagementModal from "@/components/organisms/UserManagementModal.vue";

export default {
  name: "UserManagementModal",
  components: {
      TextinputElement,
      ButtonElement,
      UserManagementModal,
  },
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

select {
  padding: 5px;
  border-radius: 5px;
  border: 1px solid #ddd;
}
</style>
