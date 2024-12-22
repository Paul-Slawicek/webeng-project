<template>
    <form @submit.prevent="onSubmit">
        <InputGroup v-for="field in fields" :key="field.id" :label="field.label" :id="field.id" :type="field.type"
            v-model="localForm[field.id]" :placeholder="field.placeholder" />
        <!-- Aktuelles Passwort -->
        <div class="col-sm-9 mb-2">
            <input type="password" id="current-password" v-model="localCurrentPassword" class="form-control" required />
        </div>
        <!-- Bild-Upload -->
        <div class="form-group mb-4">
            <label for="file" class="form-label">Profilbild hochladen</label>
            <input type="file" id="file" @change="onImageUpload" class="form-control" accept="image/*" />
        </div>
        <!-- Bild-Vorschau -->
        <div v-if="previewImage" class="text-center mb-3">
            <img :src="previewImage" alt="Profilbild Vorschau" class="img-thumbnail" width="150" />
        </div>
        <!-- Submit-Button -->
        <div class="text-center">
            <Button type="submit">Profil aktualisieren</Button>
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
            localCurrentPassword: this.currentPassword, // Lokale Kopie initialisieren
        };
    },
    watch: {
        form: {
            immediate: true,
            handler(newForm) {
                this.localForm = { ...newForm }; // Form-Daten laden
            },
        },
        currentPassword: {
            immediate: true,
            handler(newPassword) {
                this.localCurrentPassword = newPassword; // Synchronisation bei Änderung der Prop
            },
        },
        localForm: {
            handler(newData) {
                console.log("localForm aktualisiert:", newData);
                if (JSON.stringify(newData) !== JSON.stringify(this.form)) {
                    this.$emit("update:form", newData);
                }
            },
            deep: true,
        },
        localCurrentPassword: {
            handler(newPassword) {
                this.$emit("update:currentPassword", newPassword); // Änderungen an den Parent senden
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
