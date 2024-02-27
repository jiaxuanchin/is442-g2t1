import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    users: [], // hold Event Managers, Ticketing Officers, and Customers
    events: [],
    bookings: [],
    tickets: [],
  },
  mutations: {
    // User-related mutations
    REGISTER_USER(state, user) {
      state.users.push(user);
    },
    SET_ACCOUNT_BALANCE(state, { userId, balance }) {
      const user = state.users.find(u => u.email === userId);
      if (user) user.accountBalance = balance;
    },

    // Event-related mutations
    CREATE_EVENT(state, event) {
      state.events.push(event);
    },
    UPDATE_EVENT(state, updatedEvent) {
      const index = state.events.findIndex(e => e.eventName === updatedEvent.eventName);
      if (index !== -1) Vue.set(state.events, index, updatedEvent);
    },
    CANCEL_EVENT(state, eventName) {
      const index = state.events.findIndex(e => e.eventName === eventName);
      if (index !== -1) state.events.splice(index, 1);
    },

    // Booking-related mutations
    CREATE_BOOKING(state, booking) {
      state.bookings.push(booking);
    },
    CANCEL_BOOKING(state, bookingId) {
      const index = state.bookings.findIndex(b => b.id === bookingId);
      if (index !== -1) state.bookings.splice(index, 1);
    },

    // Ticket-related mutations
    ISSUE_TICKET(state, ticket) {
      state.tickets.push(ticket);
    },
  },

  actions: {
    // User-related actions
    async registerUser({ commit }, userData) {
      // replace with user API call
      commit('REGISTER_USER', userData);
    },

    // Event-related actions
    async createEvent({ commit }, eventData) {
      // replace with event API call
      commit('CREATE_EVENT', eventData);
    },

    // Booking-related actions
    async createBooking({ commit }, bookingData) {
      // replace with booking API call
      commit('CREATE_BOOKING', bookingData);
    },
  },
  
  getters: {
    // Define getters to retrieve state
    getUserByEmail: state => email => state.users.find(user => user.email === email),
    getEventByName: state => name => state.events.find(event => event.eventName === name),
    getBookingsByUser: state => userId => state.bookings.filter(booking => booking.userId === userId),
    // add more getters...
  },
});
