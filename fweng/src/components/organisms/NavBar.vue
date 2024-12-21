<template>
  <nav class="navbar navbar-expand-lg navbar-dark pl-1 pr-1">
    <HomeButton />
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
      aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <MainNavigation />
      <UserControls />
    </div>
  </nav>
</template>

<script>
import HomeButton from "@/components/molecules/HomeButton.vue";
import MainNavigation from "@/components/molecules/MainNavigation.vue";
import UserControls from "@/components/molecules/UserControls.vue";

export default {
  name: "NavBar",
  components: {
    HomeButton,
    MainNavigation,
    UserControls,
  },
};
</script>

<style scoped>
.navbar {
  padding: 10px;
  background: linear-gradient(to bottom, lightblue, ghostwhite);
}
</style>
