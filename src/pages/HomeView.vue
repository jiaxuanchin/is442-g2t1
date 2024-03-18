<template>
  <div>
    <h1>Welcome to the Ticket Booking System</h1>
    <p>
      Please <router-link to="/login">login</router-link> or
      <router-link to="/register">register</router-link> to book tickets.
    </p>
    <!-- If  user is logged in, show navigation to the dashboard or events -->
    <router-link v-if="isLoggedIn" to="/dashboard">Go to Dashboard</router-link>
    <router-link v-if="isLoggedIn" to="/events">View Events</router-link>
  </div>
</template>

<script>
export default {
  computed: {
    isLoggedIn() {
      // check if `user` state is not null.
      return this.$store.state.user !== null;
    },
  },
};
</script>
