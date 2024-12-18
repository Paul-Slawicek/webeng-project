import { defineStore } from 'pinia';
import { jwtDecode } from "jwt-decode";

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
        return decoded.role || "user";
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
        return decoded.sub || decoded.id;
      } catch (error) {
        console.error("Fehler beim Dekodieren des Tokens:", error);
      }
    },
  },
  actions: {
    login(token) {
      this.token = token;
    },
    logout() {
      this.token = null;
    },
  },
});
