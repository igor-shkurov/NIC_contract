<template>
  <div class="wrapper">
    <div class="mainWindow">
      <div class="reports">
        <div class="reports-header">
          Отчёты
        </div>
        <div class="reports-element-container">
          <div class="reports-element-header">
            1. Вывод всех договоров за задаваемый плановый период
          </div>
          <div class="reports-element-controls">
            <form class="reports-element-form" @submit.prevent="formSubmit(1)">
              <div class="reports-element-form__field">
                <label for="approxBeginDate">Плановый срок начала:</label>
                <input
                    type="date"
                    id="approxBeginDate"
                    v-model="form1.approxBeginDate"
                >
              </div>
              <div class="reports-element-form__field">
                <label for="approxEndDate">Плановый срок окончания:</label>
                <input
                    type="date"
                    id="approxEndDate"
                    v-model="form1.approxEndDate"
                >
              </div>
              <div class="reports-element-form__button">
                <button type="submit" class="reports-element-form-button">
                  Получить ссылку на отчёт</button>
              </div>
            </form>
          </div>
          <div
              class="reports-element-link"
              v-if="isFirstLinkGot"
          >
            firstLink
          </div>
        </div>
        <div class="reports-element-container">
          <div class="reports-element-header">
            2. Вывод всех этапов для выбранного договора
          </div>
          <div class="reports-element-controls">
            <form class="reports-element-form" @submit.prevent="formSubmit(2)">
              <div class="reports-element-form__field">
                <label for="chosenContract">Выбрать договор:</label>
                <select
                    id="chosenContract"
                    name="chosenContract"
                    v-model="form2.idContract"
                >
                  <option value="1" checked>Не выбрано</option>
                </select>
              </div>
              <div class="reports-element-form__button">
                <button type="submit" class="reports-element-form-button">Получить ссылку на отчёт</button>
              </div>
            </form>
          </div>
          <div
              class="reports-element-link"
              v-if="isSecondLinkGot"
          >
            secondLink
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  name: "ReportsPage",
  data(){
    return {
      isFirstLinkGot: false,
      isSecondLinkGot: false,
      form1: {
        approxBeginDate: null,
        approxEndDate: null
      },
      form2: {
        idContract: null
      }
    }
  },
  computed: {
    ...mapGetters(['getFirstReport', "getStages"]),
    getFirstReport() {
      return this.getFirstReport
    },
    getSecondReport() {
      return this.getSecondReport
    }

  },
  methods: {
    ...mapActions(['loadFirstReport', "loadSecondReport"]),
    formSubmit(formNumber) {
      switch(formNumber) {
        case 1:
          this.loadFirstReport(this.form1.approxBeginDate, this.form1.approxEndDate)
          this.isFirstLinkGot = true
          console.log('GET-request with dates for first report...')
          break
        case 2:
          this.loadSecondReport(this.form2.idContract)
          console.log('GET-request with contract for second report...')
          this.isSecondLinkGot = true
          break
      }
    }
  }
}
</script>

<style>
  .reports{
    width: 100%;
  }
  .reports-header{
    font-size: 22px;
    font-weight: 600;
    text-transform: uppercase;
    text-shadow: 1px 2px 3px rgba(0,0,0,0.5);
    text-align: center;
    margin-top: 25px;
    margin-bottom: 15px;
  }
  .reports-element-container{
    width: 100%;
    border: solid #fff 2px;
    border-radius: 6px;
    margin-bottom: 15px;
    padding: 10px 30px;
  }
  .reports-element-header{
    font-size: 22px;
  }
  .reports-element-form{
    display: flex;
    justify-content: flex-start;
    width: 100%;
    margin-top: 10px;

  }
  .reports-element-form__field, .reports-element-form__button {
    display: flex;
    border: solid #fff 1px;
    border-radius: 6px;
    padding: 8px 10px;
    margin-right: 40px;

  }
  .reports-element-form__field > label {
    margin-right: 15px;
  }
  .reports-element-form__button{
    background-color: #454545;
  }
  .reports-element-form__button:hover{
    transform: translateY(-2px);
  }
  .reports-element-form__button:active{
    transform: translateY(2px);
  }
  .reports-element-form-button {
    border: none;
    font: inherit;
  }
  .reports-element-link {
    border-top: solid #FFF 1px;
    margin-top: 20px;
  }
</style>