<script setup>
import { useTheme } from 'vuetify'
import illustrationJohnDark from '@images/cards/illustration-john-dark.png'
import illustrationJohnLight from '@images/cards/illustration-john-light.png'

const { global } = useTheme()
const illustrationJohn = computed(() => global.name.value === 'dark' ? illustrationJohnDark : illustrationJohnLight)
</script>

<template>
  <VCard class="text-center text-sm-start">
    <VRow no-gutters>
      <VCol

      >
        <VCardItem>
          <VCardTitle class="text-md-h5 text-primary">
            Event Sales Statistics 🎉
          </VCardTitle>
        </VCardItem>

        <VCardText>
          <span>
            Event Name: The Era Tour
            <br>
            Event ID: 1
            <br>
            Event Description: The Era Tour is a musical concert that will be held in the city center.
            <br>
            Event tickets price: Sold out
            <br>
            Event Date: 2023-03-03

          </span>
          <br>
          <VBtn
            variant="tonal"
            class="mt-4"
            size="small"
          >
            View Event
          </VBtn>
        </VCardText>
      </VCol>

      <VCol

      >
        <img
          :src="illustrationJohn"
          :height="$vuetify.display.xs ? '150' : '175'"
          :class="$vuetify.display.xs ? 'mt-6 mb-n2' : 'position-absolute'"
          class="john-illustration flip-in-rtl"
        >
      </VCol>
    </VRow>
  </VCard>
</template>

<style lang="scss" scoped>
.john-illustration {
  inset-block-end: -0.0625rem;
  inset-inline-end: 3rem;
}
</style>
