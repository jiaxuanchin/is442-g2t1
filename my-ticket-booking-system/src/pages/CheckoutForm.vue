<script setup>
import { ref, onMounted } from "vue";
// ref: to create reactive variables
import { loadStripe } from "@stripe/stripe-js"; // import strip object

import SrMessages from "./SrMessages.vue"; // display messages

const isLoading = ref(false); // track whether data is loaded
const messages = ref([]); // store payment messages

let stripe; // hold Stripe object
let elements; // hold Stripe Elements object

onMounted(async () => {
  // fetch publishable key
  const { publishableKey } = await fetch(
    "http://localhost:8080/api/payments/config"
  ).then((res) => res.json());
  stripe = await loadStripe(publishableKey); // initialize the Stripe object
  console.log(publishableKey);
  console.log(stripe);

  // fetch client secret -> to confirm payment
  const { clientSecret } = await fetch(
    "http://localhost:8080/api/payments/create-payment-intent",
    {
      method: "POST",
    }
  ).then((res) => {
    console.log(res);
    return res.json();
  });

  console.log(clientSecret);

  messages.value.push(`Client secret returned.`);
  console.log(messages);

  // create payment form element

  elements = stripe.elements({ clientSecret });
  const paymentElement = elements.create("payment");
  paymentElement.mount("#payment-element"); // mount payment form to DOM to display
});

// when user submits form
const handleSubmit = async () => {
  console.log("submitted form");
  if (isLoading.value) {
    return;
  }

  isLoading.value = true;

  // confirm payment
  const { error } = await stripe.confirmPayment({
    elements,
    confirmParams: {
      return_url: `${window.location.origin}/payment/return`, // generates Return.vue
    },
  });

  if (error.type === "card_error" || error.type === "validation_error") {
    messages.value.push(error.message);
  } else {
    messages.value.push("An unexpected error occured."); // display any error message
  }

  isLoading.value = false; // this is to prevent duplicate submissions when waiting for the payment confirmation response from stripe
};

// maintains PCI compliance requirements
</script>
<template>
  <main>
    <h1>Payment</h1>

    <p>
      Enable more payment method types
      <a
        href="https://dashboard.stripe.com/settings/payment_methods"
        target="_blank"
        >in your dashboard</a
      >.
    </p>

    <form id="payment-form" @submit.prevent="handleSubmit">
      <div id="payment-element" />
      <button id="submit" :disabled="isLoading">Pay now</button>
      <sr-messages :messages="messages" />
    </form>
  </main>
</template>
