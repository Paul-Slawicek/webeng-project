<template>
  <InputField :label="label" :id="id" :type="type" :modelValue="modelValue"
    @update:modelValue="$emit('update:modelValue', $event)" :placeholder="placeholder" />
</template>

<script>
import InputField from "@/components/atoms/InputField.vue";

export default {
  props: {
    label: String,
    id: String,
    type: { type: String, default: "text" },
    placeholder: String,
    modelValue: String,
  },
  emits: ["update:modelValue"],
  components: { InputField },
};
</script>