<script setup>
// import AnalyticsCongratulations from '@/views/dashboard/AnalyticsCongratulations.vue'
// import AnalyticsProfitReport from '@/views/dashboard/AnalyticsProfitReport.vue'

import AnalyticsEventDetails from '@/views/sales-statistics/AnalyticsEventDetails.vue'
// import AnalyticsEventAttendance from '@/views/sales-statistics/AnalyticsEventAttendance.vue'
import AnalyticsEventSales from '@/views/sales-statistics/AnalyticsEventSales.vue'
import GenerateFile from '@/views/sales-statistics/GenerateFile.vue'

// 👉 Images
import chart from '@images/cards/chart-success.png'
import wallet from '@images/cards/wallet-info.png'
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
                    <CardStatisticsVertical v-bind="{
                        title: 'Attendance',
                        image: chart,
                        stats: '60%',
                        change: 72.80,
                    }" />
                </VCol>
                <VCol cols="12" sm="6">
                    <!-- Sales -->
                    <CardStatisticsVertical v-bind="{
                        title: 'Sales',
                        image: wallet,
                        stats: '$4,679',
                        change: 28.42,
                    }" />
                </VCol>
            </VRow>

            <!-- Profit Report -->
            <VRow>
                <VCol cols="12">
                    <AnalyticsEventSales />
                </VCol>
            </VRow>
        </VCol>

        <!-- Right Column for Generating Reports -->
        <VCol cols="12" md="4">
            <GenerateFile/>
        </VCol>
    </VRow>
</template>
