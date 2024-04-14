<script setup>
import AnalyticsEventDetails from '@/views/sales-statistics/AnalyticsEventDetails.vue'
import AnalyticsEventSales from '@/views/sales-statistics/AnalyticsEventSales.vue'
import GenerateFile from '@/views/sales-statistics/GenerateFile.vue'
import axios from 'axios';

// 👉 Images
import chart from '@images/cards/chart-success.png'
import wallet from '@images/cards/wallet-info.png'
const route = useRoute();
const router = useRouter();
const eventId = ref(route.params.eventId);
const eventDetails = ref({});
const attendance = ref('');
const sales = ref('');
const profit = ref('');

const BASE_URL = 'http://localhost:8080/event'; 

onMounted(async () => {
  if (!eventId.value) {
    console.error('No event ID provided');
    return;
  }
  
  try {
    console.log('Fetching event details');
    const response = await fetch(`${BASE_URL}/searchById/${eventId.value}`, {
      method: 'GET',
      headers: {
          'Authorization': `Bearer ${localStorage.getItem('token')}`,
        }
      }).then(response => response.json());
    console.log(response.data);
    eventDetails.value = response.data;
    const filled = eventDetails.value.filled;
    const capacity = eventDetails.value.capacity;
    const ticketPrice = eventDetails.value.ticketPrice;
    const cancelFee = eventDetails.value.cancelFee;

      attendance.value = `${(filled / capacity * 100).toFixed(2)}%`;
      sales.value = `$${(ticketPrice * filled).toFixed(2)}`;
      profit.value = `$${((ticketPrice * filled)).toFixed(2)}`;

      console.log('Attendance:', attendance.value);
      console.log('Sales:', sales.value);
      console.log('Profit:', profit.value);
  } 
  catch (error) {
    console.error('Error fetching event details:', error);
  }
});

</script>

<template>
    <VRow>
        <!-- Left Column for Event Details, Attendance, and Sales -->
        <VCol cols="12" md="8">
            <!-- Event Details -->
            <VRow>
                <VCol cols="12">
                    <AnalyticsEventDetails />
                </VCol>
            </VRow>
            
            <!-- Attendance and Sales -->
            <VRow>
                <VCol cols="12" sm="6">
                    <!-- Attendance -->
                    <CardStatisticsVertical class="no-indicator" v-bind="{
                        title: 'Attendance',
                        image: chart,
                        stats: attendance,
                    }" />
                </VCol>
                <VCol cols="12" sm="6">
                    <!-- Sales -->
                    <CardStatisticsVertical v-bind="{
                        title: 'Sales',
                        image: wallet,
                        stats: sales,

                    }" />
                </VCol>
            </VRow>

            <!-- Profit Report -->
            <VRow>
                <VCol cols="12">
                    <!-- Sales -->
                    <CardStatisticsVertical v-bind="{
                        title: 'Total Profit',
                        image: chart,
                        stats: profit,
                    }" />
                </VCol>
            </VRow>
        </VCol>

        <!-- Right Column for Generating Reports -->
        <VCol cols="12" md="4">
            <GenerateFile/>
        </VCol>
    </VRow>
</template>


