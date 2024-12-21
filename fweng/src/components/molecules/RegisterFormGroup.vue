<template>
    <div class="row mb-2">
      <!-- Input Fields -->
      <template v-for="(input, index) in inputs">
        <InputField
          v-if="input.type !== 'select'"
          :key="index"
          :type="input.type"
          :id="input.id"
          :label="input.label"
          :placeholder="input.placeholder"
          :maxlength="input.maxlength"
          :required="input.required"
          :modelValue="input.modelValue"
          @update:modelValue="$emit('update:' + input.id, $event)"
        />
        <DropdownField
          v-else
          :key="`dropdown-${index}`"
          :id="input.id"
          :label="input.label"
          :placeholder="input.placeholder"
          :options="dropdownOptions"
          :modelValue="input.modelValue"
          @update:modelValue="$emit('update:' + input.id, $event)"
        />
      </template>
    </div>
  </template>
  
  <script>
  import InputField from "@/components/atoms/InputField.vue";
  import DropdownField from "@/components/atoms/DropdownField.vue";
  
  export default {
    name: "RegistrationFormGroup",
    components: { InputField, DropdownField },
    props: {
      inputs: { type: Array, required: true },
      dropdownOptions: { type: Array, default: () => [] },
    },
  };
  </script>
  