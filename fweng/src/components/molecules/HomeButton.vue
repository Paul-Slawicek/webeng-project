<template>
    <router-link to="/" class="navbar-brand">
        <img src="../../assets/img/basseno.png" class="bassenoBild" alt="Basseno Home Logo" />
    </router-link>
</template>

<script>
export default {
    name: "HomeButton",
};
</script>

<style scoped>
.navbar-brand {
    transition: transform 0.3s ease;
    padding-left: calc((180px * 0.1) / 2);
}

.navbar-brand:hover {
    transform: scale(1.1);
}

.bassenoBild {
    width: 180px;
}
</style>