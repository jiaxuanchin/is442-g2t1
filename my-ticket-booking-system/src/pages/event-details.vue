<script setup>
import { useRoute } from 'vue-router'

const {params} = useRoute()
const eventId = params.eventId

const ticketData = {
  ticketType : "VIP",
  quantity: 1,
}

const refInputEl = ref()
const ticketDataLocal = ref(structuredClone(ticketData))

const tickets = [
  {
    type: 'VIP',
    price: 308
  },
  {
    type: 'CAT 1',
    price: 128
  },
  {
    type: 'CAT 2',
    price: 108
  },
  {
    type: 'CAT 3',
    price: 98
  },
  {
    type: 'CAT 4',
    price: 88
  },
]

const ticketType = [
  "VIP",
  "CAT 1",
  "CAT 2",
  "CAT 3",
  "CAT 4"
]

</script>

<template>
  <VRow>
    <VCol cols="12">

      <!-- Start of event details -->
      <VCard title="Event Details">
        <VCardText class="d-flex flex-column gap-y-8">

          <div>
            <h6 class="text-h6">
              Event Name
            </h6>
            <span>Event description</span><br>
            <span>23 January 2023, Singapore indoor stadium, 6pm</span>
          </div>

          <VDivider />

          <div>
            <h6 class="text-h6">
              About Artist (tbc)
            </h6>
            <span>description about artist</span>
          </div>


        </VCardText>
      </VCard>
    </VCol>

    <!-- Start of the ticketing details -->
    <VCol cols="12">
      <VCard title="Ticket Pricing">
        <VCardText class="d-flex flex-column gap-y-8">

          <div>
            <h6 class="text-h6">
              Ticket Category
            </h6>
            <VTable>
              <thead>
                <tr>
                  <th class="text-uppercase">
                    Ticket Type
                  </th>
                  <th>
                    Ticket Price ($)
                  </th>
                </tr>
              </thead>

              <tbody>
                <tr
                  v-for="item in tickets"
                  :key="item.tickets"
                >
                  <td>
                    {{ item.type }}
                  </td>
                  <td class="text-center">
                    {{ item.price }}
                  </td>
                </tr>
              </tbody>
            </VTable><br>
            <h6 class="text-h6">
              VIP PACKAGE includes:
            </h6>

            <ul style="padding-left: 30px; margin-left: 0;">
              <li>Includes one (1) Cat 1 ($128) ticket</li>
              <li>Meet Amber Liu for an individual photo opportunity by a professional photographer</li>
              <li>Pre-show soundcheck party with Amber Liu</li>
              <li>One (1) signed poster by Amber Liu</li>
            </ul>

          </div>
          <VDivider />
          <div>
            <h6 class="text-h6">
              Note:
            </h6>
            <ul style="padding-left: 30px; margin-left: 0;">
              <li>Limited to only 5 tickets per transaction.</li>
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

            <!-- 👉 Asking for the ticket type -->
            <VRow>
              <VCol md="6" cols="12">
                  <VSelect
                  v-model="ticketDataLocal.ticketType"
                  label="Ticket Type"
                  placeholder="Select Ticket Type"
                  :items="ticketType"
                  :menu-props="{ maxHeight: 200 }"
                  />
              </VCol>

              <!-- 👉 Quantity of tickets -->
              <VCol
                cols="12"
                md="6"
              >
                <VTextField
                  v-model="ticketDataLocal.quantity"
                  label="Ticket Quantity"
                  placeholder="1"
                />
              </VCol>
            </VRow>

            <!-- 👉 Submit the forms -->
            <VRow>
              <VCol cols="12" class="text-end mt-4"> <!-- Use 'text-end' class to align content to the right -->
                <router-link :to="'/other-page/' + eventId" style="display: flex; justify-content: flex-end;">
                  <VBtn>
                    Purchase Tickets
                  </VBtn>
                </router-link>
              </VCol>
            </VRow>

          </VForm>  
        </VCardText>
      </VCard>
    </VCol>
  </VRow>
</template>
