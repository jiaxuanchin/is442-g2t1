<script setup>
const events = ref([
  {
    name: 'Event 1',
    description: 'Event 1 description',
    date: '2023-03-23',
    location: 'The Capitol',
    time: '18:00 - 21:00',
    tickets: 4,
    id: 1
  },
  {
    name: 'Event 2',
    description: 'Event 2 description',
    date: '2023-03-23',
    location: 'The Capitol',
    time: '18:00 - 21:00',
    tickets: 4,
    id: 1
  },
]);

const cancelEvent = (index) => {
  // To do: Need to also call the cancellation of booking here
  events.value.splice(index, 1);
};

</script>

<template>
  <VRow>
    <!-- 👉 Booking card -->
    <VCol
      v-for="(event, index) in events"
      :key="index"
      md="12"
      lg="12"
      cols="12"
    >
      <VCard>
        <VRow no-gutters>
          <VCol
            cols="12"
            sm="8"
            md="12"
            order="2"
            order-lg="1"
          >
            <VCardItem>
              <VCardTitle>{{ event.name }}</VCardTitle>
            </VCardItem>

            <VCardText>
                <p>Event Description: {{ event.description }}</p> 
                <p>Date: {{ event.date }}</p> 
                <p>Location: {{ event.location }}</p>
            </VCardText>

            <VCardText>
              <VDivider />
            </VCardText>

            <VCardText class="d-flex justify-center">
              <div class="me-auto pe-4">
                <p class="d-flex align-center mb-6">
                  <VIcon
                    color="primary"
                    icon="bx-lock-open"
                  />
                  <span class="ms-3">{{ event.time }}</span>
                </p>

                <p class="d-flex align-center mb-0">
                  <VIcon
                    color="primary"
                    icon="bx-user"
                  />
                  <span class="ms-3">{{ event.tickets }} Tickets</span>
                </p>
              </div>
              <!-- View more details button -->
              <VBtn class="mt-8" :to="'/booking-details/' + event.id">
                More details
              </VBtn>
              <!-- Cancellation button -->
              <VBtn class="mt-8 ms-2"  @click="cancelEvent(index)" color="error">
                Cancel Booking
              </VBtn>

            </VCardText>
          </VCol>
        </VRow>
      </VCard>
    </VCol>
    <!-- !SECTION -->

  </VRow>
</template>
