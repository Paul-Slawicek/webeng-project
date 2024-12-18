<template>
  <div
    v-if="isVisible"
    class="message-banner"
    :class="[type, { hide: isHiding }]"
  >
    {{ message }}
  </div>
</template>

<script>
export default {
  name: "MessageBanner",
  data() {
    return {
      isVisible: false,
      isHiding: false,
      message: "",
      type: "success", // Default to success
      duration: 2000,
    };
  },
  methods: {
    show(message, duration = 2000, type = "success") {
      this.message = message;
      this.duration = duration;
      this.type = type; // Set the type (success, error, etc.)
      this.isVisible = true;
      this.isHiding = false;

      // Automatically hide the banner after the duration
      setTimeout(() => {
        this.isHiding = true;
        setTimeout(() => {
          this.isVisible = false;
        }, 500); // Matches the CSS transition time
      }, this.duration);
    },
  },
};
</script>

<style scoped>
.message-banner {
  position: fixed;
  top: 0;
  left: 50%;
  transform: translateX(-50%);
  width: 100%;
  max-width: 500px;
  padding: 15px;
  border-radius: 0 0 5px 5px;
  color: white;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1000;
  transition: opacity 0.2s ease-in, opacity 0.5s ease-out;
  text-align: center;
  opacity: 1;
}

/* Success Style */
.message-banner.success {
  background-color: #28a745;
}

/* Error Style */
.message-banner.error {
  background-color: #dc3545;
}

/* Transition Hide */
.message-banner.hide {
  opacity: 0;
}
</style>
