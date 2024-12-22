<template>
  <div class="form-floating mb-3">
    <input
        :type="type"
        class="form-control"
        :id="id"
        :class="customClass"
        :placeholder="placeholder"
        :value="modelValue"
        :maxlength="maxlength"
        :required="required"
        @input="$emit('update:modelValue', $event.target.value)"
    />
    <label :for="id">{{ label }}</label>
  </div>
</template>

<script>
export default {
  name: "InputField",
  props: {
    type: {
      type: String,
      required: true,
    },
    id: {
      type: String,
      required: true,
    },
    placeholder: {
      type: String,
      required: true,
    },
    label: {
      type: String,
      required: true,
    },
    modelValue: {
      type: String,
      required: true,
    },
    maxlength: {
      type: Number,
      default: null,
    },
    required: {
      type: Boolean,
      default: false, 
    },
    customClass: {
      type: String,
      default: "", 
    },
  },
};
</script>

<style scoped>
.form-floating label {
  text-align: left;
  margin-left: 0;
}
</style>
  