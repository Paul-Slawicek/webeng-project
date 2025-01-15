<template>
    <div class="container-wrapper mb-4">
        <div class="text-center mb-4 mt-3">
            <h1>My Profile</h1>
        </div>

        <div class="container d-flex align-items-center justify-content-center">
            <div class="profile-container">
                <div class="row">
                    <div class="col-md-12">
                        <h3 class="text-center">Profile Details</h3>
                        <div class="profile-details text-center mb-4">
                            <div class="mb-3">
                                <img :src="getImageUrl(profileData.picture)" alt="Profile Picture" class="img-thumbnail"
                                    width="150" />
                            </div>
                            <p><strong>Salutation:</strong> {{ profileData.salutation }}</p>
                            <p><strong>First Name:</strong> {{ profileData.firstname }}</p>
                            <p><strong>Last Name:</strong> {{ profileData.lastname }}</p>
                            <p><strong>Username:</strong> {{ profileData.username }}</p>
                            <p><strong>Email:</strong> {{ profileData.email }}</p>
                        </div>
                        <div class="text-center">
                            <button class="btn btn-primary" @click="navigateToEdit">
                                Edit Profile
                            </button>
                        </div>
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
    name: "ProfileView",
    data() {
        return {
            profileData: {
                salutation: "",
                firstname: "",
                lastname: "",
                username: "",
                email: "",
                picture: "",
            },
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
                this.$root.showMessage(
                    "Failed to load profile data. Please try again.",
                    2000,
                    "error"
                );
            }
        },
        getImageUrl(picture) {
            if (picture) {
                return `http://localhost:8080/uploads/${picture}`;
            }
            return require("@/assets/img/default-profile.png");
        },
        navigateToEdit() {
            this.$router.push("/profileEdit");
        },
    },

    mounted() {
        this.fetchProfileData();
    },
};
</script>

<style>
.container-wrapper {
    margin: 0 auto;
    max-width: 800px;
}

.profile-details p {
    margin: 5px 0;
}
</style>