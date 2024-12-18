<template>
  <div class="container-wrapper mb-4">
    <div class="text-center mb-4 mt-3">
      <h1>My Account</h1>
    </div>
    <div class="container d-flex align-items-center justify-content-center">
      <div class="profile-container">
        <div class="row">
          <div class="col-md-12">
            <h2 class="text-center">Edit Profile</h2>
            <form @submit.prevent="updateProfile">
              <!-- Salutation -->
              <div class="form-group row mb-2">
                <label for="anrede" class="col-sm-3 col-form-label">Anrede:</label>
                <div class="col-sm-9">
                  <input type="text" id="anrede" v-model="profileData.salutation" class="form-control"
                    placeholder="Enter salutation" />
                </div>
              </div>
              <!-- First Name -->
              <div class="form-group row mb-2">
                <label for="firstname" class="col-sm-3 col-form-label">Vorname:</label>
                <div class="col-sm-9">
                  <input type="text" id="firstname" v-model="profileData.firstname" class="form-control" />
                </div>
              </div>
              <!-- Last Name -->
              <div class="form-group row mb-2">
                <label for="lastname" class="col-sm-3 col-form-label">Nachname:</label>
                <div class="col-sm-9">
                  <input type="text" id="lastname" v-model="profileData.lastname" class="form-control" />
                </div>
              </div>
              <!-- Username -->
              <div class="form-group row mb-2">
                <label for="username" class="col-sm-3 col-form-label">Username:</label>
                <div class="col-sm-9">
                  <input type="text" id="username" v-model="profileData.username" class="form-control" />
                </div>
              </div>
              <!-- Email -->
              <div class="form-group row mb-2">
                <label for="email" class="col-sm-3 col-form-label">Email:</label>
                <div class="col-sm-9">
                  <input type="email" id="email" v-model="profileData.email" class="form-control" />
                </div>
              </div>
              <!-- New Password -->
              <div class="form-group row mb-2 align-items-center">
                <label for="new-password" class="col-sm-3 col-form-label">Neues Passwort:</label>
                <div class="col-sm-9">
                  <input type="password" id="new-password" v-model="newPassword" class="form-control" />
                </div>
              </div>
              <!-- Confirm Password -->
              <div class="form-group row mb-2 align-items-center">
                <label for="confirm-password" class="col-sm-3 col-form-label">Passwort bestätigen:</label>
                <div class="col-sm-9">
                  <input type="password" id="confirm-password" v-model="confirmPassword" class="form-control" />
                </div>
              </div>
              <!-- Current Password -->
              <div class="form-group row mb-2 align-items-center">
                <label for="current-password" class="col-sm-3 col-form-label">Aktuelles Passwort:</label>
                <div class="col-sm-9">
                  <input type="password" id="current-password" v-model="currentPassword" class="form-control"
                    required />
                </div>
              </div>
              <!-- Profile Image Upload -->
              <div class="form-group mb-4">
                <label for="file" class="form-label">Profilbild hochladen</label>
                <input type="file" id="file" @change="handleImageUpload" class="form-control" accept="image/*" />
              </div>
              <div v-if="previewImage" class="text-center mb-3">
                <img :src="previewImage" alt="Profilbild Vorschau" class="img-thumbnail" width="150" />
              </div>
              <!-- Submit Button -->
              <div class="text-center">
                <button type="submit" class="btn btn-primary">Profil aktualisieren</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "@/services/api";
import { useAuthStore } from "@/stores/authStore";

export default {
  data() {
    return {
      profileData: {
        salutation: "",
        firstname: "",
        lastname: "",
        username: "",
        email: "",
      },
      currentPassword: "",
      newPassword: "",
      confirmPassword: "",
      profileImage: null,
      previewImage: null,
    };
  },
  setup() {
    const authStore = useAuthStore();
    return { userId: authStore.userId };
  },
  methods: {
    async fetchProfileData() {
      try {
        const response = await axios.get(`/users/${this.userId}`);
        if (response.status === 200) {
          this.profileData = response.data;
        }
      } catch (error) {
        console.error("Error fetching profile data:", error);
        this.$root.showMessage("Failed to load profile data. Please try again.", 2000, "error");
      }
    },
    handleImageUpload(event) {
      const file = event.target.files[0];
      if (file) {
        this.profileImage = file;
        const reader = new FileReader();
        reader.onload = (e) => {
          this.previewImage = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },
    async updateProfile() {
      if (!this.currentPassword) {
        this.$root.showMessage("Please enter your current password!", 2000, "error");
        return;
      }

      if (this.newPassword && this.newPassword !== this.confirmPassword) {
        this.$root.showMessage("New passwords do not match!", 2000, "error");
        return;
      }

      try {
        const formData = new FormData();
        formData.append("profileData", JSON.stringify({
          ...this.profileData,
          password: this.currentPassword,
          newPassword: this.newPassword || null,
        })
        );

        if (this.profileImage) {
          formData.append("profileImage", this.profileImage);
        }

        const response = await axios.put(`/users/${this.userId}`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });

        if (response.status === 200) {
          this.$root.showMessage("Profile updated successfully!", 2000, "success");
          this.currentPassword = "";
          this.newPassword = "";
          this.confirmPassword = "";
        } else {
          this.$root.showMessage("Failed to update profile. Please try again.", 2000, "error");
        }
      } catch (error) {
        console.error("Error updating profile:", error);
        if (error.response?.status === 401) {
          this.$root.showMessage("Incorrect current password!", 2000, "error");
        } else {
          this.$root.showMessage(error?.response?.data?.errors?.join("\r\n") ?? error?.response?.data ?? "An error occurred. Please try again later.", 2000, "error");
        }
      }
    },
  },
  mounted() {
    this.fetchProfileData();
  },
};
</script>

<style scoped>
.container-wrapper {
  margin: 0 auto;
  max-width: 800px;
}
</style>
