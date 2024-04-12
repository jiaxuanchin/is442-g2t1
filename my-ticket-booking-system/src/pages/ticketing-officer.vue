<template>
  <div class="ticketing-officer">
    <div class="tabs">
      <div
        class="tab"
        :class="{ active: currentTab === 'verify' }"
        @click="currentTab = 'verify'"
      >
        Verify Ticket
      </div>
      <div
        class="tab"
        :class="{ active: currentTab === 'sales' }"
        @click="currentTab = 'sales'"
      >
        On-Site Sales
      </div>
    </div>

    <div class="tab-content">
      <verify-ticket-form v-if="currentTab === 'verify'"></verify-ticket-form>
      <onsite-sales-form v-if="currentTab === 'sales'"></onsite-sales-form>
    </div>
  </div>
</template>

<script>
import VerifyTicketForm from "./ticketing-verification.vue";
import OnsiteSalesForm from "./ticketing-sales.vue";

export default {
  components: {
    VerifyTicketForm,
    OnsiteSalesForm,
  },
  data() {
    return {
      currentTab: "verify",
    };
  },
};
</script>
<style scoped>
.event-management {
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: #f0f2f5;
}

.tabs {
  display: flex;
  width: 100%;
}

.tab {
  flex-grow: 1;
  text-align: center;
  padding: 20px 0;
  cursor: pointer;
  background-color: #fff;
  transition: background-color 0.3s ease;
  font-size: 18px;
  color: #555;
  border-bottom: 3px solid transparent;
}

.tab:hover {
  background-color: #e9ebee;
}

.tab.active {
  color: #000;
  font-weight: bold;
  border-color: #4caf50;
}

.tab-content {
  width: 100%;
  padding: 30px;
  background-color: #fff;
  border: 1px solid #ccc;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}
</style>
