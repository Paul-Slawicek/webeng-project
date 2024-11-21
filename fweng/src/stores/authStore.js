import { defineStore } from 'pinia';

export const useAuthStore = defineStore('auth', {
  state: () => ({
    isLoggedIn: !!localStorage.getItem('jwtToken') || !!sessionStorage.getItem('jwtToken'),
  }),
  actions: {
    login(token, rememberMe) {
      // Token speichern
      if (rememberMe) {
        localStorage.setItem('jwtToken', token);
      } else {
        sessionStorage.setItem('jwtToken', token);
      }
      this.isLoggedIn = true; // Zustand aktualisieren
    },
    logout() {
      localStorage.removeItem('jwtToken');
      sessionStorage.removeItem('jwtToken');
      this.isLoggedIn = false; // Zustand aktualisieren
    },
  },
});
