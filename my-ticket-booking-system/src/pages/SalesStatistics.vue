<template>
  <div class="sales-statistics">
    <h1>Sales Statistics</h1>
    <table>
      <thead>
        <tr>
          <th>Event Name</th>
          <th>Tickets Sold</th>
          <th>Revenue Generated</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="stat in statistics" :key="stat.eventName">
          <td>{{ stat.eventName }}</td>
          <td>{{ stat.ticketsSold }}</td>
          <td>{{ `$${stat.revenueGenerated.toFixed(2)}` }}</td>
        </tr>
      </tbody>
    </table>
    <button @click="fetchStatistics">Refresh Statistics</button>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      statistics: [],
    };
  },
  methods: {
    fetchStatistics() {
      // replace API
      axios
        .get("/api/sales-statistics")
        .then((response) => {
          this.statistics = response.data;
        })
        .catch((error) => {
          console.error("Error fetching statistics:", error);
        });

      this.statistics = [
        // Dummy data for illustration
        { eventName: "Concert A", ticketsSold: 120, revenueGenerated: 3600 },
        { eventName: "Theatre B", ticketsSold: 80, revenueGenerated: 2400 },
      ];
    },
  },
  // Fetch statistics when component is created
  created() {
    this.fetchStatistics();
  },
};
</script>

<style scoped>
.sales-statistics {
  max-width: 800px;
  margin: auto;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 1rem;
}

th,
td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}

button {
  padding: 0.5rem 1rem;
  background-color: #3498db;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #2980b9;
}
</style>
