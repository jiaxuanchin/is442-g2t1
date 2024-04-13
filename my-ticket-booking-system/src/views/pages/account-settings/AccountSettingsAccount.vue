<script setup>
import axios from 'axios';
import { ref, onMounted} from 'vue';

// Set the user_id in local storage --> To remove after testing
// localStorage.setItem('user_id', '1');

// Avatar image
import avatar1 from '@images/avatars/avatar-1.png'

const accountData = {
  avatarImg: avatar1,
  user_fname: 'john',
  user_lname: 'Doe',
  email: 'johnDoe@example.com',
  phone: '+(65) 9534-3543',
}

const refInputEl = ref()
const accountDataLocal = ref(structuredClone(accountData))
const isAccountDeactivated = ref(false)

// The method to deactivate the account
const deactivateAccount = () => {
  // To do: Perform the deactivation logic but not sure if we will do
};

const resetForm = async () => {
  try {
    const userId = localStorage.getItem('user_id'); 
    await fetchCustomerInfo(userId);
  } catch (error) {
    console.error('Error resetting form:', error);
  }
};

const changeAvatar = file => {
  const fileReader = new FileReader()
  const { files } = file.target
  if (files && files.length) {
    fileReader.readAsDataURL(files[0])
    fileReader.onload = () => {
      if (typeof fileReader.result === 'string')
        accountDataLocal.value.avatarImg = fileReader.result
    }
  }
}

// reset avatar image
const resetAvatar = () => {
  accountDataLocal.value.avatarImg = accountData.avatarImg
}

// Update the customer information from the database
const saveChanges = async () => {
  try{
    const userId = localStorage.getItem('user_id'); 
    await updateCustomerInfo(userId, accountDataLocal.value.user_fname,accountDataLocal.value.user_lname,accountDataLocal.value.email);
    await fetchCustomerInfo(userId);
    alert('Changes saved successfully!');
  } catch (error){
    console.log("Error saving changes: ", error);
    alert('Error saving changes: ' + error.message);
  }
};

// Method to fetch customer info
const fetchCustomerInfo = async (userId) => {
  try {
    const response = await axios.get(`http://localhost:8080/UserEntity/${userId}`,{
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    const customerData = response.data;
    accountDataLocal.value = { ...accountDataLocal.value, ...customerData }; // This helps to merge the existing value to the new value
  } catch (error) {
    console.error('Error fetching customer info:', error);
  }
};

// Fetch customer info when the component is mounted
onMounted(() => {
  const userId = localStorage.getItem('user_id');
  fetchCustomerInfo(userId);
});

// Method to update customer information
const updateCustomerInfo = async (userId, user_fname, user_lname, email) => {
  try {
    const response = await axios.put(`http://localhost:8080/UserEntity/update/${userId}`, null, {
      params: {
        user_fname,
        user_lname,
        email,
      },
      headers: {
        'Authorization': `Bearer ${localStorage.getItem('token')}`
      }
    });
    console.log(response.data); // Log the response from the backend
  } catch (error) {
    console.error('Error updating customer information:', error);
    throw error; // Rethrow the error to be caught by the caller
  }
};

</script>

<template>
  <VRow>
    <VCol cols="12">
      <VCard title="Account Details">
        <VCardText class="d-flex">
          <!-- 👉 Avatar -->
          <VAvatar
            rounded="lg"
            size="100"
            class="me-6"
            :image="accountDataLocal.avatarImg"
          />

          <!-- 👉 Upload Photo -->
          <form class="d-flex flex-column justify-center gap-5">
            <div class="d-flex flex-wrap gap-2">
              <VBtn
                color="primary"
                @click="refInputEl?.click()"
              >
                <VIcon
                  icon="bx-cloud-upload"
                  class="d-sm-none"
                />
                <span class="d-none d-sm-block">Upload new photo</span>
              </VBtn>

              <input
                ref="refInputEl"
                type="file"
                name="file"
                accept=".jpeg,.png,.jpg,GIF"
                hidden
                @input="changeAvatar"
              >

              <VBtn
                type="reset"
                color="error"
                variant="tonal"
                @click="resetAvatar"
              >
                <span class="d-none d-sm-block">Reset</span>
                <VIcon
                  icon="bx-refresh"
                  class="d-sm-none"
                />
              </VBtn>
            </div>

            <p class="text-body-1 mb-0">
              Allowed JPG, GIF or PNG. Max size of 800K
            </p>
          </form>
        </VCardText>

        <VDivider />

        <VCardText>
          <!-- 👉 Form -->
          <VForm class="mt-6">
            <VRow>
              <!-- 👉 First Name -->
              <VCol
                md="6"
                cols="12"
              >
                <VTextField
                  v-model="accountDataLocal.user_fname"
                  placeholder="John"
                  label="First Name"
                />
              </VCol>

              <!-- 👉 Last Name -->
              <VCol
                md="6"
                cols="12"
              >
                <VTextField
                  v-model="accountDataLocal.user_lname"
                  placeholder="Doe"
                  label="Last Name"
                />
              </VCol>

              <!-- 👉 Email -->
              <VCol
                cols="12"
                md="6"
              >
                <VTextField
                  v-model="accountDataLocal.email"
                  label="E-mail"
                  placeholder="johndoe@gmail.com"
                  type="email"
                />
              </VCol>

              <!-- 👉 Phone -->
              <!-- <VCol
                cols="12"
                md="6"
              >
                <VTextField
                  v-model="accountDataLocal.phone"
                  label="Phone Number"
                  placeholder="+(65) 9534-3543"
                />
              </VCol> -->

              <!-- 👉 Form Actions -->
              <VCol
                cols="12"
                class="d-flex flex-wrap gap-4"
              >
                <VBtn @click="saveChanges">Save changes</VBtn>

                <VBtn
                  color="secondary"
                  variant="tonal"
                  type="reset"
                  @click.prevent="resetForm"
                >
                  Reset
                </VBtn>
              </VCol>
            </VRow>
          </VForm>
        </VCardText>
      </VCard>
    </VCol>

    <VCol cols="12">
      <!-- 👉 Deactivate Account -->
      <VCard title="Deactivate Account">
        <VCardText>
          <div>
            <VCheckbox
              v-model="isAccountDeactivated"
              label="I confirm my account deactivation"
            />
          </div>

          <VBtn
            :disabled="!isAccountDeactivated"
            color="error"
            class="mt-3"
            @click="deactivateAccount"
          >
            Deactivate Account
          </VBtn>
        </VCardText>
      </VCard>
    </VCol>
  </VRow>
</template>
