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
                        <InputField type="text" id="salutation" label="Salutation" v-model="localUser.salutation" />
                        <DropdownField id="role" label="Role" placeholder="Select a role" :options="roleOptions"
                            v-model="localUser.role" required />
                    </div>
                    <div class="col">
                        <InputField type="text" id="firstname" label="First Name" v-model="localUser.firstname" />
                        <InputField type="text" id="lastname" label="Last Name" v-model="localUser.lastname" />
                        <InputField type="text" id="address" label="Address" v-model="localUser.address" />
                        <InputField type="text" id="city" label="City" v-model="localUser.city" />
                        <InputField type="text" id="plz" label="PLZ" v-model="localUser.plz" />
                    </div>
                </div>
                <div class="modal-footer">
                    <ButtonField type="submit" class="btn-save">Save</ButtonField>
                    <ButtonField @click="$emit('close')" class="btn-cancel">Cancel</ButtonField>
                </div>
            </form>
        </div>
    </div>
</template>

<script>
import InputField from "@/components/atoms/InputField.vue";
import ButtonField from "@/components/atoms/ButtonField.vue";
import DropdownField from "@/components/atoms/DropdownField.vue";

export default {
    name: "UserEditModal",
    components: { InputField, ButtonField, DropdownField },
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

.modal-footer {
    display: flex;
    justify-content: space-between;
    margin-top: 20px;
}

.btn-save {
  background-color: #28a745;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
}

.btn-save:hover {
  background-color: #218838;
}

.btn-cancel {
  background-color: #6c757d;
  color: white;
  padding: 10px 20px;
  border-radius: 5px;
  border: none;
  cursor: pointer;
}

.btn-cancel:hover {
  background-color: #5a6268;
}
</style>
