<template>
    <img :for="htmlFor" >{{
        text
    }}</input>
</template>

<script>
export default {
    name: 'ImageElement',
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