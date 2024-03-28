<script setup>

// Get the Id from the parameter
const { params } = useRoute()
const bookingId = params.bookingId

const tickets = ref([
  {
    name: 'Ticket 1',
    ticketId: 3
  },
]);

</script>

<template>
  <VRow>
    <VCol cols="12">
      <VCard title="Booking details">
        <VCardText class="d-flex flex-column gap-y-8">
          <div>
            <h6 class="text-h6">
              Id:
            </h6>
            <span>Booking id {{ bookingId }}</span>
            
            <br><br>
            <h6 class="text-h6">
              Event name:
            </h6>
            <span>Disney world</span>

            <br><br>
            <h6 class="text-h6">
              Event date:
            </h6>
            <span>2023-09-15 </span>

            <br><br>
            <h6 class="text-h6">
              Event description:
            </h6>
            <span>Disney is coming to town </span>

            <br><br>
            <h6 class="text-h6">
              Event time:
            </h6>
            <span>18:00 - 21:00 </span>

            <br><br>
            <h6 class="text-h6">
              Cancel fee:
            </h6>
            <span>$20</span>
          </div>
        </VCardText>
      </VCard>
    </VCol>

    <!-- 👉 Ticket cards -->
    <VCol
      v-for="(ticket, index) in tickets"
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
              <VCardTitle>{{ ticket.name }}</VCardTitle>
            </VCardItem>

            <VCardText>
                <p>Ticket Id: {{ ticket.ticketId }}</p>
            </VCardText>
            
          </VCol>
        </VRow>
      </VCard>
    </VCol>
  </VRow>
</template>
