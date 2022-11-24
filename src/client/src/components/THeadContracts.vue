<template>
  <thead class="thead-contracts">
  <tr>
    <th
      v-for=""
    >

    </th>
<!--    <th>Тип договора</th>-->
<!--    <th>Плановые сроки</th>-->
<!--    <th>Фактические сроки </th>-->
<!--    <th>Сумма договора</th>-->
  </tr>
<!--  <tr>-->
<!--    <th>начала</th>-->
<!--    <th>окончания</th>-->
<!--    <th>начала</th>-->
<!--    <th class="secondHeader">окончания</th>-->
<!--  </tr>-->
  </thead>
</template>

<script>
export default {
  name: "THeadContracts",
  props: {
    tableHeaders: Array,
  }
}
</script>

<style scoped>

</style>