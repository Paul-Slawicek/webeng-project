<template>
    <form @submit.prevent="onSubmit">
        <InputGroup v-for="field in fields" :key="field.id" :label="field.label" :id="field.id" :type="field.type"
            v-model="localForm[field.id]" :placeholder="field.placeholder" />

        <div class="form-floating mb-3">
            <input type="password" id="current-password" v-model="localCurrentPassword" class="form-control"
                placeholder="Enter Current Password" required />
            <label for="current-password">Current Password</label>
        </div>

        <div class="form-group mb-4">
            <label for="file" class="form-label">Upload Profile Picture</label>
            <input type="file" id="file" @change="onImageUpload" class="form-control" accept="image/*" />
        </div>

        <div v-if="previewImage" class="text-center mb-3">
            <img :src="previewImage" alt="Profilbild Vorschau" class="img-thumbnail" width="150" />
        </div>

        <div class="text-center">
            <Button type="submit">Update Profile</Button>
        </div>
    </form>
</template>

<script>
import InputGroup from "@/components/molecules/InputGroup.vue";
import Button from "@/components/atoms/ButtonField.vue";

export default {
    props: {
        fields: { type: Array, required: true },
        form: { type: Object, required: true },
        currentPassword: { type: String, required: true },
        previewImage: String,
    },
    emits: ["submit", "update:form", "update:currentPassword", "imageUpload"],
    components: { InputGroup, Button },
    data() {
        return {
            localForm: { ...this.form },
            localCurrentPassword: this.currentPassword,
        };
    },
    watch: {
        form: {
            immediate: true,
            handler(newForm) {
                this.localForm = { ...newForm };
            },
        },
        currentPassword: {
            immediate: true,
            handler(newPassword) {
                this.localCurrentPassword = newPassword;
            },
        },
        localForm: {
            handler(newData) {
                if (JSON.stringify(newData) !== JSON.stringify(this.form)) {
                    this.$emit("update:form", newData);
                }
            },
            deep: true,
        },
        localCurrentPassword: {
            handler(newPassword) {
                this.$emit("update:currentPassword", newPassword);
            },
        },
    },
    methods: {
        onSubmit() {
            this.$emit("submit");
        },
        onImageUpload(event) {
            this.$emit("imageUpload", event);
        },
    },
};
</script>