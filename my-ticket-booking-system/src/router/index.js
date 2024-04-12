import { createRouter, createWebHistory } from "vue-router";

import CheckoutForm from "@/pages/CheckoutForm.vue";
import Return from "@/pages/ReturnView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: "/", redirect: "/dashboard" },
    {
      path: "/",
      component: () => import("../layouts/default.vue"),
      children: [
        {
          path: "dashboard",
          component: () => import("../pages/dashboard.vue"),
        },
        {
          path: "home",
          component: () => import("../pages/home.vue"),
        },
        {
          path: "account-settings",
          component: () => import("../pages/account-settings.vue"),
        },
        {
          path: 'my-wallet',
          component: () => import('../views/pages/account-settings/AccountSettingsWallet.vue'),
        },
        {
          path: 'my-bookings',
          component: () => import('../pages/my-bookings.vue'),
        },
        {
          path: "typography",
          component: () => import("../pages/typography.vue"),
        },
        {
          path: "icons",
          component: () => import("../pages/icons.vue"),
        },
        {
          path: "cards",
          component: () => import("../pages/cards.vue"),
        },
        {
          path: "tables",
          component: () => import("../pages/tables.vue"),
        },
        {
          path: "form-layouts",
          component: () => import("../pages/form-layouts.vue"),
        },
        {
          path: "/booking-details/:bookingId",
          component: () => import("../pages/booking-details.vue"),
          props: true,
        },
        {
          path: '/event-details/:eventId',
          component: () => import('../pages/event-details.vue'),
          props: true
        },
        {
          path: 'ticketing-verification',
          component: () => import('../pages/ticketing-verification.vue'),
          props: true
        },
        {
          path: 'ticketing-officer',
          component: () => import('../pages/ticketing-officer.vue'),
          props: true
        },
        {
          path: '/sales-statistics/:eventId',
          name: 'SalesStatistics',
          component: () => import('../pages/sales-statistics.vue'),
          props: true
        },
        {
          path: 'add-ticketing-officer',
          component: () => import('../pages/add-ticketing-officer.vue'),
          props: true
        },
        {
          path: 'event-management',
          component: () => import('../pages/event-management.vue'),
          props: true
        },
        {
          path: '/edit-event/:eventId',
          name: 'EditEvent',
          component: () => import('../pages/edit-event.vue'),
          props: true
        },
        
        
      ],
    },
    {
      path: "/",
      component: () => import("../layouts/blank.vue"),
      children: [
        {
          path: "login",
          component: () => import("../pages/login.vue"),
        },
        {
          path: "register",
          component: () => import("../pages/register.vue"),
        },
        {
          path: "/:pathMatch(.*)*",
          component: () => import("../pages/[...all].vue"),
        },
      ],
    },
    { path: "/payment", component: CheckoutForm },
    { path: "/payment/return", component: Return },
  ],
});

export default router;
