<template>
  <div class="wrapper">
    <div class="mainWindow">
      <sign-in />
    </div>
  </div>

</template>
<script>
import SignIn from "../components/SignIn"
import {mapActions} from 'vuex'

export default {
  name: 'AuthPage',
  components: {
    SignIn
  },
  methods: {
    ...mapActions(['logout']),
  },
  created() {
    localStorage.removeItem('access_token')
    localStorage.removeItem('refresh_token')
    this.logout()
  }
}
</script>
<style>

</style>