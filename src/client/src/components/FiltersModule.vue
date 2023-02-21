<template>
  <div class="filters-module">
    <div class="module-header">Фильтры:</div>
    <form class="filter-form" @submit.prevent="showFiltered()">
      <div class="filter-fields">
        <div class="filter-elem-inner"
             v-if="$props.mode === 'contractsCounterparty'"
        >

        </div>
        <div class="filter-elem-inner"
             v-for="(key, index) in inputElemsKeys"
             :key="index"
        >
          <div
              class="filter-elem__header"
          >
            {{inputElemsHeaders[index]}}:
          </div>
          <div
              class="filter-elem__field"
              v-if="key === 'approxBeginDate' || key === 'approxEndDate' || key === 'beginDate' || key === 'endDate'"
          >
            <label for="from">от:</label>
            <input
                :type="'date'"
                class="filter-elem-field__input"
                v-model="filters[`${key}_from`]"
                name="from"
            >
            <label for="to">&emsp;до:</label>
            <input
                :type="'date'"
                class="filter-elem-field__input"
                v-model="filters[`${key}_to`]"
                name="to"
            >
          </div>
          <div
              class="filter-elem__field"
              v-if="key === 'sum' || key === 'credit' || key === 'salary' || key === 'approxCredit' || key === 'approxSalary'"
          >
            <input
                :type="'text'"
                class="filter-elem-field__input"
                v-model="filters[`${key}_from`]"
                placeholder="От"
            >
            <input
                :type="'text'"
                class="filter-elem-field__input"
                v-model="filters[`${key}_to`]"
                placeholder="До"
            >
          </div>
          <div
              class="filter-elem__field"
              v-if="key === 'name' || key === 'address' || key === 'inn' || key === 'FIO' || key === 'username'"
          >
            <input
                :type="'text'"
                class="filter-elem-field__input"
                v-model="filters[key]"
                placeholder="Название"
            >
          </div>
        </div>
      </div>
      <div class="filter-controls">
        <button
            class="filter-controls-button"
            type="submit"
        >
          Применить
        </button>
        <button
            class="filter-controls-button"
        >
          Сбросить
        </button>
      </div>
      NOTE: Фильтрация - UI продуман, реализация - в разработке, поэтому кнопки пока нерабочие.
    </form>
  </div>
</template>

<script>
import {inputElems} from "@/mixins/chooseInputFields";
import {mapActions, mapGetters} from "vuex";

export default {
  name: "filters-module",
  props: {
    cardKeys: Array,
    cardFields: Array
  },
  mixins: [inputElems],
  data(){
    return {
      filterHeaders: this.$props.cardFields,
      filterKeys: this.$props.cardKeys,
      filters: {}
    }
  },
  computed: {
    ...mapGetters(['getCounterparties']),
    counterparties(){
      return this.getCounterparties
    }
  },
  methods: {
    showFiltered() {
      console.log('hey, i show filtered')
    },
    ...mapActions(['loadCounterparties'])
  },
  created() {
    if(!this.counterparties)
      this.loadCounterparties()
  }
}
</script>

<style scoped>
  .filters-module{
    width: 100%;
    border: solid #fff 1px;
    border-radius: 6px;
    margin-top: 5px;
    padding: 10px 30px;
  }
  .module-header {
    font-weight: bold;
    font-size: 20px;
    color: #EFEFEF;
    text-shadow: 2px 2px 3px rgba(0,0,0,0.4);
  }
  .filter-fields {
    margin-top: 10px;
    display: flex;
    flex-direction: column;
  }
  .filter-elem-inner {
    display: flex;
    width: 100%;
    margin-bottom: 5px;
  }
  .filter-elem__header {
    width: 21%;
    justify-content: flex-start;
  }
  .filter-elem__field {
    width: 78%;
    display: flex;
    margin-left: 10px;
  }
  .filter-elem-field__input {
    background-color: #929292;
    border: 4px solid #454545;
    border-radius: 6px;
    padding: 5px 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #FFFFFF;
    margin-left: 10px;
    width: 260px;
  }

  .filter-elem__field, .filter-elem__header {
    display: flex;
    align-items: center;
  }

  .filter-controls {
    display: flex;
    flex-direction: row;
    margin-top: 5px;
  }
  .filter-controls-button {
    display: flex;
    align-items: center;
    font-size: 15px;
    font-weight: 600;
    background-color: #707070;
    border: 2px solid #454545;
    border-radius: 6px;
    padding: 3px 25px;
    margin-right: 3px;
  }
  .filter-controls-button:nth-child(1) {
    background-color: #A0A0A0;
  }
  button:hover {
    transform: translateY(-2px);
  }
  button:active {
    transform: translateY(0px);
  }
</style>