<template>
    <input :for="htmlFor" type="text">{{
        text
    }}</input>
</template>

<script>
export default {
    name: 'TextinputElement',
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