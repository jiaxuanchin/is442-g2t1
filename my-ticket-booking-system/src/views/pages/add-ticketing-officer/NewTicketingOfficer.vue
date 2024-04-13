<script setup>
import { ref } from 'vue';

const firstName = ref('');
const lastName = ref('');
const email = ref('');
const password = ref('');

async function handleSubmit() {
  const params = new URLSearchParams();
  params.append('user_fname', firstName.value);
  params.append('user_lname', lastName.value);
  params.append('email', email.value);
  params.append('password', password.value);

  try {
    const response = await fetch(`http://localhost:8080/UserEntity/createTicketingOfficer`, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ 
        user_fname: firstName.value,
        user_lname: lastName.value,
        email: email.value,
        password: password.value
      }),
      }
    );

    // const response = await fetch(`http://localhost:8080/UserEntity/createTicketingOfficer/${params.toString()}`, {
    //   method: 'POST',
    //   headers: {
    //     'Authorization': `Bearer ${localStorage.getItem('token')}`,
    //   },
    // });

    if (response.ok) {
      alert('Ticketing officer created successfully!');
      handleReset();
    } else {
      const errorData = await response.json();
      alert('Error: ' + errorData.message);
    }
  } catch (error) {
    alert('Error: ' + error.message);
  }
}

function handleReset() {
  firstName.value = '';
  lastName.value = '';
  email.value = '';
  password.value = '';
}

</script>

<template>
  <VForm @submit.prevent="handleSubmit">
    <VRow>
      <!-- 👉 First Name -->
      <VCol
        cols="12"
        md="6"
      >
        <VTextField
          v-model="firstName"
          label="First Name"
          placeholder="John"
        />
      </VCol>

      <!-- 👉 Last Name -->
      <VCol
        cols="12"
        md="6"
      >
        <VTextField
          v-model="lastName"
          label="Last Name"
          placeholder="Doe"
        />
      </VCol>

      <!-- 👉 Email -->
      <VCol
        cols="12"
        md="6"
      >
        <VTextField
          v-model="email"
          label="Email"
          placeholder="johndoe@email.com"
        />
      </VCol>

      <!-- 👉 Password -->
      <VCol
        cols="12"
        md="6"
      >
        <VTextField
          v-model="password"
          label="Password"
          placeholder=".........."
        />
      </VCol>


    <VCol cols="12" class="d-flex gap-4">
      <VBtn type="submit">Submit</VBtn>
      <VBtn type="button" @click="handleReset">Reset</VBtn>
    </VCol>
    </VRow>
  </VForm>
</template>
