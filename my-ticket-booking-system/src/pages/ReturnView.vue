<script setup>
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import { loadStripe } from "@stripe/stripe-js";

import SrMessages from "./SrMessages.vue";

// reactive references -> change UI when it changes
const messages = ref([]);
const clientSecret = ref("");

const countdown = ref(5);

// currentRoute is a computed property that returns the query parameters of the current route using 'useRoute().query'
const currentRoute = computed(() => {
  return useRoute().query;
});

clientSecret.value = currentRoute.value?.payment_intent_client_secret;
// get payment intent client secret from query parameters

let stripe;
console.log(clientSecret.value);

onMounted(async () => {
  startCountdown();

  // get publishable key
  const { publishableKey } = await fetch("/api/payments/config").then((res) =>
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

function startCountdown() {
  const interval = setInterval(() => {
    if (countdown.value > 0) {
      countdown.value--;
    } else {
      clearInterval(interval);
      redirectToHomepage();
    }
  }, 1000);
}

function redirectToHomepage() {
  // Redirect to homepage
  window.location.href = "/home";
}
</script>

<template>
  <body>
    <main>
      <div class="container text-center" style="margin: 30px">
        <h1>Payment was successful!</h1>
        <p>
          A confirmation email regarding your booking details have been sent to
          your email. Click here to go <a href="/home">Home</a>.
        </p>
        <img width="300" src="../assets/email.jpg" alt="Email" />
        <h1>Thank you!</h1>
        <sr-messages v-if="clientSecret" :messages="messages" />
        <p v-if="countdown > 0">
          Redirecting to homepage in {{ countdown }} seconds...
        </p>
      </div>
    </main>
  </body>
</template>
