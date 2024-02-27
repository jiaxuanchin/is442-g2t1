<template>
    <div>
      <h1>Event List</h1>
      <ul>
        <li v-for="event in events" :key="event.id">
          <router-link :to="{ name: 'EventDetails', params: { id: event.id } }">{{ event.eventName }}</router-link>
        </li>
      </ul>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        events: []
      };
    },
    created() {
      this.fetchEvents();
    },
    methods: {
      async fetchEvents() {
        try {
          // API call to fetch events
          const response = await this.$http.get('/events');
          this.events = response.data;
        } catch (error) {
          console.error('There was an error fetching the events:', error);
        }
      }
    }
  };
  </script>
  