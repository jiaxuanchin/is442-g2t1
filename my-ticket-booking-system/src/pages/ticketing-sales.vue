<template>
    <div class="onsite-sales">
      <h3>On-Site Ticket Sales</h3>
      <form @submit.prevent="processSale">
        <input type="number" v-model.number="eventId" placeholder="Event ID" required>
        <input type="number" v-model.number="numOfTickets" placeholder="Number of Tickets" required>
        <input type="number" v-model.number="customerId" placeholder="Customer ID" required>
        <button type="submit">Process Sale</button>
      </form>
      <div v-if="saleResult !== null">
        <p>Sale Status: <strong>{{ saleResult }}</strong></p>
      </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        eventId: null,
        numOfTickets: null,
        customerId: null,
        saleResult: null,
      };
    },
    methods: {
      async processSale() {
        try {
          const response = await axios.post('http://your-backend-api/process-sale', {
            eventId: this.eventId,
            numOfTickets: this.numOfTickets,
            customerId: this.customerId
          });
          this.saleResult = 'Sale processed successfully'; // Customize based on your API response
        } catch (error) {
          console.error('Error processing on-site ticket sale:', error);
          this.saleResult = 'Error during the sale process';
        }
      },
    },
  };
  </script>

  <!-- Add this to the style section of each form component -->
<style scoped>
.verify-ticket, .onsite-sales {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
}

h3 {
  margin-top: 0;
  color: #333;
}

form {
  display: flex;
  flex-direction: column;
}

input[type="text"], input[type="number"] {
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.2s;
}

button:hover {
  background-color: #45a049;
}

.result {
  margin-top: 20px;
  padding: 15px;
  background-color: #e8f5e9;
  /* color: #388e3c; */
  border-radius: 4px;
  text-align: center;
}
</style>

  