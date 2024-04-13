<script setup>
import { useTheme } from 'vuetify'
import { onBeforeMount } from 'vue'
import VerticalNavSectionTitle from '@/@layouts/components/VerticalNavSectionTitle.vue'
import VerticalNavLayout from '@layouts/components/VerticalNavLayout.vue'
import VerticalNavLink from '@layouts/components/VerticalNavLink.vue'
import axios from 'axios';

localStorage.setItem('user_id', '3');
console.log(localStorage.getItem('user_id'));


// Components
import Footer from '@/layouts/components/Footer.vue'
import NavbarThemeSwitcher from '@/layouts/components/NavbarThemeSwitcher.vue'
import UserProfile from '@/layouts/components/UserProfile.vue'

const vuetifyTheme = useTheme()

const upgradeBanner = computed(() => {
  return vuetifyTheme.global.name.value === 'light' ? upgradeBannerLight : upgradeBannerDark
})

let userRole = ""; // Default value
// Function to fetch user data and update userRole
const fetchUserRole = async () => {
  try {
    const userId = localStorage.getItem('user_id'); // Get the user ID from local storage
    const response = await axios.get(`http://localhost:8080/UserEntity/${userId}`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    const userData = response.data; 
    userRole = userData.role.name;
    console.log(userRole)
  } catch (error) {
    console.error('Error fetching user role:', error);
  }
};

// Call fetchUserRole function before mounting the component
onBeforeMount(fetchUserRole);

</script>

<template>
  <VerticalNavLayout>
    <!-- 👉 navbar -->
    <template #navbar="{ toggleVerticalOverlayNavActive }">
      <div class="d-flex h-100 align-center">
        <!-- 👉 Vertical nav toggle in overlay mode -->
        <IconBtn
          class="ms-n3 d-lg-none"
          @click="toggleVerticalOverlayNavActive(true)"
        >
          <VIcon icon="bx-menu" />
        </IconBtn>

        <!-- 👉 Search -->
        <div
          class="d-flex align-center cursor-pointer"
          style="user-select: none;"
        >
          <!-- 👉 Search Trigger button -->
          <IconBtn>
            <VIcon icon="bx-search" />
          </IconBtn>

          <span class="d-none d-md-flex align-center text-disabled">
            <span class="me-3">Search</span>
            <span class="meta-key">&#8984;K</span>
          </span>
        </div>

        <VSpacer />

        <NavbarThemeSwitcher class="me-2" />

        <UserProfile />
      </div>
    </template>

    <template #vertical-nav-content> 
      <VerticalNavLink
        :item="{
          title: 'Home',
          icon: 'bx-home',
          to: '/home',
        }"
      />
      
      <template v-if="userRole === 'customer'">
      <VerticalNavLink
        :item="{
          title: 'My Booking',
          icon: 'mdi-account-cog-outline',
          to: '/my-bookings',
        }"
      />
      </template>

      <template v-if="userRole === 'ticketing_officer'">
      <VerticalNavLink
        :item="{
          title: 'Ticket Management',
          icon: 'mdi-account-cog-outline',
          to: '/ticketing-officer',
        }"
      />
      </template>

      <template v-if="userRole === 'event_manager'">
      <VerticalNavLink
        :item="{
          title: 'User Management',
          icon: 'mdi-account-cog-outline',
          to: '/add-ticketing-officer',
        }"
      />
      <VerticalNavLink
        :item="{
          title: 'Event Management',
          icon: 'mdi-account-cog-outline',
          to: '/event-management',
        }"
      />
      </template>
      
    </template>

    <!-- 👉 Pages -->
    <slot />

    <!-- 👉 Footer -->
    <template #footer>
      <Footer />
    </template>
  </VerticalNavLayout>
</template>

<style lang="scss" scoped>
.meta-key {
  border: thin solid rgba(var(--v-border-color), var(--v-border-opacity));
  border-radius: 6px;
  block-size: 1.5625rem;
  line-height: 1.3125rem;
  padding-block: 0.125rem;
  padding-inline: 0.25rem;
}
</style>
