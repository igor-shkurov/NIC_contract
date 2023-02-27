<template>

  <div class="table-template">
    <filters-module
      :cardKeys="getHeaders()['keysElemData']"
      :cardFields="getHeaders()['fieldsHeaders']"
      :mode="this.mode"
      v-if="isOpenFilters"
      @sendFilters="getFiltered"
    >
    </filters-module>
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
      <tbody id="tableBody">
          <tr
              v-for="(elemData, ind) in elemsArray"
              :key="ind"
              @click="openModalEvent(ind)"
          >
            <td
                v-for="(key, index) in headers.keysElemData"
                :key="index"
            >
              {{ returnElemDataByKey(elemData, key) }} <!-- ф-ция нужна для проверки, если нужно выдать контрагента, то выдавать его название, а не id -->
            </td>
          </tr>
      </tbody>
    </table>
  </div>
</template>

<script>

import {mapActions, mapGetters} from "vuex";
import FiltersModule from "@/components/FiltersModule";
import {filtration} from "@/mixins/filtration";

export default {
  name: "TableTemplate.vue",
  components: {
    'filters-module': FiltersModule
  },
  data() {
    return {
      headers: null,
      isFiltered: false,
      filteredData: [],
      filtersObj: {}
    }
  },
  mixins: [filtration],
  props: {
    arrData: Array,
    mode: String,
    isOpenFilters: Boolean
  },
  computed: {
    ...mapGetters(['getCounterparties']),
    counterparties(){
      return this.getCounterparties
    },
    elemsArray(){   // для отображения либо отфильтрованного, либо исходного списка (проходимся по elemsArray в шаблоне)
      return this.isFiltered? this.filteredData : this.arrData   // переменная this.filteredData заполняется в method'e getFiltered()
    }
  },
  methods: {
    ...mapActions(['loadCounterparties']),
    openModalEvent(ind) {
      this.$emit('openModal', this.elemsArray[ind])
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
          headers = ['ФИО', 'Логин', 'Роль']
          cardHeader = 'пользователя'
          keysElemData = ['FIO', 'username', 'role']
      }
      return { fieldsHeaders: headers, cardHeader: cardHeader, keysElemData: keysElemData}
    },
    returnElemDataByKey(obj, key){
      if(key === 'counterpartyId'){
        let id = obj[key]
        return this.counterparties.find((elem)=>elem.id === id).name
      }
      if(key === 'contractType'){
        switch (obj[key]){
          case 'WORK':
            return 'Работы'
          case 'SUPPLY':
            return 'Поставка'
          case 'PURCHASE':
            return 'Закупка'
        }
      }
      return obj[key]
    },
    getFiltered(filtersObj) {
      if(!filtersObj) { // if filtersObj === null then RESET FILTERS
        this.isFiltered = false
        this.filtersObj =  {}
        this.filteredData = this.arrData
      } else {
        this.filtersObj =  filtersObj
        let inputKeys = ['name', 'FIO', 'address', 'inn', 'username']
        let typeKeys = ['contractType', 'counterpartyId']
        let fromKeys = {'sum_from':'sum', 'approxCredit_from':'approxCredit', 'approxSalary_from':'approxSalary', 'credit_from':'credit', 'salary_from':'salary'}
        let toKeys = {'sum_to':'sum', 'approxCredit_to':'approxCredit', 'approxSalary_to':'approxSalary', 'credit_to':'credit', 'salary_to':'salary'}
        this.filteredData = this.arrData
        for(let key in filtersObj) {

          if(filtersObj[key]){

            if(inputKeys.indexOf(key) !== -1) {
              this.filteredData = this.getFilteredByInputSearch(this.filteredData, key, filtersObj[key])
            }
            if (typeKeys.indexOf(key) !== -1){
              this.filteredData = this.getFilteredByType(this.filteredData, key, filtersObj[key])
            }
            if (fromKeys[key]) {
              this.filteredData = this.getFilteredByFrom(this.filteredData, fromKeys[key], filtersObj[key])
            }
            if (toKeys[key]) {
              this.filteredData = this.getFilteredByTo(this.filteredData, toKeys[key], filtersObj[key])
            }
            this.isFiltered = true
          }
        }
      }
    }
  },
  watch: {
    arrData: function (){
      this.getFiltered(this.filtersObj)
    },
    filteredData: function () {   // отображение сообщения о том, что результаты по заданным фильтрам не найдены
      const tr = document.getElementById('filteredResultsNotFound')
      const body = document.getElementById('tableBody')

      if (this.filteredData.length === 0 && !tr){
        const tr = document.createElement('tr')
        const td = document.createElement('td')
        td.innerHTML = 'По заданным фильтрам результатов не найдено'
        td.colSpan=this.getHeaders().fieldsHeaders.length
        tr.id = 'filteredResultsNotFound'
        tr.appendChild(td)
        body.appendChild(tr)
      } else if(this.filteredData.length !== 0 && tr){
        body.removeChild(tr)
      }
    }
  },
  created() {
    this.headers = this.getHeaders()
    this.$emit('sendHeaders', this.headers)
    if(this.counterparties && this.counterparties.length === 0) {
      this.loadCounterparties()
    }
  }
}
</script>

<style>
.bordered {
  margin-top: 5px;
}
#filteredResultsNotFound {
  font-size: 16px;
  font-style: italic;
  text-align: center;
  width: 100%;
  padding: 10px;
}
</style>