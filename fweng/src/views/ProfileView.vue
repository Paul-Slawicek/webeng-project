<template>
  <div class="container-wrapper">
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
                  <select id="anrede" v-model="profileData.salutation" class="form-control">
                    <option value="Herr">Herr</option>
                    <option value="Frau">Frau</option>
                  </select>
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
              <div class="form-group row mb-2 align-items-center">
                <label for="new-password" class="col-sm-3 col-form-label">Neues Passwort:</label>
                <div class="col-sm-9">
                  <input type="password" id="new-password" v-model="newPassword" class="form-control" />
                </div>
              </div>

              <div class="form-group row mb-2 align-items-center">
                <label for="confirm-password" class="col-sm-3 col-form-label">Passwort bestätigen:</label>
                <div class="col-sm-9">
                  <input type="password" id="confirm-password" v-model="confirmPassword" class="form-control" />
                </div>
              </div>

              <div class="form-group row mb-2 align-items-center">
                <label for="current-password" class="col-sm-3 col-form-label">Aktuelles Passwort:</label>
                <div class="col-sm-9">
                  <input type="password" id="current-password" v-model="currentPassword" class="form-control"
                    required />
                </div>
              </div>

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
    const authStore = useAuthStore(); // Zugriff auf den Pinia-Store
    return { userId: authStore.userId }; // Zur Nutzung in Methoden verfügbar machen
  },
  methods: {
    /**
     * Ruft die Profildaten des Benutzers vom Backend ab.
     */
    async fetchProfileData() {
      console.log("Benutzer-ID:", this.userId);
      if (!this.userId) {
        console.error("Benutzer-ID fehlt!");
        return;
      }

      try {
        const response = await axios.get(`/users/${this.userId}`);

        if (response.status === 200) {
          this.profileData = response.data; // Profildaten zuweisen
        } else {
          console.warn("Fehlerhafte API-Antwort:", response);
        }
      } catch (error) {
        console.error("Fehler beim Abrufen der Profildaten:", error);
      }
    },

    /**
     * Aktualisiert die Profildaten des Benutzers über die API.
     */
    async updateProfile() {
      if (!this.userId) {
        console.error("Benutzer-ID fehlt!");
        return;
      }

      // Validierung: Aktuelles Passwort muss angegeben werden
      if (!this.currentPassword) {
        alert("Bitte geben Sie Ihr aktuelles Passwort ein!");
        return;
      }

      // Validierung: Neues Passwort und Bestätigung müssen übereinstimmen
      if (this.newPassword || this.confirmPassword) {
        if (this.newPassword !== this.confirmPassword) {
          alert("Die neuen Passwörter stimmen nicht überein!");
          return;
        }
      }

      try {
        const payload = {
          ...this.profileData, // Profildaten aus den Eingabefeldern
          password: this.currentPassword, // Aktuelles Passwort
        };

        // Füge `newPassword` nur hinzu, wenn es eingegeben wurde
        if (this.newPassword) {
          payload.newPassword = this.newPassword;
        }

        const response = await axios.put(`/users/${this.userId}`, payload);

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
          alert("Ein Fehler ist aufgetreten. Bitte versuchen Sie es später erneut.");
        }
      }
    }
  },

  mounted() {
    this.fetchProfileData(); // Profildaten laden
  },
};
</script>


<style scoped></style>
