<script setup>

import NewTicketingOfficer from '@/views/pages/add-ticketing-officer/NewTicketingOfficer.vue'
</script>

<template>
  <div>
    <VRow>
      <VCol cols="12">
        <!-- 👉 Multiple Column -->
        <VCard title="Add New Ticketing Officer">
          <VCardText>
            <NewTicketingOfficer/>
          </VCardText>
        </VCard>
      </VCol>
    </VRow>
  </div>
</template>
