<template>
    <CardAtom @cardClick="navigateToProductDetails">
        <div class="product-overview-container">
            <ImageElement :src="imageUrl" :alt="product.title" className="card-img-top product-overview" />
        </div>
        <div class="card-body">
            <h5 class="card-title">{{ product.title }}</h5>
            <p class="card-text"><strong>Price:</strong> {{ product.price }} €</p>
            <p class="card-text"><strong>Category:</strong> {{ product.category }}</p>
        </div>
    </CardAtom>
</template>

<script>
import CardAtom from "@/components/atoms/CardElement.vue";
import ImageElement from "@/components/atoms/ImageElement.vue";

export default {
    name: "ProductCard",
    components: { CardAtom, ImageElement },
    props: {
        product: {
            type: Object,
            required: true,
        },
        imageUrl: {
            type: String,
            required: true,
        },
    },
    methods: {
        navigateToProductDetails() {
            this.$emit("navigate", this.product.id);
        },
    },
};
</script>