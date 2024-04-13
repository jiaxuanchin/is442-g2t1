<script setup>
import { ref } from "vue";
import axios from "axios";

const BASE_URL = 'http://localhost:8080/event'; 

const eventTitle = ref('')
const eventDate = ref('')
const eventDesc = ref('')  
const eventLoc = ref('')
const startTime = ref('')
const endTime = ref('')
const filled = ref('')
const capacity = ref('')
const ticketPrice = ref('')
const cancelFee = ref('')


const createEvent = async () => {
  // Check if all fields are filled
  if (!eventTitle.value || !eventDate.value ||  !eventDesc.value || !eventLoc.value || !startTime.value || !endTime.value || !filled.value || !capacity.value || !ticketPrice.value || !cancelFee.value) {
    alert('Please fill in all the fields');
    return;
  }

  // Prepare the data to send
  const eventData = {
    eventTitle: eventTitle.value,
    eventDate: eventDate.value,
    eventDesc: eventDesc.value,
    eventLoc: eventLoc.value,
    startTime: startTime.value,
    endTime: endTime.value,
    filled: filled.value,
    capacity: capacity.value,
    cancelFee: cancelFee.value,
    ticketPrice: ticketPrice.value,

  };

  try {
    // Send a POST request to your API endpoint
    const response = await fetch(`${BASE_URL}/createEvent`,
    {
      method: 'POST',
      headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
          'Content-Type': 'application/json',
        },
      body: JSON.stringify(eventData),
      }).then(response => response.json());

    // Handle success
    console.log(response);
    alert('Event created successfully!');
    // Reset form after successful creation
    resetForm();
  } catch (error) {
    // Handle error
    console.error(error);
    alert("An error occurred while creating the event.");
  }
};

const resetForm = () => {
  eventTitle.value = '';
  eventDate.value = '';
  eventDesc.value = '';
  eventLoc.value = '';
  startTime.value = '';
  endTime.value = '';
  filled.value = '';
  capacity.value = '';
  ticketPrice.value = '';
  cancelFee.value = '';

}
</script>

<template>
  <VContainer>
    <VCard class="pa-4" elevation="2">
      <VCardTitle class="text-h5 mb-6">Create A New Event</VCardTitle>
      <VForm @submit.prevent="createEvent">
        <VRow>
          <!-- event title -->
          <VCol cols="12" md="6">
            <VTextField v-model="eventTitle" label="Event Title" placeholder="Enter the event title" outlined dense />
          </VCol>
          <!-- event date -->
          <VCol cols="12" md="6">
            <VTextField v-model="eventDate" label="Event Date" placeholder="Select date" type="date" outlined dense />
          </VCol>
          <!-- event description -->
          <VCol cols="12" md="6">
            <VTextField
              v-model="eventDesc"  
              label="Event Description" 
              placeholder="Enter event description" 
              dense
            />
          </VCol>
          <!-- event location -->
          <VCol cols="12" md="6">
            <VTextField v-model="eventLoc" label="Event Location" placeholder="Enter Event Location" outlined dense />
          </VCol>
          <!-- start time -->
          <VCol cols="12" md="6">
            <VTextField v-model="startTime" label="Event Start Time" placeholder="Select Start Time" type="time" outlined dense />
          </VCol>
          <!-- end time -->
          <VCol cols="12" md="6">
            <VTextField v-model="endTime" label="Event End Time" placeholder="Select End Time" type="time" outlined dense />
          </VCol>
          
          <!-- filled -->
          <VCol cols="12" md="6">
            <VTextField v-model="filled" label="Filled" placeholder="Enter filled"  outlined
              dense type="number" />
          </VCol>
          <!-- capacity -->
          <VCol cols="12" md="6">
            <VTextField v-model="capacity" label="Capacity" placeholder="Enter capacity"  outlined
              dense type="number" />
          </VCol>
          <!-- ticket price -->
          <VCol cols="12" md="6">
            <VTextField v-model="ticketPrice" label="Ticket Pricing" placeholder="Enter pricing" prefix="$" outlined
              dense type="number" />
          </VCol>
          <!-- cancel fee -->
          <VCol cols="12" md="6">
            <VTextField v-model="cancelFee" label="Cancel Fee" placeholder="Enter cancellation fee" prefix="$" outlined
              dense type="number" />
          </VCol>

          <VCol cols="12" class="d-flex justify-space-between">
            <VBtn color="primary" class="mr-4" @click="createEvent"
              >Submit</VBtn
            >
            <VBtn color="error" text @click="resetForm">Reset</VBtn>
          </VCol>
        </VRow>
      </VForm>
    </VCard>
  </VContainer>
</template>
