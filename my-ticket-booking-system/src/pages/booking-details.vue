<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import axios from 'axios';

// Get the Id from the parameter
const { params } = useRoute();
const bookingId = params.bookingId;

// Reactive variables
const event = ref({});
const tickets = ref([]);

// Router
const router = useRouter();

// Fetch booking details and event information
onMounted(async () => {
  try {
    // Fetch booking details
    const bookingResponse = await axios.get(`http://localhost:8080/booking/${bookingId}`,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    const bookingData = bookingResponse.data;
    const numTicketsBought = bookingData.tickets.length;
    
    // Fetch event information
    const eventResponse = await axios.get(`http://localhost:8080/event/searchById/${bookingData.eventId}`,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    const eventData = eventResponse.data.data;

    // Update reactive variables
    event.value = {
      eventTitle: eventData.eventTitle,
      eventDate: eventData.eventDate,
      eventDesc: eventData.eventDesc,
      startTime: eventData.startTime,
      endTime: eventData.endTime,
      cancelFee: eventData.cancelFee
    };

    tickets.value = Array.from({ length: numTicketsBought }, (_, index) => ({
      name: `Ticket ${index + 1}`,
      ticketId: bookingData.tickets[index]
    }));
  } catch (error) {
    console.error('Error fetching data:', error);
  }
});

</script>


<template>
  <VRow>
    <VCol cols="12">
      <VCard title="Booking details">
        <VCardText class="d-flex flex-column gap-y-8">
          <div>
            <h6 class="text-h6">Id:</h6>
            <span>Booking id {{ bookingId }}</span>
            
            <br><br>
            <h6 class="text-h6">Event name:</h6>
            <span>{{ event.eventTitle }}</span>

            <br><br>
            <h6 class="text-h6">Event date:</h6>
            <span>{{ event.eventDate }}</span>

            <br><br>
            <h6 class="text-h6">Event description:</h6>
            <span>{{ event.eventDesc }}</span>

            <br><br>
            <h6 class="text-h6">Event time:</h6>
            <span>{{ event.startTime }} - {{ event.endTime }}</span>

            <br><br>
            <h6 class="text-h6">Cancel fee:</h6>
            <span>${{ event.cancelFee }}</span>
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
                <p>Ticket Id: {{ ticket.ticketId.ticketId }}</p>
                <p>Ticket Attendance: {{ ticket.ticketId.attendance }}</p>
            </VCardText>
            
          </VCol>
        </VRow>
      </VCard>
    </VCol>
  </VRow>
</template>
