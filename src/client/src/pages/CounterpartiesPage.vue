<template>
  <list-all
      :mode = this.$props.mode
  ></list-all>
</template>

<script>
import ListAll from "@/pages/ListAll";

export default {
  components: {
    'list-all': ListAll
  },
  props: {
    mode: String
  }
}
</script>