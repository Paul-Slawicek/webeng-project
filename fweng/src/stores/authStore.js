import { defineStore } from 'pinia';
import { jwtDecode } from "jwt-decode"; // JWT-Dekodierung

export const useAuthStore = defineStore('auth', {
  state: () => ({ token: undefined }),
  getters: {
    isLoggedIn: (state) => !!state.token,
    role(state) {
      try {
        if (!state.token) {
          console.warn("Kein JWT-Token gefunden!");
          return;
        }
        const decoded = jwtDecode(state.token);
        console.debug("Decoded Token:", decoded); // Nur für Debugging
        return decoded.role || "user"; // Extrahiere Benutzerrolle
      } catch (error) {
        console.error("Fehler beim Dekodieren des Tokens:", error);
      }
    },
    userId(state) {
      try {
        if (!state.token) {
          console.warn("Kein JWT-Token gefunden!");
          return;
        }
        console.log(state.token);
        const decoded = jwtDecode(state.token);
        console.debug("Decoded Token:", decoded); // Nur für Debugging
        return decoded.sub || decoded.id; // Extrahiere Benutzer-ID
      } catch (error) {
        console.error("Fehler beim Dekodieren des Tokens:", error);
      }
    }
  },
  actions: {
    login(token, rememberMe) {
      this.token = token;
      console.log('rememberMe', rememberMe);
    },
    logout() {
      this.token = null;
    },
  },
});
