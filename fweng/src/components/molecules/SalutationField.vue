<template>
    <div>
        <div class="row mb-2">
            <div class="col-md-12">
                <DropdownField :id="id" :label="label" :placeholder="placeholder" :options="options"
                    :modelValue="localSalutation" @update:modelValue="handleSalutationChange" />
            </div>
        </div>

        <div v-if="localSalutation === 'Other'" class="row mb-2">
            <div class="col-md-12">
                <InputField type="text" id="otherInputId" label="Please specify" placeholder="Specify your title"
                    :modelValue="localOtherSalutation" @update:modelValue="$emit('update:otherSalutation', $event)" />
            </div>
        </div>
    </div>
</template>

<script>
import DropdownField from "@/components/atoms/DropdownField.vue";
import InputField from "@/components/atoms/InputField.vue";

export default {
    name: "SalutationField",
    components: { DropdownField, InputField },
    props: {
        id: { type: String, required: true },
        otherInputId: { type: String, required: true },
        label: { type: String, required: true },
        placeholder: { type: String, required: true },
        options: { type: Array, required: true },
        salutation: { type: String, default: "" },
        otherSalutation: { type: String, default: "" },
    },
    data() {
        return {
            localSalutation: this.salutation,
            localOtherSalutation: this.otherSalutation,
        };
    },
    methods: {
        handleSalutationChange(value) {
            this.localSalutation = value;
            this.$emit("update:salutation", value);

            if (value !== "Other") {
                this.localOtherSalutation = "";
                this.$emit("update:otherSalutation", "");
            }
        },
    },
};
</script>