import Home from "@/pages/HomeView.vue";
import Login from "@/pages/LoginView.vue";
import Register from "@/pages/RegisterView.vue";
import Dashboard from "@/pages/DashboardView.vue";
import EventList from "@/pages/EventList.vue";
import EventDetails from "@/pages/EventDetails.vue";
import EventCreate from "@/pages/EventCreate.vue";
import BookingForm from "@/pages/BookingForm.vue";
import SalesStatistics from "@/pages/SalesStatistics.vue";
import TicketValidation from "@/pages/TicketValidation.vue";
import CheckoutForm from "@/pages/CheckoutForm.vue";
import Return from "@/pages/ReturnView.vue";
import { createRouter, createWebHistory } from "vue-router";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", component: Home },
    { path: "/login", component: Login },
    { path: "/register", component: Register },
    { path: "/dashboard", component: Dashboard },
    { path: "/events", component: EventList },
    { path: "/events/create", component: EventCreate },
    { path: "/events/:id", component: EventDetails, props: true },
    { path: "/booking/:eventId", component: BookingForm, props: true },
    { path: "/statistics", component: SalesStatistics },
    { path: "/validate", component: TicketValidation },
    { path: "/payment", component: CheckoutForm },
    { path: "/payment/return", component: Return },
  ],
});

// // navigation guard -> wait until the DOM is updated
// router.afterEach((to) => {
//   nextTick(() => {
//     document.title = to.meta.title || "Payment";
//   });
// });

export default router;
