<script setup>
import pages1 from '@images/pages/eg6.jfif'
import pic1 from '@images/pages/eg1.jpg'
import pic2 from '@images/pages/eg3.jfif'
import pic3 from '@images/pages/eg4.jfif'
import pic4 from '@images/pages/eg5.webp'
import pic5 from '@images/pages/eg6.jfif'
import pic6 from '@images/pages/eg2.jpg'
import { ref, onMounted } from 'vue';
import axios from 'axios';


const isCardDetailsVisible = ref(false);

const images = [pic1, pic2, pic3, pic4, pic5, pic6];

const randomImage = () => {
  const randomIndex = Math.floor(Math.random() * images.length); // Generate a random index
  return images[randomIndex]; // Return the image URL at the random index
};

const cardList = ref([]); 
const upcomingEvents = ref([]);
const currentEvents = ref([]);

const fetchEventData = async () => {
  try {
    const response = await axios.get('http://localhost:8080/event',{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    const eventData = response.data;

    // Get the current time
    const currentTime = new Date();

    // Get user role
    const userId = localStorage.getItem('user_id'); 
    // console.log(userId)
    const user = await axios.get(`http://localhost:8080/UserEntity/${userId}`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    // console.log(user.data.role.name)
    const userRole = user.data.role.name

    // Filter events based on criteria
    eventData.forEach(event => {
      const eventTime = new Date(event.eventDate + 'T' + event.startTime);
      const timeDifference = eventTime.getTime() - currentTime.getTime();

      if (userRole == 'ticketing_officer') {
        // If the event is within 24 hours, skip it
        if (timeDifference>=0 && timeDifference <= 24 * 60 * 60 * 1000) {
          console.log(timeDifference)
          currentEvents.value.push({
            title: event.eventTitle,
            eventId: event.eventId,
            description: event.eventDesc,
            date: event.eventDate,
            time: `${event.startTime} - ${event.endTime}`
          });
        }
      }
      // If the event is within 24 hours, skip it
      if (timeDifference < 24 * 60 * 60 * 1000) {
        return;
      }

      // If the event is more than 6 months later, add it to upcomingEvents
      if (timeDifference > 6 * 30 * 24 * 60 * 60 * 1000) {
        upcomingEvents.value.push({
          title: event.eventTitle,
          eventId: event.eventId,
          description: event.eventDesc,
          date: event.eventDate,
          time: `${event.startTime} - ${event.endTime}`
        });
      } else {
        // Otherwise, add it to currentEvents
        currentEvents.value.push({
          title: event.eventTitle,
          eventId: event.eventId,
          description: event.eventDesc,
          date: event.eventDate,
          time: `${event.startTime} - ${event.endTime}`
        });
      }
    });
  } catch (error) {
    console.error('Error fetching event data:', error);
  }
};

onMounted(fetchEventData);

const truncateDescription = (description) => {
  const maxLength = 100;
  return description.length > maxLength ? description.slice(0, maxLength) + '...' : description;
};
</script>

<template>
  <div>
    <p class="text-2xl mb-6">Upcoming Events</p>
    <VRow>
      <VCol v-for="(event, index) in currentEvents" :key="index" cols="12" sm="4" md="3">
        <VCard>
          <VImg :src="randomImage()" cover style="width: 300px; height: 200px;" />
  
          <VCardTitle>{{ event.title }}</VCardTitle>
  
          <VCardText class="card-description" style="font-size: 1.1rem;">
            {{ event.date }} ({{ event.time }}) <br><br>
            {{ truncateDescription(event.description) }} <br><br>
            <div style="text-align: right;">
              <VBtn :to="'/event-details/' + event.eventId">
                View Details
              </VBtn>
            </div>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

    <p class="text-2xl mb-6 mt-6">Future Events</p>
    <VRow>
      <VCol v-for="(event, index) in upcomingEvents" :key="index" cols="12" sm="4" md="3">
        <VCard>
          <VImg :src="randomImage()" cover style="width: 300px; height: 200px;"/>
  
          <VCardTitle>{{ event.title }}</VCardTitle>
  
          <VCardText class="card-description" style="font-size: 1.1rem;">
            {{ event.date }} ({{ event.time }}) <br><br>
            {{ truncateDescription(event.description) }} <br><br>
            <div style="text-align: right;">
              <VBtn :to="'/event-details/' + event.eventId">
                View Details
              </VBtn>
            </div>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>
  </div>
</template>


<style lang="scss" scoped>
.card-description {
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
</style>
