<template>
    <div class="ticket-validation">
      <h1>Ticket Validation</h1>
      <form @submit.prevent="validateTicket">
        <div class="form-group">
          <label for="ticketId">Ticket ID:</label>
          <input type="text" id="ticketId" v-model="ticketId" required>
        </div>
        <button type="submit">Validate Ticket</button>
      </form>
      <div v-if="validationResult" class="validation-result">
        <h2>Validation Result:</h2>
        <p :class="{ valid: validationResult.isValid, invalid: !validationResult.isValid }">
          {{ validationResult.message }}
        </p>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        ticketId: '',
        validationResult: null,
      };
    },
    methods: {
      validateTicket() {
        // Replace with API call
        console.log('Validating ticket ID:', this.ticketId);

        // Mock validation result
        this.validationResult = {
          isValid: true, 
          message: 'The ticket is valid and the seat number is 24B.', // Example message
        };
  
        // Example of an actual API call using axios:
        // axios.post('/api/validate-ticket', { ticketId: this.ticketId })
        //   .then(response => {
        //     this.validationResult = {
        //       isValid: response.data.isValid,
        //       message: response.data.message,
        //     };
        //   })
        //   .catch(error => {
        //     console.error('Validation error:', error);
        //     this.validationResult = {
        //       isValid: false,
        //       message: 'Error during validation. Please try again.',
        //     };
        //   });
      },
    },
  };
  </script>
  
  <style scoped>
  .ticket-validation {
    max-width: 600px;
    margin: auto;
    text-align: center;
  }
  
  .form-group {
    margin-bottom: 1rem;
  }
  
  label {
    display: block;
  }
  
  input[type="text"] {
    width: 100%;
    padding: 0.5rem;
    margin-top: 0.25rem;
  }
  
  button {
    padding: 0.5rem 1rem;
    background-color: #4CAF50;
    color: white;
    border: none;
    cursor: pointer;
    margin-top: 0.5rem;
  }
  
  button:hover {
    background-color: #45a049;
  }
  
  .validation-result {
    margin-top: 1rem;
  }
  
  .valid {
    color: #28a745;
  }
  
  .invalid {
    color: #dc3545;
  }
  </style>
  