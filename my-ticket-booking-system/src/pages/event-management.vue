<script setup>
import pages1 from '@images/pages/1.png'
import CreateEvent from '@/views/pages/event-management/CreateEvent.vue'

const isCardDetailsVisible = ref(false)

// Define a list of card information

const cardList = [
  {
    title: 'Cat',
    eventId : "2",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId : "1",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId : "3",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId : "4",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  {
    title: 'Musical',
    eventId : "2",
    description: 'Disney is coming to town',
    date: '2023-09-15',
    time: '18:00 - 21:00'
  },
  // Add more card objects as needed
]
</script>

<template>
        
    <VRow>
        <!-- Page Title -->
        <VCol cols="12">
            <div>
                <h1 class="align-center text-h3">
                    Event Management
                </h1>
            </div>
        </VCol>

        <VCol cols="12">
            <!-- 👉 Multiple Column -->
            <VCard title="Create A New Event">
            <VCardText>
                <CreateEvent />
            </VCardText>
            </VCard>
        </VCol>
    </VRow>


  <VRow>
    <!-- Use v-for to generate cards -->
    <VCol
      v-for="(card, index) in cardList"
      :key="index"
      cols="12"
      sm="6"
      md="4"
    >
      <VCard>
        <VImg :src="pages1" cover />

        <VCardItem>
          <VCardTitle>{{ card.title }}</VCardTitle>
        </VCardItem>

        <VCardText>
          {{ card.description }} <br><br>
          {{ card.date }} ({{ card.time }}) <br><br>
          <div style="text-align: right;">
            <VBtn :to="'/event-details-event-manager/' + card.eventId">
                View Details
            </VBtn>
          </div>
        </VCardText>
      </VCard>
    </VCol>
    <!-- End of card generation -->

  </VRow>
</template>

<style lang="scss" scoped>
</style>
