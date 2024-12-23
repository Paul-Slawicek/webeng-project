<template>
  <div class="container-wrapper mb-4">
    <div class="text-center mb-4 mt-3">
      <h1>My Account</h1>
    </div>
    <div class="container d-flex align-items-center justify-content-center">
      <div class="profile-container">
        <h2 class="text-center">Edit Profile</h2>
        <ProfileForm :fields="fields" :form="profileData" :currentPassword="currentPassword"
          :previewImage="previewImage" @submit="updateProfile" @update:form="updateProfileData"
          @update:currentPassword="currentPassword = $event" @imageUpload="handleImageUpload" />
      </div>
    </div>
  </div>
</template>

<script>
import ProfileForm from "@/components/organisms/ProfileForm.vue";
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
        newPassword: "",
        confirmPassword: "",
      },
      currentPassword: "",
      previewImage: null,
    };
  },
  setup() {
    const authStore = useAuthStore();
    return { userId: authStore.userId };
  },
  computed: {
    fields() {
      return [
        { label: "Salutation", id: "salutation", type: "text", placeholder: "Enter salutation"},
        { label: "First Name", id: "firstname", type: "text", placeholder: "Enter first name" },
        { label: "Last Name", id: "lastname", type: "text", placeholder: "Enter last name" },
        { label: "Username", id: "username", type: "text", placeholder: "Enter username" },
        { label: "Email", id: "email", type: "email", placeholder: "Enter email" },
        { label: "New Password", id: "newPassword", type: "password", placeholder: "Enter new password" },
        { label: "Confirm Password", id: "confirmPassword", type: "password", placeholder: "Confirm password" },
      ];
    },
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
      }
    },
    updateProfileData(newData) {
      this.profileData = { ...newData };
    },
    handleImageUpload(event) {
      const file = event.target.files[0];
      if (file) {
        this.previewImage = file;
      }
    },
    async updateProfile() {
      if (!this.currentPassword) {
        this.$root.showMessage("Please enter your current password!", 2000, "error");
        return;
      }

      if (
        this.profileData.newPassword &&
        this.profileData.newPassword !== this.profileData.confirmPassword
      ) {
        this.$root.showMessage("Passwords do not match!", 2000, "error");
        return;
      }

      try {
        const requestData = {
          ...this.profileData,
          password: this.currentPassword,
          newPassword: this.profileData.newPassword || null,
        };
        delete requestData.confirmPassword;

        const formData = new FormData();
        formData.append("profileData", JSON.stringify(requestData)); 
        if (this.previewImage) {
          formData.append("profileImage", this.previewImage); 
        }
        const response = await axios.put(`/users/${this.userId}`, formData, {
          headers: { "Content-Type": "multipart/form-data" },
        });

        if (response.status === 200) {
          this.$root.showMessage("Profile updated successfully!", 2000, "success");
        } else {
          this.$root.showMessage("Failed to update profile. Please try again.", 2000, "error");
        }
      } catch (error) {
        console.error("Error updating profile:", error);
        this.$root.showMessage(
          error?.response?.data || "An error occurred. Please try again later.",
          2000,
          "error"
        );
      }
    },
  },
  mounted() {
    this.fetchProfileData();
  },
  components: { ProfileForm },
};
</script>
