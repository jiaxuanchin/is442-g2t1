<script setup>
import avatar1 from '@images/avatars/avatar-1.png'
import avatar2 from '@images/avatars/avatar-2.png'
import avatar3 from '@images/avatars/avatar-3.png'
import avatar4 from '@images/avatars/avatar-4.png'
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import axios from 'axios';

import pages2 from '@images/pages/2.png'
const route = useRoute();
const router = useRouter();
const eventId = ref(route.params.eventId);

const avatars = [
  avatar1,
  avatar2,
  avatar3,
  avatar4,
]

const isCardDetailsVisible = ref(false)
const BASE_URL = 'http://localhost:8080/eventmanager'; 

onMounted(() => {
  eventId.value = route.params.eventId;
  console.log(eventId.value);
});


const downloadReport = async () => {
  if (!eventId.value) {
    alert('Event ID is not available.');
    return;
  }

  try {
    const response = await axios({
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`,
      },
      url: `${BASE_URL}/statistics/${eventId.value}`,
      responseType: 'blob',
    });
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement('a');
    link.href = url;
    link.setAttribute('download', 'event-report.csv');  
    document.body.appendChild(link);
    link.click();
    link.parentNode.removeChild(link);
    // ...existing code to handle the download...
  } catch (error) {
    if (error.response && error.response.status === 403) {
      alert('You do not have permission to download this report.');
    } else if (error.response && error.response.status === 404) {
      alert('The report could not be found.');
    } else {
      alert('An error occurred while downloading the report.');
    }
    console.error('Failed to download report:', error);
  }
};

</script>

<template>
  <VRow>
    <VCol>
      <VCard>
        <VImg :src="pages2" />
        <VCardText class="position-relative">
          <div class="d-flex justify-center flex-wrap pt-8">
            <VBtn @click="downloadReport">Download report for this event</VBtn>
          </div>
        </VCardText>
      </VCard>
    </VCol>
  </VRow>
</template>


<style lang="scss" scoped>
.avatar-center {
  position: absolute;
  border: 3px solid rgb(var(--v-theme-surface));
  inset-block-start: -2rem;
  inset-inline-start: 1rem;
}

// membership pricing
.member-pricing-bg {
  position: relative;
  background-color: rgba(var(--v-theme-on-surface), var(--v-hover-opacity));
}

.membership-pricing {
  sup {
    inset-block-start: 9px;
  }
}
</style>
