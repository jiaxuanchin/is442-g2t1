<script setup>
import { ref } from 'vue';
import axios from 'axios';

const events = ref([]);
let confirmationDialog = ref(false);
let cancelIndex = null;

const cancelEvent = async (index) => {
  try {
    const bookingId = events.value[index].bookingId;
    await axios.delete(`http://localhost:8080/booking/delete/${bookingId}`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    events.value.splice(index, 1);
    confirmationDialog.value = false;
    alert('Booking cancelled successfully!');
  } catch (error) {
    console.error('Error cancelling booking:', error);
    alert('Failed to cancel booking. Please try again later.');
  }
};

const showConfirmationDialog = (index) => {
  console.log('Showing confirmation dialog for index:', index);
  cancelIndex = index;
  confirmationDialog.value = true;
};

const fetchUserBookings = async () => {
  try {
    const userId = localStorage.getItem('user_id'); // Get the user ID from local storage
    const response = await axios.get(`http://localhost:8080/booking/user/${userId}`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    const bookings = response.data;

    const updatedEvents = await Promise.all(bookings.map(async (booking) => {
      // Get event details
      const eventResponse = await axios.get(`http://localhost:8080/event/searchById/${booking.eventId}`, {
        headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
      });

      // Return merged event details with numOfTickets
      return {
        ...eventResponse.data.data,
        tickets: booking.numOfTickets, // Use numOfTickets from the booking
        bookingId: booking.bookingId
      };
    }));

    // Filter events based on whether they have already happened
    const today = new Date();
    const pastEvents = updatedEvents.filter(event => {
      const eventStart = new Date(event.eventDate + ' ' + event.startTime);
      return eventStart >= today;
    });

    // Map the pastEvents array to match the structure of the events array
    const mappedEvents = pastEvents.map(event => ({
      name: event.eventTitle,
      description: event.eventDesc,
      date: event.eventDate,
      location: event.eventLoc,
      time: `${event.startTime} - ${event.endTime}`,
      tickets: event.tickets,
      id: event.eventId,
      bookingId: event.bookingId,
      cancelable: isEventCancelable(event)
    }));

    events.value = mappedEvents;
  } catch (error) {
    console.error('Error fetching user bookings:', error);
  }
};

const isEventCancelable = (event) => {
  if (!event.eventDate || !event.startTime) {
    return false; // or any other appropriate action
  }

  const eventDateParts = event.eventDate.split('-');
  const startTimeParts = event.startTime.split(':');

  const eventStart = new Date(
    parseInt(eventDateParts[0]),   // Year
    parseInt(eventDateParts[1]) - 1, // Month (Note: Months are zero-based in JavaScript)
    parseInt(eventDateParts[2]),   // Day
    parseInt(startTimeParts[0]),  // Hour
    parseInt(startTimeParts[1])    // Minute
  );

  const currentTime = new Date();
  const timeDiff = eventStart - currentTime;
  const hoursDiff = timeDiff / (1000 * 60 * 60);
  return hoursDiff > 48;
};

fetchUserBookings();

</script>

<template>
  <VRow>
    <!-- Booking card -->
    <div class="ms-5">
      <p>Note: For events happning before 24 hours, cancellation is not allowed.</p>
    </div>
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
              <VBtn class="mt-8" :to="'/booking-details/' + event.bookingId">
                More details
              </VBtn>

              <!-- Cancellation button -->
              <VBtn class="mt-8 ms-2" @click="showConfirmationDialog(index)" color="error" :disabled="!event.cancelable">
                Cancel Booking
              </VBtn>

              <!-- Confirmation dialog for this booking -->
              <VDialog v-if="confirmationDialog && cancelIndex === index" v-model="confirmationDialog" max-width="500">
                <VCard>
                  <VCardText>
                    <div>Are you sure you want to cancel the booking for "{{ event.name }}"?</div>
                  </VCardText>
                  <VCardActions>
                    <VBtn color="error" @click="cancelEvent(index)">Yes, Cancel Booking</VBtn>
                    <VBtn @click="confirmationDialog = false">No, Keep Booking</VBtn>
                  </VCardActions>
                </VCard>
              </VDialog>

            </VCardText>
          </VCol>
        </VRow>
      </VCard>
    </VCol>
    <!-- End of Booking card -->
  </VRow>
</template>
