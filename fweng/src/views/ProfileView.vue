<template>
  <div class="container-wrapper mb-4">
    <div class="text-center mb-4 mt-3">
      <h1>Mein Account</h1>
    </div>
    <div class="container d-flex align-items-center justify-content-center">
      <div class="profile-container">
        <div class="row">
          <div class="col-md-12">
            <h3 class="text-center">Benutzerprofil</h3>
            <form @submit.prevent="updateProfile">
              <!-- Anrede -->
              <div class="form-group row mb-2">
                <label for="anrede" class="col-sm-3 col-form-label">Anrede:</label>
                <div class="col-sm-9">
                  <input type="text" id="anrede" v-model="profileData.salutation" class="form-control"
                    placeholder="Enter salutation" />
                </div>
              </div>
              <!-- Vorname -->
              <div class="form-group row mb-2">
                <label for="firstname" class="col-sm-3 col-form-label">Vorname:</label>
                <div class="col-sm-9">
                  <input type="text" id="firstname" v-model="profileData.firstname" class="form-control" />
                </div>
              </div>
              <!-- Nachname -->
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
              <!-- Neues Passwort -->
              <div class="form-group row mb-2 align-items-center">
                <label for="new-password" class="col-sm-3 col-form-label">Neues Passwort:</label>
                <div class="col-sm-9">
                  <input type="password" id="new-password" v-model="newPassword" class="form-control" />
                </div>
              </div>
              <!-- Passwort Bestätigen -->
              <div class="form-group row mb-2 align-items-center">
                <label for="confirm-password" class="col-sm-3 col-form-label">Passwort bestätigen:</label>
                <div class="col-sm-9">
                  <input type="password" id="confirm-password" v-model="confirmPassword" class="form-control" />
                </div>
              </div>
              <!-- Aktuelles Passwort -->
              <div class="form-group row mb-2 align-items-center">
                <label for="current-password" class="col-sm-3 col-form-label">Aktuelles Passwort:</label>
                <div class="col-sm-9">
                  <input type="password" id="current-password" v-model="currentPassword" class="form-control"
                    required />
                </div>
              </div>
              <!-- Profilbild Upload -->
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
import axios from "@/services/api"; // API-Client
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
      readonlyFields: {
        firstname: true,
        lastname: true,
        username: true,
        email: true,
      },
      currentPassword: "",
    };
  },
  setup() {
    const authStore = useAuthStore();
    return { userId: authStore.userId };
  },
  methods: {
    async fetchProfileData() {
      console.log("Benutzer-ID:", this.userId);
      if (!this.userId) {
        console.error("Benutzer-ID fehlt!");
        return;
      }

      try {
        const response = await axios.get(`/users/${this.userId}`);
        if (response.status === 200) {
          this.profileData = response.data;
        } else {
          console.warn("Fehlerhafte API-Antwort:", response);
        }
      } catch (error) {
        console.error("Fehler beim Abrufen der Profildaten:", error);
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
      if (!this.userId) {
        console.error("Benutzer-ID fehlt!");
        return;
      }

      if (!this.currentPassword) {
        alert("Bitte geben Sie Ihr aktuelles Passwort ein!");
        return;
      }

      if (this.newPassword && this.newPassword !== this.confirmPassword) {
        alert("Die neuen Passwörter stimmen nicht überein!");
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
          alert("Profil erfolgreich aktualisiert!");
          this.currentPassword = "";
          this.newPassword = "";
          this.confirmPassword = "";
        } else {
          alert("Fehler beim Aktualisieren des Profils: " + response.data);
        }
      } catch (error) {
        console.error("Fehler beim Aktualisieren des Profils:", error);
        if (error.response?.status === 401) {
          alert("Das aktuelle Passwort ist falsch!");
        } else {
          alert(error?.response?.data?.errors?.join('\r\n') ?? error?.response?.data ?? "Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        }
      }
    }

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
