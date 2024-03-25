<script setup>
import { ref } from 'vue'
import axios from 'axios'

const eventName = ref('')
const eventDate = ref('')
const ticketPrice = ref('')
const city = ref('')
const eventDescription = ref('')  // Changed from country

const createEvent = async () => {
  // Check if all fields are filled
  if (!eventName.value || !eventDate.value || !ticketPrice.value || !city.value || !eventDescription.value) {
    alert('Please fill in all the fields');
    return;
  }

  // Prepare the data to send
  const eventData = {
    name: eventName.value,
    date: eventDate.value,
    price: ticketPrice.value,
    city: city.value,
    description: eventDescription.value  // Changed from country
  };

  try {
    // Send a POST request to your API endpoint
    const response = await axios.post('YOUR_BACKEND_ENDPOINT/events/create', eventData);
    // Handle success
    console.log(response.data);
    alert('Event created successfully!');
    // Reset form after successful creation
    resetForm();
  } catch (error) {
    // Handle error
    console.error(error);
    alert('An error occurred while creating the event.');
  }
}

const resetForm = () => {
  eventName.value = '';
  eventDate.value = '';
  ticketPrice.value = '';
  city.value = '';
  eventDescription.value = '';  // Changed from country
}
</script>


<template>
  <VContainer>
    <VCard class="pa-4" elevation="2">
      <VCardTitle class="text-h5 mb-6">Create A New Event</VCardTitle>
      <VForm @submit.prevent="createEvent">
        <VRow>
          <VCol cols="12" md="6">
            <VTextField v-model="eventName" label="Event Name" placeholder="Enter the event name" outlined dense />
          </VCol>
          <VCol cols="12" md="6">
            <VTextField v-model="eventDate" label="Event Date" placeholder="Select date" type="date" outlined dense />
          </VCol>
          <VCol cols="12" md="6">
            <VTextField v-model="ticketPrice" label="Ticket Pricing" placeholder="Enter pricing" prefix="$" outlined
              dense type="number" />
          </VCol>
          <VCol cols="12" md="6">
            <VTextField v-model="city" label="City" placeholder="Enter city" outlined dense />
          </VCol>
          <VCol cols="12" md="6">
            <VTextField
              v-model="eventDescription"  
              label="Event Description" 
              placeholder="Enter event description" 
              dense
            />
          </VCol>
          <VCol cols="12" class="d-flex justify-space-between">
            <VBtn color="primary" class="mr-4" @click="createEvent">Submit</VBtn>
            <VBtn color="error" text @click="resetForm">Reset</VBtn>
          </VCol>
        </VRow>
      </VForm>
    </VCard>
  </VContainer>
</template>
