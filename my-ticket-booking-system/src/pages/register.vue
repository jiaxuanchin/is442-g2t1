<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
// import AuthProvider from '@/views/pages/authentication/AuthProvider.vue'
import logo from '@images/logo.svg?raw'

const router = useRouter()

const form = ref({
  fname: '',
  lname: '',
  email: '',
  password: '',
})

const isPasswordVisible = ref(false)
const errorMessage = ref('')
const successMessage = ref('')

// send to signup api
const handleSignUp = async () => {
  errorMessage.value = ''
  successMessage.value = ''

  try {
    await axios.post('http://localhost:8080/api/auth/signup', {
      user_fname: form.value.fname,
      user_lname: form.value.lname,
      email: form.value.email,
      password: form.value.password,
    })
    
    successMessage.value = 'Signup successful. Redirecting to login...'
    
    // Redirect to login after a short delay
    setTimeout(() => {
      router.push('/login')
    }, 3000)
  } catch (error) {
    if (error.response && error.response.data) {
      // Handle errors returned from your backend
      errorMessage.value = error.response.data.message || 'An error occurred during signup.'
    } else {
      errorMessage.value = 'An error occurred. Please try again.'
    }
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
          Ticket Booking System
        </VCardTitle>
      </VCardItem>

      <VCardText class="pt-2">
        <h5 class="text-h5 mb-1">
          Concert adventure starts here 🚀
        </h5>
      </VCardText>

      <VCardText>
        <VForm @submit.prevent="handleSignUp">
          <VRow>
            <!-- Firstname -->
            <VCol cols="12">
              <VTextField
                v-model="form.fname"
                autofocus
                label="First name"
              />
            </VCol>
            <!-- Firstname -->
            <VCol cols="12">
              <VTextField
                v-model="form.lname"
                autofocus
                label="Last name"
              />
            </VCol>
            <!-- email -->
            <VCol cols="12">
              <VTextField
                v-model="form.email"
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
              <div class="d-flex align-center mt-1 mb-4">
                <!-- <VCheckbox
                  id="privacy-policy"
                  v-model="form.privacyPolicies"
                  inline
                />
                <VLabel
                  for="privacy-policy"
                  style="opacity: 1;"
                >
                  <span class="me-1">I agree to</span>
                  <a
                    href="javascript:void(0)"
                    class="text-primary"
                  >privacy policy & terms</a>
                </VLabel> -->
              </div>

              <VBtn
                block
                type="submit"
              >
                Sign up
              </VBtn>
            </VCol>

            <!-- login instead -->
            <VCol
              cols="12"
              class="text-center text-base"
            >
              <span>Already have an account?</span>
              <RouterLink
                class="text-primary ms-2"
                to="/login"
              >
                Sign in instead
              </RouterLink>
            </VCol>
          </VRow>
          <!-- Error message -->
          <VAlert type="error" v-if="errorMessage">{{ errorMessage }}</VAlert>

          <!-- Success message -->
          <VAlert type="success" v-if="successMessage">{{ successMessage }}</VAlert>
        </VForm>
      </VCardText>
    </VCard>
  </div>
</template>

<style lang="scss">
@use "@core/scss/template/pages/page-auth.scss";
</style>
