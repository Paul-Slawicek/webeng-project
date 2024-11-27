import axios from "axios";
import { useAuthStore } from "@/stores/authStore";

const apiClient = axios.create({
  baseURL: "http://localhost:8080/api", // Dein Backend
  headers: {
    "Content-Type": "application/json",
  },
});

// Request-Interceptor: JWT-Token automatisch hinzufügen
apiClient.interceptors.request.use(
  (config) => {

    const authStore = useAuthStore();
    const token = authStore.token;

    if (token) {
      config.headers["Authorization"] = `Bearer ${token}`;
    }

    return config;
  },
  (error) => {
    return Promise.reject(error); // Fehler im Request weitergeben
  }
);

// Response-Interceptor: Globale Fehlerbehandlung
apiClient.interceptors.response.use(
  (response) => {
    return response; // Erfolgreiche Antworten unverändert zurückgeben
  },
  (error) => {
    console.error("API Error:", error.response || error.message); // Fehler loggen
    return Promise.reject(error); // Fehler für spezifische Behandlung weitergeben
  }
);

export default apiClient;
