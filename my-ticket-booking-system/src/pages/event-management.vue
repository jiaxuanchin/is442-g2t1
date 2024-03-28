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

// Define a list of card information

const cardList = [
  {
    title: 'Cat',
    eventId: "2",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId: "1",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId: "3",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId: "4",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId: "2",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId: "3",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId: "4",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId: "2",
    description: 'Disney is coming to townDisney is coming to townDisney is coming to townDisney is coming to townDisney is coming to townDisney is coming to townDisney is coming to townDisney is coming to townDisney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },

]

const truncateDescription = (description) => {
  const maxLength = 100; // Set the maximum length of the description
  return description.length > maxLength ? description.slice(0, maxLength) + '...' : description;
};

</script>

<template>
  <VContainer>
    <VTabs v-model="currentTab" background-color="primary" dark>
      <VTab value="create">Create Event</VTab>
      <VTab value="view">View Events</VTab>
      <VTab value="sales-statistics">Sales Statistics</VTab>
    </VTabs>

    <!-- Tab content for 'Create Event' -->
    <VCard v-if="currentTab === 'create'" flat>
      <CreateEvent />
    </VCard>

    <!-- Tab content for 'View Events' -->
    <VRow v-if="currentTab === 'view'">
      <!-- Use v-for to generate cards -->
      <VCol v-for="(card, index) in cardList" :key="index" cols="12" sm="4" md="3">
        <VCard>
          <VImg :src="randomImage()" cover />

          <VCardItem>
            <VCardTitle>{{ card.title }}</VCardTitle>
          </VCardItem>

          <VCardText class="card-description" style="font-size: 1.1rem;">
            {{ card.date }} ({{ card.time }}) <br><br>
            {{ truncateDescription(card.description) }} <br><br>
            <div style="text-align: right;">
              <VBtn :to="'/edit-event/' + card.eventId">
                View Event
              </VBtn>
            </div>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

    <VRow v-if="currentTab === 'sales-statistics'">
      <!-- Use v-for to generate cards -->
      <VCol v-for="(card, index) in cardList" :key="index" cols="12" sm="4" md="3">
        <VCard>
          <VImg :src="randomImage()" cover />

          <VCardItem>
            <VCardTitle>{{ card.title }}</VCardTitle>
          </VCardItem>

          <VCardText class="card-description" style="font-size: 1.1rem;">
            {{ card.date }} ({{ card.time }}) <br><br>
            {{ truncateDescription(card.description) }} <br><br>
            <div style="text-align: right;">
              <VBtn :to="'/sales-statistics/' ">
                Statistics Report
              </VBtn>
            </div>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>
  </VContainer>
</template>
