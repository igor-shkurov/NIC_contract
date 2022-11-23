<template>
  <list-all
    :mode = this.$props.mode
    :inserting = this.$props.inserting
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
    inserting: Object
  }
}
</script>