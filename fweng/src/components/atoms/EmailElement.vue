<template>
    <input :for="htmlFor" type="email">{{
        text
    }}</input>
</template>

<script>
export default {
    name: 'EmailElement',
    props: {
        htmlFor: {
            type: String,
            required: true,
        },
        text: {
            type: String,
            required: true,
        },
    },
};
</script>

<style scoped></style>