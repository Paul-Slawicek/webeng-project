<template>
    <input :for="htmlFor" type="password">{{
        text
    }}</input>
</template>

<script>
export default {
    name: 'PasswordElement',
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