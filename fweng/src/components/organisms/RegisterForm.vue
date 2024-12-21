<template>
    <form @submit.prevent="$emit('submitRegistration')">
      <DropdownField
        id="salutation"
        label="Title"
        placeholder="Select Title"
        :options="salutationOptions"
        :modelValue="salutation"
        @update:modelValue="$emit('update:salutation', $event)"
      />
  
      <InputField
        v-if="salutation === 'Other'"
        type="text"
        id="otherSalutation"
        label="Please specify"
        placeholder="Specify your title"
        :modelValue="otherSalutation"
        @update:modelValue="$emit('update:otherSalutation', $event)"
      />
  
      <RegistrationFormGroup
        :inputs="nameInputs"
        @update:firstname="$emit('update:firstname', $event)"
        @update:lastname="$emit('update:lastname', $event)"
      />
  
      <RegistrationFormGroup
        :inputs="addressInputs"
        @update:address="$emit('update:address', $event)"
      />
  
      <RegistrationFormGroup
        :inputs="locationInputs"
        :dropdownOptions="countries"
        @update:plz="$emit('update:plz', $event)"
        @update:city="$emit('update:city', $event)"
        @update:country="$emit('update:country', $event)"
      />
  
      <RegistrationFormGroup
        :inputs="accountInputs"
        @update:username="$emit('update:username', $event)"
        @update:email="$emit('update:email', $event)"
      />
  
      <RegistrationFormGroup
        :inputs="passwordInputs"
        @update:password="$emit('update:password', $event)"
        @update:password2="$emit('update:password2', $event)"
      />
  
      <div class="text-center">
        <ButtonField>Register</ButtonField>
      </div>
    </form>
  </template>
  
  <script>
  import DropdownField from "@/components/atoms/DropdownField.vue";
  import InputField from "@/components/atoms/InputField.vue";
  import ButtonField from "@/components/atoms/ButtonField.vue";
  import RegistrationFormGroup from "@/components/molecules/RegisterFormGroup.vue";
  
  export default {
    name: "RegisterForm",
    components: { DropdownField, InputField, ButtonField, RegistrationFormGroup },
    props: {
      countries: Array,
      salutation: String,
      otherSalutation: String,
      firstname: String,
      lastname: String,
      address: String,
      plz: String,
      city: String,
      country: String,
      email: String,
      username: String,
      password: String,
      password2: String,
    },
    emits: [
      "update:salutation",
      "update:otherSalutation",
      "update:firstname",
      "update:lastname",
      "update:address",
      "update:plz",
      "update:city",
      "update:country",
      "update:email",
      "update:username",
      "update:password",
      "update:password2",
      "submitRegistration",
    ],
    computed: {
      salutationOptions() {
        return [
          { value: "Herr", label: "Mr." },
          { value: "Frau", label: "Ms." },
          { value: "Other", label: "Other" },
        ];
      },
      nameInputs() {
        return [
          { type: "text", id: "firstname", label: "First Name", placeholder: "First Name", required: true },
          { type: "text", id: "lastname", label: "Last Name", placeholder: "Last Name", required: true },
        ];
      },
      addressInputs() {
        return [{ type: "text", id: "address", label: "Address", placeholder: "Address", required: true }];
      },
      locationInputs() {
        return [
          { type: "text", id: "plz", label: "Postal Code", placeholder: "Postal Code", required: true },
          { type: "text", id: "city", label: "City", placeholder: "City", required: true },
          { type: "select", id: "country", label: "Country", placeholder: "Select Country", required: true },
        ];
      },
      accountInputs() {
        return [
          { type: "text", id: "username", label: "Username", placeholder: "Username", required: true },
          { type: "email", id: "email", label: "Email Address", placeholder: "Email Address", required: true },
        ];
      },
      passwordInputs() {
        return [
          { type: "password", id: "password", label: "Password", placeholder: "Password", required: true },
          { type: "password", id: "password2", label: "Repeat Password", placeholder: "Repeat Password", required: true },
        ];
      },
    },
  };
  </script>
  