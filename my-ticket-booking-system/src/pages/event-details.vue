<script setup>
import { useRoute } from 'vue-router'
import { ref } from "vue";
import axios from 'axios'

const { params } = useRoute()
const eventId = params.eventId

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
      'Authorization': `Bearer ${localStorage.getItem('token')}`
    },
    body: JSON.stringify({
      userId: parseInt(localStorage.getItem("user_id")),
      eventId: eventId,
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
  let userId = parseInt(localStorage.getItem("user_id"));

  // FOR TESTING
  // const response1 = await axios.get(`http://localhost:8080/booking/all`,{
  //   headers: {
  //       'Authorization': `Bearer ${localStorage.getItem('token')}`
  //     }
  //   })
  // console.log(response1.data);

  const response = await fetch(`http://localhost:8080/UserEntity/${userId}`,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
    .then((res) => res.json())

  console.log(response);

  
  if (response.role.name == "customer") {
    router.push({
      name: "CheckoutForm",
      params: { data: JSON.stringify(data) },
    });
  } else if (response.role.name == "ticketing_officer") {
    router.push({
      name: "CheckoutFormTicketingOfficer",
      params: { data: JSON.stringify(data) },
    });
  } else {
    alert("You are not authorized to purchase tickets");
  }
};
const eventData = ref(null)

// Fetch event details from the API
axios.get(`http://localhost:8080/event/searchById/${eventId}`, {
  headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
})
  .then(response => {
    // Update the eventData with the response data
    eventData.value = response.data.data
    console.log(response.data.data)
  })
  .catch(error => {
    console.error('Error fetching event details:', error)
  })

// Function to calculate sales start and end dates based on event details
function calculateSalesDates(event) {
  if (!event) return null;

  // Parse event date and start time
  const eventDate = new Date(event.eventDate);
  const startTime = event.startTime.split(':').map(Number);

  // Calculate sales start date (6 months before the event date and time)
  const salesStartDate = new Date(eventDate);
  salesStartDate.setMonth(salesStartDate.getMonth() - 6);
  salesStartDate.setHours(startTime[0], startTime[1]);

  // Calculate sales end date (24 hours before the event start time)
  const salesEndDate = new Date(eventDate);
  salesEndDate.setDate(salesEndDate.getDate() - 1);
  salesEndDate.setHours(startTime[0], startTime[1]);

  return { 
    salesStartDate, 
    salesEndDate 
  };
}

const salesDates = ref(null);

watchEffect(() => {
  // Recalculate salesDates whenever eventData changes
  salesDates.value = calculateSalesDates(eventData.value);
});

function formatDate(dateString) {
  const parts = dateString.split('-');
  // Ensure parts are numbers
  const year = parseInt(parts[0]);
  const month = parseInt(parts[1]);
  const day = parseInt(parts[2]);
  // Create a new date object
  const date = new Date(year, month - 1, day);
  // Get the formatted date string
  const formattedDate = `${date.getMonth() + 1}/${date.getDate()}/${date.getFullYear()}`;
  return formattedDate;
}

function formatTime(timeString) {
  const [hours, minutes] = timeString.split(':').map(Number);
  const date = new Date();
  date.setHours(hours);
  date.setMinutes(minutes);
  // Use toLocaleTimeString() with appropriate options
  const formattedTime = date.toLocaleTimeString([], { hour: 'numeric', minute: '2-digit', second: '2-digit' });
  return formattedTime;
}

// Computed property to determine if the form should be hidden
const isSalesStartDatePast = computed(() => {
  console.log('salesDates:', salesDates.value);
  if (!salesDates.value || !salesDates.value.salesStartDate) {
    return false;
  }
  const today = new Date();
  const salesStartDate = salesDates.value.salesStartDate;
  console.log("today: ", today)
  console.log('salesStartDate:', salesStartDate);
  console.log(salesStartDate < today)
  return salesStartDate > today;
});
</script>


<template>
  <VRow>
    <VCol cols="12">
      <!-- Start of event details -->
      <VCard  v-if="eventData">
        <VCardText class="d-flex flex-column gap-y-8">
          <div>
            <h6 class="text-h6">
              {{eventData.eventTitle}}
            </h6>
            <span>Location: {{eventData.eventLoc}}</span><br>
            <span>Start: {{formatDate(eventData.eventDate)}}, {{formatTime(eventData.startTime)}}</span><br>
            <span>End: {{formatDate(eventData.eventDate)}}, {{formatTime(eventData.endTime)}}</span><br><br>
            <span> {{eventData.eventDesc}}</span><br>
          </div>
        </VCardText>
      </VCard>
    </VCol>

    <!-- Start of the ticketing details -->
    <VCol cols="12">
      <VCard title="Ticket Sales Information">
        <VCardText class="d-flex flex-column gap-y-8">

          <div v-if="salesDates">
            <span v-if="salesDates.salesStartDate">Sales Start: {{ salesDates.salesStartDate.toLocaleDateString() }}, {{ salesDates.salesStartDate.toLocaleTimeString() }}</span><br>
            <span v-if="salesDates.salesEndDate">Sales End: {{ salesDates.salesEndDate.toLocaleDateString() }}, {{ salesDates.salesEndDate.toLocaleTimeString() }}</span><br>
          </div>
          <div v-else>
            Loading sales information...
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
  <VCol cols="12" v-if="!isSalesStartDatePast">
    <VCard title="Choose your tickets">
      <VCardText>
        <VForm class="mt-6">

          <VRow>

            <!-- 👉 Quantity of tickets -->
            <VCol
              cols="12"
            >
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
            <div style="padding-left: 20px;margin-bottom: 10px;margin-top: 10px">
              <span>Note:</span>
              <ul style="padding-left: 30px; margin-left: 0;">
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

