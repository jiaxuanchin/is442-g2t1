<script setup>
import { ref, onMounted } from "vue";
// ref: to create reactive variables
import { loadStripe } from "@stripe/stripe-js"; // import stripe object

// import SrMessages from "./SrMessages.vue"; // display messages

const isLoading = ref(false); // track whether data is loaded
const messages = ref([]); // store payment messages

let stripe; // hold Stripe object
let elements; // hold Stripe Elements object

const route = useRoute();

const eventData = ref(null);

const email = ref("");
const loading = ref(false);
const password = ref("");

const show1 = ref(false);

onMounted(async () => {
  // fetch publishable key
  const eventId = route.params.id;
  console.log(route.params.id);

  // const response = await this.$http.get(`/searchById/${eventId}`);
  // a;
  const response = await fetch(
    `http://localhost:8080/event/searchById/${eventId}`
  ).then((res) => res.json());

  eventData.value = response.data;
  console.log(response);

  const { publishableKey } = await fetch(
    "http://localhost:8080/api/payments/config"
  ).then((res) => res.json());

  stripe = await loadStripe(publishableKey); // initialize the Stripe object
  console.log(publishableKey);

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

const toggleForm = () => {
  showForm.value = !showForm.value;
};

const hideForm = () => {
  showForm.value = false;
};

const showForm = ref(true); // track whether form is shown

const required = (v) => !!v || "Field is required";

const min = (v) => (v && v.length >= 8) || "Min 8 characters";

const onSubmitWallet = async () => {
  console.log("payed with wallet");
  const response = await fetch(
    `http://localhost:8080/api/auth/verify_password/${password}`
  ).then((res) => res.json());
  console.log(response);
  if (response.data == true) {
    // redirect to success page
    window.location.href = `${window.location.origin}/payment/return`;
  }
};
</script>

<template>
  <div class="container">
    <h1>Payment</h1>
    <v-row>
      <v-col cols="6">
        <div id="content">
          <v-expansion-panels>
            <v-expansion-panel
              title="Stripe Payment"
              :value="showForm"
              @click="toggleForm"
            >
              <main v-show="showForm">
                <p>Please enter your card details.</p>

                <form id="payment-form" @submit.prevent="handleSubmit">
                  <div id="payment-element" />
                  <button id="submit" :disabled="isLoading">Pay now</button>
                  <!-- <sr-messages :messages="messages" /> -->
                </form>
              </main>
            </v-expansion-panel>
            <v-expansion-panel title="Wallet" @click="hideForm">
              <v-expansion-panel-text>
                <!-- <v-text-field
                  v-model="trip.name"
                  placeholder="Caribbean Cruise"
                  hide-details
                ></v-text-field> -->
                <v-expansion-panel-text>
                  User id: Apple
                </v-expansion-panel-text>
                <v-expansion-panel-text>
                  E-wallet balance: $0.12
                </v-expansion-panel-text>
                <v-expansion-panel-text>
                  <v-form v-model="form" @submit.prevent="onSubmitWallet">
                    <v-text-field
                      v-model="email"
                      :readonly="loading"
                      :rules="[required]"
                      class="mb-2"
                      label="Email"
                      clearable
                    ></v-text-field>

                    <v-text-field
                      v-model="password"
                      :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                      :type="show1 ? 'text' : 'password'"
                      :readonly="loading"
                      :rules="[required, min]"
                      label="Password"
                      placeholder="Enter your password"
                      clearable
                      counter
                      @click:append="show1 = !show1"
                    ></v-text-field>

                    <br />

                    <v-btn
                      :disabled="!form"
                      :loading="loading"
                      color="success"
                      size="large"
                      type="submit"
                      variant="elevated"
                      block
                    >
                      Pay
                    </v-btn>
                  </v-form>
                </v-expansion-panel-text>
              </v-expansion-panel-text>
            </v-expansion-panel>
          </v-expansion-panels>
        </div>
      </v-col>
      <v-col cols="6" class="mt-7">
        <v-card color="indigo-darken-3">
          <v-card-item>
            <div>
              <div class="text mb-1"></div>
              <div class="text-h5 mb-1" style="color: black">
                Event Details:
              </div>
              <div
                v-if="eventData != null"
                class="text mt-6"
                style="color: black"
              >
                <p><b>Title: </b> {{ eventData.eventTitle }}</p>
                <p><b>About: </b> {{ eventData.eventDesc }}</p>
                <p><b>Date: </b> {{ eventData.eventDate }}</p>
                <p>
                  <b>Time: </b> {{ eventData.startTime }} -
                  {{ eventData.endTime }}
                </p>
                <p><b>Venue: </b> {{ eventData.eventLoc }}</p>
                <p><b>Ticket Price: </b> {{ eventData.ticketPrice }}</p>
                <p><b>Total Price: </b> {{ eventData.ticketPrice }}</p>
                <div style="color: gray">
                  To proceed, please pay with Stripe or opt for e-wallet
                  payment.
                  <br />
                  A confirmation email will be sent to your email directly after
                  purchase. These mobile tickets will be transferred directly to
                  you from a trusted seller. We'll email you instructions on how
                  to accept them on the original ticket provider's mobile app.
                </div>
              </div>
            </div>
          </v-card-item>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Raleway&display=swap");

:root {
  --light-grey: #f6f9fc;
  --dark-terminal-color: #0a2540;
  --accent-color: #635bff;
  --radius: 3px;
}

.container {
  margin: 40px; /* Add margin to create spacing */
}

#content {
  margin-top: 30px;
}

main {
  width: 480px;
  margin: 50px;
}

form > * {
  margin: 10px 0;
}

button {
  background-color: #635bff;
}

button {
  background: #635bff;
  border-radius: 3px;
  color: white;
  border: 0;
  padding: 12px 16px;
  margin-top: 16px;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.2s ease;
  display: block;
}
button:hover {
  filter: contrast(115%);
}
button:active {
  transform: translateY(0px) scale(0.98);
  filter: brightness(0.9);
}
button:disabled {
  opacity: 0.5;
  cursor: none;
}

input,
select {
  display: block;
  font-size: 1.1em;
  width: 100%;
  margin-bottom: 10px;
}

label {
  display: block;
}

a {
  color: var(--accent-color);
  font-weight: 900;
}

small {
  font-size: 0.6em;
}

fieldset,
input,
select {
  border: 1px solid #efefef;
}

#payment-form {
  border: #f6f9fc solid 1px;
  border-radius: var(--radius);
  padding: 20px;
  margin: 20px 0;
  box-shadow: 0 30px 50px -20px rgb(50 50 93 / 25%),
    0 30px 60px -30px rgb(0 0 0 / 30%);
}

#messages {
  font-family: source-code-pro, Menlo, Monaco, Consolas, "Courier New";
  background-color: #0a253c;
  color: #00d924;
  padding: 20px;
  margin: 20px 0;
  border-radius: var(--radius);
  font-size: 0.7em;
}
</style>
