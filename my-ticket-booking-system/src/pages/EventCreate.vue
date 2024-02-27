<template>
    <div class="event-create">
      <h1>Create New Event</h1>
      <form @submit.prevent="createEvent">
        <div class="form-group">
          <label for="eventName">Event Name:</label>
          <input type="text" id="eventName" v-model="event.eventName" required>
        </div>
        
        <div class="form-group">
          <label for="venue">Venue:</label>
          <input type="text" id="venue" v-model="event.venue" required>
        </div>
  
        <div class="form-group">
          <label for="dateTime">Date and Time:</label>
          <input type="datetime-local" id="dateTime" v-model="event.dateTime" required>
        </div>
  
        <div class="form-group">
          <label for="ticketPrice">Ticket Price:</label>
          <input type="number" id="ticketPrice" v-model="event.ticketPrice" required min="0">
        </div>
  
        <div class="form-group">
          <label for="numTickets">Number of Tickets:</label>
          <input type="number" id="numTickets" v-model="event.numTicketsAvailable" required min="1">
        </div>
  
        <div class="form-group">
          <label for="cancellationFee">Cancellation Fee:</label>
          <input type="number" id="cancellationFee" v-model="event.cancellationFee" required min="0">
        </div>
  
        <button type="submit">Create Event</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        event: {
          eventName: '',
          venue: '',
          dateTime: '',
          ticketPrice: 0,
          numTicketsAvailable: 0,
          cancellationFee: 0
        }
      };
    },
    methods: {
      createEvent() {

        console.log('Event data to create:', this.event);
  
        // After the API call and event creation clear the form or redirect the user
        // this.event = { eventName: '', venue: '', dateTime: '', ticketPrice: 0, numTicketsAvailable: 0, cancellationFee: 0 };
        // this.$router.push('/events');
      }
    }
  };
  </script>
  
  <style scoped>

  .form-group {
    margin-bottom: 1rem;
  }
  label {
    display: block;
  }
  input[type="text"],
  input[type="datetime-local"],
  input[type="number"] {
    width: 100%;
    padding: 0.5rem;
    margin-top: 0.25rem;
  }
  button {
    padding: 0.5rem 1rem;
    background-color: #5cb85c;
    color: white;
    border: none;
    cursor: pointer;
  }
  button:hover {
    background-color: #4cae4c;
  }
  </style>
  