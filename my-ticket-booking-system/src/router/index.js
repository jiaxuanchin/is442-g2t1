import Vue from 'vue';
import Router from 'vue-router';
import Home from '@/pages/Home.vue';
import Login from '@/pages/Login.vue';
import Register from '@/pages/Register.vue';
import Dashboard from '@/pages/Dashboard.vue';
import EventList from '@/pages/EventList.vue';
import EventDetails from '@/pages/EventDetails.vue';
import EventCreate from '@/pages/EventCreate.vue';
import BookingForm from '@/pages/BookingForm.vue';
import SalesStatistics from '@/pages/SalesStatistics.vue';
import TicketValidation from '@/pages/TicketValidation.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    { path: '/', component: Home },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/dashboard', component: Dashboard },
    { path: '/events', component: EventList },
    { path: '/events/create', component: EventCreate },
    { path: '/events/:id', component: EventDetails, props: true },
    { path: '/booking/:eventId', component: BookingForm, props: true },
    { path: '/statistics', component: SalesStatistics },
    { path: '/validate', component: TicketValidation },
   
  ],
});
