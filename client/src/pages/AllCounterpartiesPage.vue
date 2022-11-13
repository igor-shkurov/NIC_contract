<template>
  <table-template
    :arr-data="counterparties"
    :mode="'counterparties'"
  ></table-template>
</template>

<script>
import TableTemplate from "../components/TableTemplate";
import { mapGetters, mapActions } from "vuex";

export default {
  name: "AllCounterpartiesPage.vue",
  components: {
    'table-template': TableTemplate
  },
  computed: {
    ...mapGetters(['getCounterparties']),
    counterparties() {
      return this.getCounterparties
    }
  },
  methods: {
    ...mapActions(['loadCounterparties'])
  },
  created() {
    this.loadCounterparties()
  }
}
</script>

<style scoped>

</style>