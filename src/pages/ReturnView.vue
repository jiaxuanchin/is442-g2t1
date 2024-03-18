<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { loadStripe } from "@stripe/stripe-js";

import SrMessages from "./SrMessages.vue";

// reactive references -> change UI when it changes
const messages = ref([]);
const clientSecret = ref("");

const currentRoute = computed(() => {
  return useRoute().query;
});
clientSecret.value = currentRoute.value?.payment_intent_client_secret;
// get payment intent client secret from query parameters

let stripe;
console.log(clientSecret.value);

onMounted(async () => {
  // get publishable key
  const { publishableKey } = await fetch("/api/payments//config").then((res) =>
    res.json()
  );
  stripe = await loadStripe(publishableKey);

  // retrieve payment intent details using client secret
  const { error, paymentIntent } = await stripe.retrievePaymentIntent(
    clientSecret.value
  );

  if (error) {
    messages.value.append(error.message);
  }
  messages.value.push(`Payment ${paymentIntent.status}: ${paymentIntent.id}`);
});
</script>

<template>
  <body>
    <main>
      <a href="/">Home</a>
      <h1>Payment was successful.</h1>
      <h1>Thank you!</h1>
      <sr-messages v-if="clientSecret" :messages="messages" />
    </main>
  </body>
</template>
