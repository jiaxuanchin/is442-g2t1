<script setup>
import { useRoute } from 'vue-router'
import UpcomingBooking from '@/views/pages/my-bookings/UpcomingBooking.vue'
import PastBooking from '@/views/pages/my-bookings/PastBooking.vue'

const route = useRoute()
const activeTab = ref(route.params.tab)

// tabs
const tabs = [
  {
    title: 'Upcoming',
    icon: 'bx-user',
    tab: 'upcoming',
  },
  {
    title: 'Past',
    icon: 'bx-lock-open',
    tab: 'past',
  }
]
</script>

<template>
  <div>
    <VTabs
      v-model="activeTab"
      show-arrows
    >
      <VTab
        v-for="item in tabs"
        :key="item.icon"
        :value="item.tab"
      >
        <VIcon
          size="20"
          start
          :icon="item.icon"
        />
        {{ item.title }}
      </VTab>
    </VTabs>
    <VDivider />

    <VWindow
      v-model="activeTab"
      class="mt-5 disable-tab-transition"
    >
      <!-- Upcoming Booking -->
      <VWindowItem value="upcoming">
        <UpcomingBooking />
      </VWindowItem>

      <!-- Pasting Booking -->
      <VWindowItem value="past">
        <PastBooking />
      </VWindowItem>
    </VWindow>
  </div>
</template>
