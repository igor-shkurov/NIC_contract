<template>
  <div class="contracts-layout">
    <main-header />
    <router-view></router-view>
  </div>
</template>

<script>
import MainHeader from '../components/MainHeader'

export default {
  components: { MainHeader },
}
</script>

<style>
  .contracts-layout {
    min-width: 1201px;
  }
</style>