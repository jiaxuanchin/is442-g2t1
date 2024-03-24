<script setup>
import pages1 from '@images/pages/1.png'

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
      <!-- Use v-for to generate cards -->
      <VCol
        v-for="(card, index) in cardList"
        :key="index"
        cols="12"
        sm="4"
        md="3"
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
              <VBtn :to="'/event-details/' + card.eventId">
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
