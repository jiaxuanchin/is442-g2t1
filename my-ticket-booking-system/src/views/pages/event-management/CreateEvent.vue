<script setup>
const firstName = ref('')
const lastName = ref('')
const city = ref('')
const country = ref('')
const company = ref('')
const email = ref('')
const checkbox = ref(false)
</script>

<template>
  <VForm @submit.prevent="() => {}">
    <VRow>
      <!-- 👉 Event Name -->
      <VCol
        cols="12"
        md="6"
      >
        <VTextField
          v-model="eventName"
          label="Event Name"
          placeholder="Hamilton"
        />
      </VCol>

      <!-- 👉 Event Date -->
      <VCol
        cols="12"
        md="6"
      >
        <VTextField
          v-model="eventDate"
          label="Event Date"
          placeholder="01/01/2024"
        />
      </VCol>

      <!-- 👉 Ticket Pricing  -->
      <VCol
        cols="12"
        md="6"
      >
        <VTextField
          v-model="ticketPrice"
          label="Ticket Pricing"
          placeholder="$50"
        />
      </VCol>

      <!-- 👉 City -->
      <VCol
        cols="12"
        md="6"
      >
        <VTextField
          v-model="city"
          label="City"
          placeholder="New York"
        />
      </VCol>

      <!-- 👉 Country -->
      <VCol
        cols="12"
        md="6"
      >
        <VTextField
          v-model="country"
          label="Country"
          placeholder="United States"
        />
      </VCol>


      <VCol
        cols="12"
        class="d-flex gap-4"
      >
        <VBtn type="submit">
          Submit
        </VBtn>

        <VBtn
          type="reset"
          color="secondary"
          variant="tonal"
        >
          Reset
        </VBtn>
      </VCol>
    </VRow>
  </VForm>
</template>
