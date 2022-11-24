<template>
  <div class="table-template">
    <table
        class="bordered">
      <thead>
        <tr>
          <th
              v-for="(value, index) in headers.fieldsHeaders"
              :key="index"
          >{{value}}</th>
        </tr>
      </thead>
      <tbody>
      <tr
          v-for="(elemData, ind) in arrData"
          :key="ind"
          @click="openModalEvent(elemData.id)"
      >
        <td
          v-for="(value, key, index) in dataWithoutID(elemData)"
          :key="index"
        >{{ value }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>


export default {
  name: "TableTemplate.vue",
  data() {
    return {
      headers: null,
    }
  },
  props: {
    arrData: Array,
    mode: String
  },
  methods: {
    dataWithoutID(elemData){
      let clone = {};
      for (let key in elemData) {
        if (key !== 'id'){
          clone[key] = elemData[key];
        }
      }
      return clone;
    },
    openModalEvent(id) {
      this.$emit('openModal', id)
    },
    getHeaders() {
      let headers = null
      let cardHeader = null
      switch (this.$props.mode){
        case 'contracts':
          headers = ['Название','Тип договора','Плановые сроки начала','Плановые сроки окончания','Фактические сроки начала', 'Фактические сроки окончания', 'Сумма договора']
          cardHeader = 'договора'
          break
        case 'counterparties':
          headers = ['Название','Адрес','ИНН']
          cardHeader = 'контрагента'
          break
        case 'stages':
          headers = ['Название','Плановые сроки начала','Плановые сроки окончания','Фактические сроки начала', 'Фактические сроки окончания', 'Сумма этапа', 'Плановые расходы на материалы', 'Плановые расходы на зарплату', 'Фактические расходы на материалы', 'Фактические расходы на зарплату']
          cardHeader = 'этапа'
          break
        case 'contractsCounterparty':
          headers = ['Название','Тип договора','Организация-контрагент', 'Сумма договора', 'Плановые сроки начала','Плановые сроки окончания','Фактические сроки начала', 'Фактические сроки окончания']
          cardHeader = 'договора с контрагентом'
          break
        case 'users':
          headers = ['ФИО', 'Логин', 'Пароль']
          cardHeader = 'пользователя'
      }
      return { fieldsHeaders: headers, cardHeader: cardHeader}
    }
  },
  created() {
    this.headers = this.getHeaders()
    this.$emit('sendHeaders', this.headers)
  }


}
</script>

<style>
.bordered {
  margin-top: 5px;
}
</style>