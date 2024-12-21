<template>
    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
            <router-link class="nav-link" to="/products">Products</router-link>
        </li>
        <li class="nav-item">
            <router-link class="nav-link" to="/impressum">Imprint</router-link>
        </li>
        <li class="nav-item">
            <router-link class="nav-link" to="/help">Help</router-link>
        </li>
    </ul>
</template>

<script>
export default {
    name: "MainNavigation",
};
</script>

<style scoped>
.nav-item {
    margin-right: 10px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.nav-link {
    color: black;
    font-size: 16px;
    text-decoration: none;
}

.nav-link:focus,
.nav-link:active {
    color: black;
}
</style>