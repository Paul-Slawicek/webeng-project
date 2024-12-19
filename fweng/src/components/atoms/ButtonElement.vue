<template>
    <button :for="htmlFor" class="btn btn-primary">{{
        text
    }}</button>
</template>

<script>
export default {
    name: 'ButtonElement',
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