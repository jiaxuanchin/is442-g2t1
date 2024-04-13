<template>
  <div class="verify-ticket">
    <h3>Verify Ticket</h3>
    <form @submit.prevent="verifyTicket">
      <input
        type="text"
        v-model="ticketId"
        placeholder="Enter Ticket ID"
        required
      />
      <button type="submit">Verify</button>
    </form>
    <div v-if="verificationResult !== null">
      <p>
        Verification Status: <strong>{{ verificationResult }}</strong>
      </p>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      ticketId: "",
      verificationResult: null,
    };
  },
  methods: {
    async verifyTicket() {
      try {
        const ticketId = this.ticketId.replace(/^0+/, '');
        const response = await axios.get('http://localhost:8080/ticket/' + ticketId,
          {
            headers: {
              'Authorization': `Bearer ${localStorage.getItem('token')}`
          }
        });
      
        if (response.data.attendance === true) {
          this.verificationResult = 'Ticket has been used.';
        } else if (response.data.attendance === false) {
          console.log('Ticket ID:', ticketId);
          console.log(localStorage.getItem('token'))

          const update = await fetch('http://localhost:8080/ticket/update/attendance/' + ticketId, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
            method: "PUT",
          })

          if (!update) {
            this.verificationResult = 'Error updating attendance status.';
          } else {
            const message = `Ticket is in booking ID ${response.data.bookingId} and attendance has been successfully marked.`;
            this.verificationResult = message;
          }
        }
      } catch (error) {
        console.error('Error verifying ticket:', error);
        if (error.response.data === 'Ticket not found') {
          this.verificationResult = 'Ticket ID does not exist.';
        } else {
          this.verificationResult = 'Error during verification process.';
        }
      }
    },
  },
};
</script>

<!-- Add this to the style section of each form component -->
<style scoped>
.verify-ticket,
.onsite-sales {
  background-color: #fff;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
}

h3 {
  margin-top: 0;
  color: #333;
}

form {
  display: flex;
  flex-direction: column;
}

input[type="text"],
input[type="number"] {
  padding: 10px;
  margin: 10px 0;
  border: 1px solid #ccc;
  border-radius: 4px;
}

button {
  padding: 10px;
  background-color: #4caf50;
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
