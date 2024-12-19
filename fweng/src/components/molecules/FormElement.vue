<template>
  <div class="mb-6">
    <BadgeElement :htmlFor="id" :text="labelText" />

    <Input
        :id="id"
        :class="class"
        :type="type"
        v-model="internalValue"
        :placeholder="placeholder"
         />
  </div>
</template>

<script>
import BadgeElement from '@/components/atoms/BaseLabel.vue';
import Input from '@/components/atoms/BaseInput.vue';

export default {
  name: 'FormElement',
  components: {
    BaseLabel,
    Input,
  },
  props: {
    id: {
      type: String,
      required: true,
    },
    class: {
      type: String,
      default: 'form-control',
    },
    type: {
      type: String,
      default: 'text',
    },
    modelValue: {
      type: [String, Number],
      default: '',
    },
    placeholder: {
      type: [String, Number],
      default: '',
    },
  },
};
</script>