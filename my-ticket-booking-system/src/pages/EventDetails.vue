<template>
    <div>
      <h1>Event Details</h1>
      <div v-if="event">
        <h2>{{ event.eventName }}</h2>
        <p>Venue: {{ event.venue }}</p>
        <p>Date: {{ event.dateTime }}</p>
        <p>Tickets Available: {{ event.numTicketAvailable }}</p>
        <p>Price: ${{ event.eventTicketPrice }}</p>
        <!-- Other details -->
      </div>
      <div v-else>
        <p>The event details could not be found.</p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    props: {
      id: {
        type: String,
        required: true
      }
    },
    data() {
      return {
        event: null
      };
    },
    created() {
      this.fetchEventDetails();
    },
    methods: {
      async fetchEventDetails() {
        try {
          //  API call to fetch specific event details
          const response = await this.$http.get(`/events/${this.id}`);
          this.event = response.data;
        } catch (error) {
          console.error('There was an error fetching the event details:', error);
        }
      }
    }
  };
  </script>
  