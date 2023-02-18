<template>
  <div class="wrapper" >
    <div class="mainWindow" id="errorWindow">
      <p class="error-header">403 Forbidden</p>
      <p>Для доступа к этой странице нужны права администратора.</p>
    </div>
  </div>

</template>
<script>

export default {
  name: 'NotAuthorized'
}
</script>
<style>
  .error-header {
    font-weight: 700;
    font-size: 25px;
  }
  #errorWindow {
    min-height: 20vh;
  }
</style>