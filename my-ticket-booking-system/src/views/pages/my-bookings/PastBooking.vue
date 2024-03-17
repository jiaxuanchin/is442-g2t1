// ToDo: Fetch info from database

<script setup>
import { VDataTable } from 'vuetify/labs/VDataTable'

const cardNumber = ref('0000 0000 0000 0000')
const isExpiryDateVisible = ref(false)
const isCsvVisible = ref(false)
const expiryDate = ref('11/25')
const csv = ref('543')
const accountBalance = ref('1000.00');

const passwordRequirements = [
  'Minimum 8 characters long - the more, the better',
  'At least one lowercase character',
  'At least one number, symbol, or whitespace character',
]

const cards = [
  {
    name: 'Goh Kelly',
    number: '1234 5678 9453'
  },
  {
    name: 'Goh Kelly',
    number: '1234 1234 1234'
  },
  {
    name: 'Goh Kelly',
    number: '1234 4325 2352'
  }
]
</script>

<template>
  <VRow>
    <!-- SECTION: Account e-wallet balance -->
    <VCol cols="12">
      <VCard title="Account e-wallet Balance">
        <VCardText>
          <p>Current balance</p>
          <p class="text-h5 font-weight-bold">$ {{ accountBalance }}</p>
        </VCardText>
      </VCard>
    </VCol>
    <!-- !SECTION -->

    <!-- SECTION: Your cards -->
    <VCol cols="12">
      <VCard title="Your Cards">
        <VTable>
          <thead>
            <tr>
              <th class="text-uppercase">
                Card holder name
              </th>
              <th>
                Card number
              </th>
            </tr>
          </thead>

          <tbody>
            <tr
              v-for="item in cards"
              :key="item.card"
            >
              <td>
                {{ item.name }}
              </td>
              <td>
                {{ item.number }}
              </td>
            </tr>
          </tbody>
        </VTable>
      </VCard>
    </VCol>
    <!-- !SECTION -->


    <!-- SECTION: Add a new card -->
    <VCol cols="12">
      <VCard title="Add a new card">
        <VForm>
          <VCardText>
            <!-- 👉 Card number -->
            <VRow>
              <VCol
                cols="12"
                md="12"
              >
                <VTextField
                  v-model="cardNumber"
                  label="Card Number"
                  placeholder="0000 0000 0000 0000"
                />
              </VCol>
            </VRow>

            <!-- 👉 Card information -->
            <VRow>
              <VCol
                cols="12"
                md="6"
              >
                <!-- 👉 Expiry Date -->
                <VTextField
                  v-model="expiryDate"
                  :type="isExpiryDateVisible ? 'text' : 'password'"
                  :append-inner-icon="isExpiryDateVisible ? 'bx-hide' : 'bx-show'"
                  label="Expiry Date"
                  placeholder="············"
                  @click:append-inner="isExpiryDateVisible = !isExpiryDateVisible"
                />
              </VCol>

              <VCol
                cols="12"
                md="6"
              >
                <!-- 👉 card CSV -->
                <VTextField
                  v-model="csv"
                  :type="isCsvVisible ? 'text' : 'password'"
                  :append-inner-icon="isCsvVisible ? 'bx-hide' : 'bx-show'"
                  label="CSV"
                  placeholder="············"
                  @click:append-inner="isCsvVisible = !isCsvVisible"
                />
              </VCol>
            </VRow>
          </VCardText>

          <!-- 👉 Action Buttons -->
          <VCardText class="d-flex flex-wrap gap-4">
            <VBtn>Add Card</VBtn>

            <VBtn
              type="reset"
              color="secondary"
              variant="tonal"
            >
              Reset
            </VBtn>
          </VCardText>
        </VForm>
      </VCard>
    </VCol>
    <!-- !SECTION -->
  </VRow>
</template>
