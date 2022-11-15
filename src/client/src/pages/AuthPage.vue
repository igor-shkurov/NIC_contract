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
  }
}
</script>
<style>

</style>