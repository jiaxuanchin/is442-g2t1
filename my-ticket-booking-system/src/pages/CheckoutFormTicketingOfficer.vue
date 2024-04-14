<script setup>
import { ref, onMounted } from "vue";
// ref: to create reactive variables

const eventData = ref(null);
const route = useRoute();

const email = ref("");
const loading = ref(false);
const password = ref("");

const show1 = ref(false);
const form = ref(false);

let userId = 0;

let numTickets = ref(0);
let totalPrice = ref(0);

onMounted(async () => {
  // fetch publishable key
  const data = JSON.parse(route.params.data);
  const eventId = data.eventId;
  console.log(eventId);

  numTickets = data.numTickets;
  console.log(numTickets);

  // const response = await this.$http.get(`/searchById/${eventId}`);
  // a;
  const response = await fetch(`http://localhost:8080/event/searchById/${eventId}`,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
    .then(
      (res) => res.json()
    );

  eventData.value = response.data;
  console.log(response);

  totalPrice = numTickets * eventData.value.ticketPrice;
});

// confirm booking
const confirmBooking = async (payType) => {
  console.log("confirm booking");
  console.log(userId)
  const bookingResponse = await fetch(
    `http://localhost:8080/booking/new/${payType}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      },
      body: JSON.stringify({
        userId: userId, // NOTE
        eventId: eventData.value.eventId,
        numOfTickets: numTickets,
      }),
    }
  )
  .then(function (response) {
      return response.text();
    })
    .then(function (data) {
      console.log(data); // this will be a string
      if (data != "Booking created") {
        alert(data);
        return false;
      } else {
        return true;
      }
    });
  console.log(bookingResponse);
  if (bookingResponse) {
    return true;
  }
};
const required = (v) => !!v || "Field is required";

const min = (v) => (v && v.length >= 8) || "Min 8 characters";

// when user pays with wallet
const onSubmitWallet = async () => {
  console.log("paid with wallet");
  if (!form.value) {
    return;
  }
  // retrieve customer using email
  console.log(email.value);
  const customer = await fetch(
    `http://localhost:8080/UserEntity/get/${email.value}`,
    {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    }
  ).then((res) => res.json());
  console.log(customer);

  if (customer.role.name != "customer") {
    alert("Invalid email. Not a customer.");
    return;
  }

  userId = customer.id;
  
   // check password
    const response = await fetch(`http://localhost:8080/api/auth/verify_password`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
      method: "POST",
      body: JSON.stringify({
        password: password.value,
        email: email.value,
      }),
    })
    .then((res) => res.json());
  console.log(response);

  if (response == false) {
    alert("Incorrect password");
    return;
  } else if (response == true) {
    const confirmBookingResponse = await confirmBooking("ewallet");

    if (confirmBookingResponse) {
      // redirect to success page
      window.location.href = `${window.location.origin}/payment/return`;
    }
  }
};
</script>

<template>
  <div class="container">
    <h1>Payment</h1>
    <v-row>
      <v-col cols="6" class="mt-7">
        <v-card color="indigo-darken-3">
          <v-card-item>
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
          </v-card-item>
        </v-card>
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

                <hr />
                <br />
                <p><b>Payment Summary: </b></p>
                <v-table>
                  <thead>
                    <tr>
                      <th class="text-left black-text">Ticket Price:</th>
                      <th class="text-left">Number of tickets:</th>
                      <th class="text-left">Total Price:</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td class="text-center">${{ eventData.ticketPrice }}</td>
                      <td class="text-center">{{ numTickets }}</td>
                      <td class="text-center">${{ totalPrice }}</td>
                    </tr>
                  </tbody>
                </v-table>
                <br />
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
