<template>
  <div class="table-template">
    <table class="bordered">
      <thead>
        <tr>
          <th
            v-for="(header, index) in headers.fieldsHeaders"
            :key="index"
          >
            {{ header }}
          </th>
        </tr>
      </thead>
      <tbody>
          <tr
              v-for="(elemData, ind) in arrData"
              :key="ind"
              @click="openModalEvent(ind)"
          >
            <td
                v-for="(key, index) in headers.keysElemData"
                :key="index"
            >
              {{ elemData[key] }}
            </td>
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
    openModalEvent(id) {
      this.$emit('openModal', id)
    },
    getHeaders() {
      let headers = null
      let cardHeader = null
      let keysElemData = null
      switch (this.$props.mode){
        case 'contracts':
          headers = ['Название','Тип договора','Плановые сроки начала','Плановые сроки окончания','Фактические сроки начала', 'Фактические сроки окончания', 'Сумма договора']
          cardHeader = 'договора'
          keysElemData = ['name','contractType','approxBeginDate','approxEndDate','beginDate', 'endDate', 'sum']
          break
        case 'counterparties':
          headers = ['Название','Адрес','ИНН']
          cardHeader = 'контрагента'
          keysElemData = ['name','address','inn']
          break
        case 'stages':
          headers = ['Название','Плановые сроки начала','Плановые сроки окончания','Фактические сроки начала', 'Фактические сроки окончания', 'Сумма этапа', 'Плановые расходы на материалы', 'Плановые расходы на зарплату', 'Фактические расходы на материалы', 'Фактические расходы на зарплату']
          cardHeader = 'этапа'
          keysElemData =['name','approxBeginDate','approxEndDate','beginDate', 'endDate', 'sum', 'approxCredit', 'approxSalary', 'credit', 'salary']
          break
        case 'contractsCounterparty':
          headers = ['Название','Тип договора','Организация-контрагент', 'Сумма договора', 'Плановые сроки начала','Плановые сроки окончания','Фактические сроки начала', 'Фактические сроки окончания']
          cardHeader = 'договора с контрагентом'
          keysElemData = ['name','contractType','counterpartyId', 'sum', 'approxBeginDate','approxEndDate','beginDate', 'endDate']
          break
        case 'users':
          headers = ['ФИО', 'Логин', 'Пароль']
          cardHeader = 'пользователя'
          keysElemData = ['FIO', 'username', 'password']
      }
      return { fieldsHeaders: headers, cardHeader: cardHeader, keysElemData: keysElemData}
    },
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