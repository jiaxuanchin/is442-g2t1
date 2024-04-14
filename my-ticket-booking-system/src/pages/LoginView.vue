<template>
    <div>
      <h2>Login</h2>
      <form @submit.prevent="login">
        <div>
          <label for="email">Email:</label>
          <input id="email" type="email" v-model="credentials.email" required>
        </div>
        <div>
          <label for="password">Password:</label>
          <input id="password" type="password" v-model="credentials.password" required>
        </div>
        <button type="submit">Login</button>
      </form>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {
        credentials: {
          email: '',
          password: ''
        }
      };
    },
    methods: {
      login() {
        // Dispatch action to login the user
        this.$store.dispatch('login', this.credentials).then(() => {
          this.$router.push('/login');
        }).catch(error => {
          console.error('Login error:', error);
        });
      }
    }
  };
  </script>
  