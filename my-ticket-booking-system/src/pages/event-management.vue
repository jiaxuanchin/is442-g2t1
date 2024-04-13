<script setup>
import { ref } from 'vue';
import CreateEvent from '@/views/pages/event-management/CreateEvent.vue';
import pages1 from '@images/pages/1.png'
import pic1 from '@images/pages/eg1.jpg'
import pic2 from '@images/pages/eg3.jfif'
import pic3 from '@images/pages/eg4.jfif'
import pic4 from '@images/pages/eg5.webp'
import pic5 from '@images/pages/eg6.jfif'
import pic6 from '@images/pages/eg2.jpg'

// State to hold the currently selected tab
const currentTab = ref('create');
const isCardDetailsVisible = ref(false)

const images = [pic1, pic2, pic3, pic4, pic5, pic6];

const randomImage = () => {
  const randomIndex = Math.floor(Math.random() * images.length); // Generate a random index
  return images[randomIndex]; // Return the image URL at the random index
};
const cardList = ref([]);


const apiUrl = 'http://localhost:8080/event';
const fetchEvents = async () => {
  try {
    const response = await fetch(apiUrl, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        }
      })
    if (!response.ok) throw new Error('Failed to fetch');
    const data = await response.json();
    cardList.value = data; 
  } catch (error) {
    console.error('Error fetching events:', error);
  }
};

onMounted(fetchEvents); 

const truncateDescription = (eventDesc) => {
  const maxLength = 100; // Set the maximum length of the description
  return eventDesc.length > maxLength ? eventDesc.slice(0, maxLength) + '...' : eventDesc;
};

</script>

<template>
  <VContainer>
    <VTabs v-model="currentTab" background-color="primary" dark>
      <VTab value="create">Create Event</VTab>
      <VTab value="view">View Events</VTab>
      <VTab value="sales-statistics">Sales Statistics</VTab>
    </VTabs>

    <!-- create event -->
    <VCard v-if="currentTab === 'create'" flat>
      <CreateEvent />
    </VCard>

    <!-- Edit events -->
    <VRow v-if="currentTab === 'view'">
      <VCol v-for="(card, index) in cardList" :key="index" cols="12" sm="4" md="3">
        <VCard>
          <VImg :src="randomImage()" cover />

          <VCardItem>
            <VCardTitle>{{ card.eventTitle }}</VCardTitle>
          </VCardItem>

          <VCardText class="card-description" style="font-size: 1.1rem;">
            {{ card.eventDate }} ({{ card.startTime }}) <br><br>
            {{ truncateDescription(card.eventDesc) }} <br><br>
            <div style="text-align: right;">

              <VBtn :to="{ name: 'EditEvent', params: { eventId: card.eventId } }">
                Edit Event
              </VBtn>

            </div>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

    <!-- sales statistics -->

    
    <VRow v-if="currentTab === 'sales-statistics'">
      <!-- Use v-for to generate cards -->
      <VCol v-for="(card, index) in cardList" :key="index" cols="12" sm="4" md="3">
        <VCard>
          <VImg :src="randomImage()" />

          <VCardItem>
            <VCardTitle>{{ card.eventTitle }}</VCardTitle>
          </VCardItem>

          <VCardText class="card-description" style="font-size: 1.1rem;">
            {{ card.eventDate }} ({{ card.startTime }}) <br><br>
            {{ truncateDescription(card.eventDesc) }} <br><br>
            <div style="text-align: right;">
              <VBtn :to="{ name: 'SalesStatistics', params: { eventId: card.eventId } }">
                Statistics Report
              </VBtn>
            </div>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

  </VContainer>
</template>
