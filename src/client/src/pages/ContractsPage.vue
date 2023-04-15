<template>
  <list-all
    :mode = this.$props.mode
    :inserting = this.$props.inserting
    :contract-dates-into-inserting-list-all = this.$props.contractDatesIntoInsertingListAll
  ></list-all>
</template>

<script>
import ListAll from "@/pages/ListAll";

export default {
  components: {
    'list-all': ListAll
  },
  props: {
    mode: String,
    inserting: Object,
    contractDatesIntoInsertingListAll: Object
  }
}
</script>