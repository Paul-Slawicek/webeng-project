<template>
  <div class="search-bar">
    <input
      v-model="searchQuery"
      type="text"
      class="form-control"
      placeholder="Search..."
    />
    <button @click="$emit('search', searchQuery)" class="btn btn-outline-secondary">
      Search
    </button>
  </div>
</template>

<script>
export default {
  name: "SearchBarElement",
  props: {
    searchQuery: {
      type: String,
      default: "",
    },
  },
  emits: ["search"],
};
</script>

<style scoped>
.search-bar {
  display: flex;
  gap: 0.5rem;
  align-items: center;
  justify-content: center;
}
.form-control {
  flex: 1;
  padding: 0.5rem;
  border-radius: 4px;
  border: 1px solid #ccc;
}
.btn {
  padding: 0.5rem 1rem;
  border-radius: 4px;
  border: none;
}
</style>
