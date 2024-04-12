<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';
import { constructFrom } from 'date-fns';

const BASE_URL = 'http://localhost:8080/event'; 
const route = useRoute();
const router = useRouter();
const eventId = ref(route.params.eventId);
const eventDetails = ref({});

// Tracks if we are in editing mode
const editing = ref(false);  


onMounted(async () => {
  if (!eventId.value) {
    console.error('No event ID provided');
    return;
  }
  
  try {
    console.log('Fetching event details');
    const response = await axios.get(`${BASE_URL}/searchById/${eventId.value}`);
    console.log(response.data);
    eventDetails.value = response.data.data;
  } catch (error) {
    console.error('Error fetching event details:', error);
  }
});

// Function to toggle editing mode
const toggleEdit = () => {
  editing.value = !editing.value;
};

const saveChanges = async () => {
  try {
    await axios.put(`${BASE_URL}/editEvent/${eventId.value}`, eventDetails.value);
    console.log('Changes saved');
    alert('Event updated successfully');
    editing.value = false;
  } catch (error) {
    console.error('Error updating event:', error);
    alert('An error occurred while updating the event.');
  }
};


// Function to cancel changes and exit editing mode
const cancelEdit = () => {
  toggleEdit();
};
</script>

<template>
  <VRow>
    <VCol cols="12">
      <VCard outlined class="pa-8"> 
        <VCardTitle class="text-h5 mb-6">Event Details</VCardTitle> 
        <VCardText>
          <template v-if="editing">
            <div class="mb-4"> 
              <VTextField label="Event Title" v-model="eventDetails.eventTitle" outlined dense solo-inverted />
            </div>
            <div class="mb-4"> 
              <VTextField label="Event Date" v-model="eventDetails.eventDate" outlined dense solo-inverted type="date"/>
            </div>
            <div class="mb-4">
              <VTextarea label="Event Description" v-model="eventDetails.eventDesc" outlined rows="5" solo-inverted />
            </div>
            <div class="mb-4">
              <VTextField label="Event Location" v-model="eventDetails.eventLoc" outlined dense solo-inverted />
            </div>
            <div class="mb-4">
              <VTextField label="Start Time" v-model="eventDetails.startTime" outlined dense solo-inverted type="time" />
            </div>
            <div class="mb-4">
              <VTextField label="End Time" v-model="eventDetails.endTime" outlined dense solo-inverted  type="time"/>
            </div>
            <div class="mb-4">
              <VTextField label="Ticket Price" v-model="eventDetails.ticketPrice" outlined dense solo-inverted prefix="$" type="number"/>
            </div> 
            <div class="mb-4">
              <VTextField label="Filled" v-model="eventDetails.filled" outlined dense solo-inverted type="number"/>
            </div> 
            <div class="mb-4">
              <VTextField label="Capacity" v-model="eventDetails.capacity" outlined dense solo-inverted type="number"/>
            </div> 
            <div class="mb-4">
              <VTextField label="Cancel Fee" v-model="eventDetails.cancelFee" outlined dense solo-inverted prefix="$" type="number"/>
            </div> 
          </template>

          
          <template v-else>
            <div class="mb-3"><b>Event Title:</b> {{ eventDetails.eventTitle }}</div>
            <div class="mb-3"><b>Event Date:</b> {{ eventDetails.eventDate }}</div>
            <div class="mb-3"><b>Event Description:</b> {{ eventDetails.eventDesc }}</div>
            <div class="mb-3"><b>Event Location:</b> {{ eventDetails.eventLoc }}</div>
            <div class="mb-3"><b>Start Time:</b> {{ eventDetails.startTime }}</div>
            <div class="mb-3"><b>End Time:</b> {{ eventDetails.endTime }}</div>
            <div class="mb-3"><b>Ticket Price:</b> {{ eventDetails.ticketPrice }}</div>
            <div class="mb-3"><b>Filled:</b> {{ eventDetails.filled }}</div>
            <div class="mb-3"><b>Capacity:</b> {{ eventDetails.capacity }}</div>
            <div class="mb-3"><b>Cancel Fee:</b> {{ eventDetails.cancelFee }}</div>
          </template>
        </VCardText>
        <VCardActions class="justify-space-between pt-4"> 
          <VBtn v-if="editing" color="error" @click="cancelEdit">Cancel</VBtn>
          <VBtn v-if="editing" color="primary" @click="saveChanges">Save</VBtn>
          <VBtn v-else color="primary" @click="toggleEdit">Edit</VBtn>
        </VCardActions>
      </VCard>
    </VCol>


    <!-- Start of the ticketing details -->
    <VCol cols="12">
      <VCard title="Ticket Sales Information">
        <VCardText class="d-flex flex-column gap-y-8">

          <div>
            <span>Sales Start: 27 December 2023, 12am </span><br>
            <span>Sales End: 22 December 2023, 4pm </span><br>
          </div>

        </VCardText>
      </VCard>
    </VCol>

    <!-- The Policies: Exchange policies and admission policies -->
    <VCol cols="12">
      <!-- Start of event details -->
      <VCard title="Policies">
        <VCardText class="d-flex flex-column gap-y-8">

          <div>
            <h6 class="text-h6">
              Exchange & Refund Policies
            </h6>
            <ul style="padding-left: 20px">
              <li>The Organiser/Venue Owner reserves the right without refund or compensation to refuse admission/evict
                any
                person(s) whose conduct is disorderly or inappropriate or who poses a threat to security, or to the
                enjoyment of the Event by others.</li>
              <li>Ticket holders assume all risk of injury and all responsibility for property loss, destruction or
                theft
                and release the promoters, performers, sponsors, ticket outlets, venues, and their employees from any
                liability thereafter.</li>
              <li>The resale of ticket(s) at the same or any price in excess of the initial purchase price is
                prohibited.
              </li>
              <li>There is no refund, exchange, upgrade, or cancellation once ticket(s) are sold.</li>
              <li>We would like to caution members of the public against purchasing tickets from unauthorized sellers or
                3rd
                party websites. By purchasing tickets through these non-authorized points of sale, buyers take on the
                risk
                that the validity of the tickets cannot be guaranteed, with no refunds possible.</li>
            </ul>
          </div>

          <VDivider />

          <div>
            <h6 class="text-h6">
              Admission Policies
            </h6>
            <ul style="padding-left: 20px;">
              <li>Admission to show/venue by full ticket only. Printed/electronic tickets must be produced for
                admission.
              </li>
              <li>There will be no admission for infants in arms and children below 3 years old.</li>
              <li>Individuals aged 3 years old and above will be required to purchase a ticket for admission.</li>
              <li>No professional photography, videography of any kind is allowed.</li>
              <li>No iPads, tablets and laptops are allowed.</li>
            </ul>
          </div>


        </VCardText>
      </VCard>
    </VCol>

    <!-- Ways to buy ticket -->
    <VCol cols="12">
      <!-- Start of event details -->
      <VCard title="Ways to buy ticket">
        <VCardText class="d-flex flex-column gap-y-8">

          <div>
            <h6 class="text-h6">
              Online & Mobile
            </h6>
            <ul style="padding-left: 20px;margin-bottom: 10px">
              <li>Book tickets online or via our mobile app up to 6 months in advance. </li>
              <li>Ticket sales will close 24 hours before the event starts.</li>
              <li>Sign up for an account to easily book tickets for any event you're interested in.</li>
            </ul>
          </div>

          <VDivider />

          <div>
            <h6 class="text-h6">
              Onsite
            </h6>
            <ul style="padding-left: 20px;margin-bottom: 10px">
              <li>Visit our ticketing office to purchase tickets</li>
              <li>Opening hours: Monday to Sunday, 9 am to 11 pm.</li>
              <li>Customers can purchase tickets up to 24 hours before the show, subject to availability.</li>
            </ul>
          </div>


        </VCardText>
      </VCard>
    </VCol>

  </VRow>
</template>
