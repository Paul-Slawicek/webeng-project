<template>
    <input
        :id="htmlFor"
        type="password"
        :placeholder="text"
        :value="modelValue"
        @input="$emit('update:modelValue', $event.target.value)"
    />
</template>

<script>
export default {
    name: "PasswordElement",
    props: {
        htmlFor: {
            type: String,
            required: true,
        },
        text: {
            type: String,
            required: true,
        },
        modelValue: {
            type: String,
            required: true,
        },
    },
};
</script>

<style scoped></style>
