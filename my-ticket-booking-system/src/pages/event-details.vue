<script setup>
import { useRoute } from "vue-router";
import { ref } from "vue";
// import { resolveConfig } from "vite";

const { params } = useRoute();
const eventId = params.eventId;

const ticketData = {
  quantity: 1,
};

const router = useRouter();

const refInputEl = ref();
const ticketDataLocal = ref(structuredClone(ticketData));

const goToCheckout = async () => {
  // check tickets entered
  if (ticketDataLocal.value.quantity < 1) {
    alert("Please select at least 1 ticket");
    return;
  } else if (ticketDataLocal.value.quantity > 5) {
    alert("You can only purchase up to 5 tickets per transaction");
    return;
  }

  // check if can book
  const responseBook = await fetch(`http://localhost:8080/booking/check`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      userId: 1,
      eventId: 1,
      numOfTickets: ticketDataLocal.value.quantity,
    }),
  }).then((res) => {
    if (res.status == 400) {
      res.json().then((data) => {
        console.log(data);
        alert(data.error);
      });
    }
    return res;
  });
  if (responseBook.status == 400) {
    return;
  }
  const data = {
    numTickets: ticketDataLocal.value.quantity,
    eventId: eventId,
  };
  router.push({
    name: "CheckoutForm",
    params: { data: JSON.stringify(data) },
  });
};
</script>

<template>
  <VRow>
    <VCol cols="12">
      <!-- Start of event details -->
      <VCard>
        <VCardText class="d-flex flex-column gap-y-8">
          <div>
            <h6 class="text-h6">Event Name</h6>
            <span>Location: Singapore indoor stadium</span><br />
            <span>Start: 23 December 2023, 4pm</span><br />
            <span>End: 23 December 2023, 8pm</span><br /><br />
            <span>Event description</span><br />
          </div>
        </VCardText>
      </VCard>
    </VCol>

    <!-- Start of the ticketing details -->
    <VCol cols="12">
      <VCard title="Ticket Sales Information">
        <VCardText class="d-flex flex-column gap-y-8">
          <div>
            <span>Sales Start: 27 December 2023, 12am </span><br />
            <span>Sales End: 22 December 2023, 4pm </span><br />
          </div>
        </VCardText>
      </VCard>
    </VCol>

    <!-- The Policies: Exchange policies and admission policies -->
    <VCol cols="12">
      <!-- Start of event details -->
      <VCard title="Policies">
        <VCardText class="d-flex flex-column gap-y-8">
          <div>
            <h6 class="text-h6">Exchange & Refund Policies</h6>
            <ul style="padding-left: 20px">
              <li>
                The Organiser/Venue Owner reserves the right without refund or
                compensation to refuse admission/evict any person(s) whose
                conduct is disorderly or inappropriate or who poses a threat to
                security, or to the enjoyment of the Event by others.
              </li>
              <li>
                Ticket holders assume all risk of injury and all responsibility
                for property loss, destruction or theft and release the
                promoters, performers, sponsors, ticket outlets, venues, and
                their employees from any liability thereafter.
              </li>
              <li>
                The resale of ticket(s) at the same or any price in excess of
                the initial purchase price is prohibited.
              </li>
              <li>
                There is no refund, exchange, upgrade, or cancellation once
                ticket(s) are sold.
              </li>
              <li>
                We would like to caution members of the public against
                purchasing tickets from unauthorized sellers or 3rd party
                websites. By purchasing tickets through these non-authorized
                points of sale, buyers take on the risk that the validity of the
                tickets cannot be guaranteed, with no refunds possible.
              </li>
            </ul>
          </div>

          <VDivider />

          <div>
            <h6 class="text-h6">Admission Policies</h6>
            <ul style="padding-left: 20px">
              <li>
                Admission to show/venue by full ticket only. Printed/electronic
                tickets must be produced for admission.
              </li>
              <li>
                There will be no admission for infants in arms and children
                below 3 years old.
              </li>
              <li>
                Individuals aged 3 years old and above will be required to
                purchase a ticket for admission.
              </li>
              <li>
                No professional photography, videography of any kind is allowed.
              </li>
              <li>No iPads, tablets and laptops are allowed.</li>
            </ul>
          </div>
        </VCardText>
      </VCard>
    </VCol>

    <!-- Ways to buy ticket -->
    <VCol cols="12">
      <!-- Start of event details -->
      <VCard title="Ways to buy ticket">
        <VCardText class="d-flex flex-column gap-y-8">
          <div>
            <h6 class="text-h6">Online & Mobile</h6>
            <ul style="padding-left: 20px; margin-bottom: 10px">
              <li>
                Book tickets online or via our mobile app up to 6 months in
                advance.
              </li>
              <li>Ticket sales will close 24 hours before the event starts.</li>
              <li>
                Sign up for an account to easily book tickets for any event
                you're interested in.
              </li>
            </ul>
          </div>

          <VDivider />

          <div>
            <h6 class="text-h6">Onsite</h6>
            <ul style="padding-left: 20px; margin-bottom: 10px">
              <li>Visit our ticketing office to purchase tickets</li>
              <li>Opening hours: Monday to Sunday, 9 am to 11 pm.</li>
              <li>
                Customers can purchase tickets up to 24 hours before the show,
                subject to availability.
              </li>
            </ul>
          </div>
        </VCardText>
      </VCard>
    </VCol>

    <!-- The form for quantity of tickets required -->
    <VCol cols="12">
      <VCard title="Choose your tickets">
        <VCardText>
          <VForm class="mt-6">
            <VRow>
              <!-- 👉 Quantity of tickets -->
              <VCol cols="12">
                <VTextField
                  v-model="ticketDataLocal.quantity"
                  label="Ticket Quantity"
                  placeholder="1"
                  type="number"
                  min="1"
                  max="5"
                />
              </VCol>

              <!-- Note -->
              <div
                style="
                  padding-left: 20px;
                  margin-bottom: 10px;
                  margin-top: 10px;
                "
              >
                <span>Note:</span>
                <ul style="padding-left: 30px; margin-left: 0">
                  <li>Limited to only 5 tickets per transaction.</li>
                </ul>
              </div>
            </VRow>

            <!-- 👉 Submit the forms -->
            <VRow>
              <VCol cols="12" class="text-end mt-4">
                <!-- Use 'text-end' class to align content to the right -->

                <VBtn @click="goToCheckout"> Purchase Tickets </VBtn>
              </VCol>
            </VRow>
          </VForm>
        </VCardText>
      </VCard>
    </VCol>
  </VRow>
</template>
