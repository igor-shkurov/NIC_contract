<template>
  <list-all
    :mode = this.$props.mode
    :is-inserted = this.$props.isInserted
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
    isInserted: Boolean
  }
}
</script>