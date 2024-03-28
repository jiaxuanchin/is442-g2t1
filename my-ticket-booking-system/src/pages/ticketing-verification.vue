<template>
  <div class="verify-ticket">
    <h3>Verify Ticket</h3>
    <form @submit.prevent="verifyTicket">
      <input type="text" v-model="ticketIdInput" placeholder="Enter Ticket ID" required>
      <button type="submit">Verify</button>
    </form>
    <div v-if="verificationResult !== null">
      <p>Verification Status: <strong>{{ verificationResult }}</strong></p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      ticketId: '',
      verificationResult: null,
    };
  },
  methods: {
    async verifyTicket() {
        try {
            const ticketId = this.ticketIdInput.replace(/^0+/, '');
            const response = await axios.get(`http://localhost:8080/ticket/${ticketId}`);
            // Handle the response
            console.log(response.data);
            this.verificationResult = response.data.status; // Assuming the API returns { status: 'Valid' or 'Invalid' }
        } catch (error) {
            console.error('Error verifying ticket:', error);
            this.verificationResult = 'Error during verification process';
        }
    }
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
  color: #388e3c;
  border-radius: 4px;
  text-align: center;
}
</style>

