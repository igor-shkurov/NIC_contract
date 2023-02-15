<template>
  <div class="wrapper">
    <div class="mainWindow">
      <sign-in />
    </div>
  </div>

</template>
<script>
import SignIn from "../components/SignIn"

export default {
  name: 'AuthPage',
  components: {
    SignIn
  },
  created() {
    localStorage.removeItem('access_token')
    localStorage.removeItem('refresh_token')
  }
}
</script>
<style>

</style>