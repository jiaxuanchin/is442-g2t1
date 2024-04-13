<script setup>
import { useTheme } from 'vuetify'
import illustrationJohnDark from '@images/cards/illustration-john-dark.png'
import illustrationJohnLight from '@images/cards/illustration-john-light.png'
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

const BASE_URL = 'http://localhost:8080/event'; 
const { global } = useTheme()
const illustrationJohn = computed(() => global.name.value === 'dark' ? illustrationJohnDark : illustrationJohnLight)
const route = useRoute();
const router = useRouter();
const eventId = ref(route.params.eventId);
const eventDetails = ref({});

onMounted(async () => {
  if (!eventId.value) {
    console.error('No event ID provided');
    return;
  }
  
  try {
    console.log('Fetching event details');
    const response = await fetch(`${BASE_URL}/searchById/${eventId.value}`, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        }
      }).then(response => response.json());
    console.log(response.data);
    eventDetails.value = response.data;

  } catch (error) {
    console.error('Error fetching event details:', error);
  }
});

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
          <div class="mb-3"><b>Event Title:</b> {{ eventDetails.eventTitle }}</div>
            <div class="mb-3"><b>Event Date:</b> {{ eventDetails.eventDate }}</div>
            <div class="mb-3"><b>Event Description:</b> {{ eventDetails.eventDesc }}</div>
            <div class="mb-3"><b>Event Location:</b> {{ eventDetails.eventLoc }}</div>
            <div class="mb-3"><b>Start Time:</b> {{ eventDetails.startTime }}</div>
            <div class="mb-3"><b>End Time:</b> {{ eventDetails.endTime }}</div>
            <div class="mb-3"><b>Ticket Price:</b> $ {{ eventDetails.ticketPrice }}</div>
            <div class="mb-3"><b>Filled:</b> {{ eventDetails.filled }}</div>
            <div class="mb-3"><b>Capacity:</b> {{ eventDetails.capacity }}</div>
            <div class="mb-3"><b>Cancel Fee: </b> $ {{ eventDetails.cancelFee }}</div>
          <br>
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
