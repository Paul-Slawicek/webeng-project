<template>
    <div class="text-center mb-4 mt-3">
        <h1>{{ text }}</h1>
    </div>
</template>

<script>
export default {
    name: "HeaderElement",
    props: {
        text: {
            type: String,
            required: true,
        },
    },
};
</script>