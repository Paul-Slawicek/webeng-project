<template>
    <div class="modal-backdrop">
        <div class="modal-content">
            <h3 class="modal-title">Edit User</h3>
            <form @submit.prevent="handleSave">
                <div class="row">
                    <div class="col">
                        <InputField type="text" id="username" label="Username" v-model="localUser.username" required />
                        <InputField type="password" id="password" label="Password" v-model="localUser.newPassword"
                            placeholder="Enter new password or leave blank" />
                        <InputField type="email" id="email" label="Email" v-model="localUser.email" required />
                    </div>
                    <div class="col">
                        <InputField type="text" id="salutation" label="Salutation" v-model="localUser.salutation" />
                        <InputField type="text" id="firstname" label="First Name" v-model="localUser.firstname" />
                        <InputField type="text" id="lastname" label="Last Name" v-model="localUser.lastname" />
                    </div>
                    <div class="col">
                        <InputField type="text" id="address" label="Address" v-model="localUser.address" />
                        <InputField type="text" id="city" label="City" v-model="localUser.city" />
                        <InputField type="text" id="plz" label="PLZ" v-model="localUser.plz" />
                    </div>
                </div>
                <div class="modal-footer">
                    <ButtonField type="submit" class="btn-success btn-lg">Save</ButtonField>
                    <ButtonField @click="$emit('close')" class="btn-secondary btn-lg">Cancel</ButtonField>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import InputField from "@/components/atoms/InputField.vue";
import ButtonField from "@/components/atoms/ButtonField.vue";

export default {
    name: "UserEditModal",
    components: { InputField, ButtonField },
    props: {
        user: {
            type: Object,
            required: true,
        },
    },
    emits: ["save", "close"],
    data() {
        return {
            localUser: { ...this.user },
            roleOptions: [
                { value: "user", label: "User" },
                { value: "admin", label: "Admin" },
            ],
        };
    },
    methods: {
        handleSave() {
            this.$emit("save", this.localUser);
        },
    },
};
</script>

<style scoped>
.modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
}

.modal-content {
    background: #ffffff;
    padding: 20px;
    border-radius: 10px;
    width: 600px;
    box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}

.modal-title {
    font-size: 24px;
    font-weight: bold;
    text-align: center;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 15px;
}

.modal-label {
    font-weight: bold;
    margin-bottom: 5px;
    display: block;
}


.form-group input:focus,
.form-group textarea:focus {
    border-color: #007bff;
    outline: none;
}

.modal-footer {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}
</style>