<template>
  <div class="mb-3">
    <input :type="type" class="form-control" :id="id" :placeholder="placeholder" :value="modelValue"
      @input="$emit('update:modelValue', $event.target.value)" />
    <label :for="id">{{ label }}</label>
  </div>
</template>

<script>
export default {
  name: "SearchbarField",
  props: {
    type: {
      type: String,
    },
    id: {
      type: String,
    },
    placeholder: {
      type: String,
    },
    label: {
      type: String,
    },
    modelValue: {
      type: String,
    },
  },
};
</script>