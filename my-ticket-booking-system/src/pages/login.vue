<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios'
// import AuthProvider from '@/views/pages/authentication/AuthProvider.vue'
import logo from '@images/logo.svg?raw'

const router = useRouter()
const route = useRoute()

const form = ref({
  email: '',
  password: '',
})

const isPasswordVisible = ref(false)
const errorMessage = ref('') // To display error message

onMounted(() => {
  // Check if there's a message in the query params and set it as the error message
  if (route.query.message) {
    errorMessage.value = route.query.message
  }
})

// send to signin api
const handleSubmit = async () => {
  try {
    const response = await axios.post('http://localhost:8080/api/auth/signin', {
      email: form.value.email,
      password: form.value.password,
    })
    
    if (response.status === 200) {
      localStorage.setItem('token', response.data.accessToken)
      localStorage.setItem('user_id', response.data.id)
      router.push('/home') // Redirect on successful login
    } else {
      errorMessage.value = 'Login failed. Please check your credentials.'
    }

  } catch (error) {
    errorMessage.value = 'Login failed. Please try again.'

  }
}

</script>

<template>
  <div class="auth-wrapper d-flex align-center justify-center pa-4">
    <VCard
      class="auth-card pa-4 pt-7"
      max-width="448"
    >
      <VCardItem class="justify-center">
        <template #prepend>
          <div class="d-flex">
            <div
              class="d-flex text-primary"
              v-html="logo"
            />
          </div>
        </template>

        <VCardTitle class="text-2xl font-weight-bold">
          G2T1's Ticket Booking System
        </VCardTitle>
      </VCardItem>

      <VCardText class="pt-2">
        <h5 class="text-h5 mb-1">
          Welcome! 👋🏻
        </h5>
        <p class="mb-0">
          Please sign-in to your account to start booking.
        </p>
      </VCardText>

      <VCardText>
        <VForm @submit.prevent="handleSubmit">
          <VRow>
            <!-- email -->
            <VCol cols="12">
              <VTextField
                v-model="form.email"
                autofocus
                label="Email"
                type="email"
              />
            </VCol>

            <!-- password -->
            <VCol cols="12">
              <VTextField
                v-model="form.password"
                label="Password"
                :type="isPasswordVisible ? 'text' : 'password'"
                :append-inner-icon="isPasswordVisible ? 'bx-hide' : 'bx-show'"
                @click:append-inner="isPasswordVisible = !isPasswordVisible"
              />

              <div class="d-flex align-center justify-space-between flex-wrap mt-1 mb-4">
              </div>

              <!-- login button -->
              <VBtn
                block
                type="submit"
              >
                Login
              </VBtn>
            </VCol>

            <!-- create account -->
            <VCol
              cols="12"
              class="text-center text-base"
            >
              <span>Don't have an account?</span>
              <RouterLink
                class="text-primary ms-2"
                to="/register"
              >
                Create an account
              </RouterLink>
            </VCol>

            <VCardText v-if="errorMessage">
              <VAlert type="error">{{ errorMessage }}</VAlert>
            </VCardText>

            <!-- <VCol
              cols="12"
              class="d-flex align-center"
            >
              <VDivider />
              <span class="mx-4">or</span>
              <VDivider />
            </VCol> -->

            <!-- auth providers -->
            <!-- <VCol
              cols="12"
              class="text-center"
            >
              <AuthProvider />
            </VCol> -->
          </VRow>
        </VForm>
      </VCardText>
    </VCard>
  </div>
</template>

<style lang="scss">
@use "@core/scss/template/pages/page-auth.scss";
</style>
