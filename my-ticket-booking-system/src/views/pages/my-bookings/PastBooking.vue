<script setup>

import axios from 'axios';

const events = ref([
]);

const fetchUserBookings = async (userId) => {
  try {
    const response = await axios.get(`http://localhost:8080/booking/user/${userId}`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    const bookings = response.data;
    
    const updatedEvents = await Promise.all(bookings.map(async (booking) => {
      const eventResponse = await axios.get(`http://localhost:8080/event/searchById/${booking.eventId}`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });
      const event = eventResponse.data.data;
      
      // Combine event date and time
      const eventDateTime = new Date(`${event.eventDate} ${event.startTime}`);
      const currentTime = new Date();

      // Check if the event date and time are in the past
      if (eventDateTime <= currentTime) {
        const tickets = booking.numOfTickets; // Count the number of tickets booked for this event
        const bookingId = booking.bookingId;
        return { ...event, tickets,bookingId}; // Return the updated event object with the calculated number of tickets
      } else {
        return null; // Return null if the event is in the future
      }
    }));

    // Filter out null values (events in the future) and update the events
    updateEvents(updatedEvents.filter(event => event !== null));
  } catch (error) {
    console.error('Error fetching user bookings:', error);
  }
};


onMounted(() => {
  const userId = localStorage.getItem('user_id'); // Get the user ID from local storage
  fetchUserBookings(userId);
});

// Method to update the frontend with the updated event details
const updateEvents = (updatedEvents) => {
  events.value = updatedEvents.map(event => ({
    name: event.eventTitle,
    description: event.eventDesc,
    date: event.eventDate,
    location: event.eventLoc,
    time: `${event.startTime} - ${event.endTime}`,
    tickets: event.tickets, // Use the calculated tickets count
    id: event.eventId,
    bookingId: event.bookingId
  }));
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
              <VBtn class="mt-8" :to="'/booking-details/' + event.bookingId">
                More details
              </VBtn>
            </VCardText>
          </VCol>
        </VRow>
      </VCard>
    </VCol>
    <!-- !SECTION -->

  </VRow>
</template>
