<template>
  <div class="form-floating">
    <select class="form-select" :id="id" :value="modelValue" @change="$emit('update:modelValue', $event.target.value)"
      :required="required">
      <option value="" disabled hidden>{{ placeholder }}</option>
      <option v-for="(option, index) in options" :key="index" :value="option.value">
        {{ option.label }}
      </option>
    </select>
    <label :for="id">{{ label }}</label>
  </div>
</template>

<script>
export default {
  name: "DropdownField",
  props: {
    id: { type: String, required: true },
    label: { type: String, required: true },
    placeholder: { type: String, required: true },
    options: { type: Array, required: true },
    modelValue: { type: String, default: "" },
    required: { type: Boolean, default: true },
  },
};
</script>

<style scoped>
.form-floating label {
  text-align: left;
  margin-left: 0;
}
</style>
