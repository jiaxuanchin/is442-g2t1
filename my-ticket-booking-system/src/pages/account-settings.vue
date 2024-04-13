<script setup>
import { useRoute } from 'vue-router'
import AccountSettingsAccount from '@/views/pages/account-settings/AccountSettingsAccount.vue'
import AccountSettingsWallet from '@/views/pages/account-settings/AccountSettingsWallet.vue'
import AccountSettingsSecurity from '@/views/pages/account-settings/AccountSettingsSecurity.vue'
import { ref, onMounted, computed } from 'vue'

const route = useRoute()
const activeTab = ref(route.params.tab)

// Define the original tabs
const originalTabs = [
  {
    title: 'Account',
    icon: 'bx-user',
    tab: 'account',
  },
  {
    title: 'Security',
    icon: 'bx-lock-open',
    tab: 'security',
  },
  {
    title: 'Wallet',
    icon: 'bx-bell',
    tab: 'wallet',
  },
]

// Define the user's role
const role = ref('')
const userId = localStorage.getItem('user_id') // Assuming userId is stored in localStorage

// Fetch user data and extract the role
const fetchUserData = async () => {
  try {
    const response = await fetch(`http://localhost:8080/UserEntity/${userId}`,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })
    const userData = await response.json()
    role.value = userData.role.name
  } catch (error) {
    console.error('Error fetching user data:', error.message)
  }
}

onMounted(fetchUserData)

const filteredTabs = computed(() => {
  if (role.value === 'customer') {
    return originalTabs
  } else {
    return originalTabs.filter(tab => tab.tab !== 'wallet')
  }
})
</script>

<template>
  <div>
    <VTabs
      v-model="activeTab"
      show-arrows
    >
      <!-- Render the filtered tabs -->
      <VTab
        v-for="item in filteredTabs"
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
      <!-- Account -->
      <VWindowItem value="account">
        <AccountSettingsAccount />
      </VWindowItem>

      <!-- Security -->
      <VWindowItem value="security">
        <AccountSettingsSecurity />
      </VWindowItem>

      <!-- Wallet - Render only if the user is a customer -->
      <VWindowItem value="wallet" v-if="role === 'customer'">
        <AccountSettingsWallet />
      </VWindowItem>
    </VWindow>
  </div>
</template>
