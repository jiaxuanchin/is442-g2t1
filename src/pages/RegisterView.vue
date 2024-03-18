<template>
    <div>
      <h2>Register</h2>
      <form @submit.prevent="register">
        <div>
          <label for="email">Email:</label>
          <input id="email" type="email" v-model="user.email" required>
        </div>
        <div>
          <label for="password">Password:</label>
          <input id="password" type="password" v-model="user.password" required>
        </div>
        <button type="submit">Register</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        user: {
          email: '',
          password: ''
        }
      };
    },
    methods: {
      register() {
        // Dispatch action to register the user
        this.$store.dispatch('registerUser', this.user).then(() => {
          this.$router.push('/dashboard');
        }).catch(error => {
          console.error('Registration error:', error);
        });
      }
    }
  };
  </script>
  