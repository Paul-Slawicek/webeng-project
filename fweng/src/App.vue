<template>
  <div id="app">
    <NavBar />
    <MessageBanner ref="messageBanner" />
    <router-view />
  </div>
</template>

<script>
import './assets/css/override.css';
import NavBar from './components/organisms/NavBar.vue';
import MessageBanner from './components/molecules/MessageBanner.vue';

export default {
  name: "App",
  components: {
    NavBar,
    MessageBanner,
  },
  methods: {
    showMessage(message, duration = 2000, type = "success") {
      this.$refs.messageBanner.show(message, duration, type);
    },
  },
  mounted() {
    this.$root.showMessage = this.showMessage;
  },
};
</script>
