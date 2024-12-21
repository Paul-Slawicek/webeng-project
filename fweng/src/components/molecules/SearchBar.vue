<template>
  <div>
    <SearchField v-model="internalQuery" :placeholder="placeholder" />
  </div>
</template>

<script>
import SearchField from "@/components/atoms/SearchField.vue";

export default {
  name: "SearchBar",
  components: { SearchField },
  props: {
    searchQuery: {
      type: String,
      default: "Search...",
    },
    placeholder: {
      type: String,
    },
  },
  emits: ["update:searchQuery", "search"],
  computed: {
    internalQuery: {
      get() {
        return this.searchQuery;
      },
      set(value) {
        this.$emit("update:searchQuery", value);
      },
    },
  },
};
</script>