<script setup>
import avatar1 from '@images/avatars/pic1.png'
import axios from 'axios';

let userRole = "Customer"; // Default value
let userName = "";

// Function to fetch user data and update userRole
const fetchUserData = async () => {
  try {
    const userId = localStorage.getItem('user_id'); // Get the user ID from local storage
    console.log(userId)
    const response = await axios.get(`http://localhost:8080/UserEntity/${userId}`, {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    const userData = response.data;
    userRole = userData.role.name;
    userName = userData.user_fname + " " + userData.user_lname
  } catch (error) {
    console.error('Error fetching user data:', error);
  }
};

fetchUserData();

// logout
import { useRouter } from 'vue-router'

const router = useRouter()

const logout = async () => {
  try {
    await axios.get('http://localhost:8080/api/auth/logout', {
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    })

    // Clear client-side storage (jwt token and user id)
    localStorage.removeItem('token')
    localStorage.removeItem('user_id')

    // Redirect to login page
    router.push('/login')

  } catch (error) {
    console.error("Logout failed:", error)

  }
}

</script>

<template>
  <VBadge
    dot
    location="bottom right"
    offset-x="3"
    offset-y="3"
    color="success"
    bordered
  >
    <VAvatar
      class="cursor-pointer"
      color="primary"
      variant="tonal"
    >
      <VImg :src="avatar1" />

      <!-- SECTION Menu -->
      <VMenu
        activator="parent"
        width="230"
        location="bottom end"
        offset="14px"
      >
        <VList>
          <!-- 👉 User Avatar & Name -->
          <VListItem>
            <template #prepend>
              <VListItemAction start>
                <VBadge
                  dot
                  location="bottom right"
                  offset-x="3"
                  offset-y="3"
                  color="success"
                >
                  <VAvatar
                    color="primary"
                    variant="tonal"
                  >
                    <VImg :src="avatar1" />
                  </VAvatar>
                </VBadge>
              </VListItemAction>
            </template>

            <VListItemTitle class="font-weight-semibold">
              {{userName}}
            </VListItemTitle>
            <VListItemSubtitle>{{userRole}}</VListItemSubtitle>
          </VListItem>
          <VDivider class="my-2" />

          <!-- 👉 Profile -->
          <VListItem :to="'/account-settings'">
            <template #prepend>
              <VIcon
                class="me-2"
                icon="bx-user"
                size="22"
              />
            </template>

            <VListItemTitle>Profile</VListItemTitle>
          </VListItem>

          <!-- Divider -->
          <VDivider class="my-2" />

          <!-- 👉 Logout -->
          <VListItem @click="logout">
            <template #prepend>
              <VIcon
                class="me-2"
                icon="bx-log-out"
                size="22"
              />
            </template>

            <VListItemTitle>Logout</VListItemTitle>
          </VListItem>
        </VList>
      </VMenu>
      <!-- !SECTION -->
    </VAvatar>
  </VBadge>
</template>
