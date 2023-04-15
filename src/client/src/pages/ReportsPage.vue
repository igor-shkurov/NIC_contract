<template>
  <div class="wrapper">
    <div class="mainWindow">
      <div class="reports">
        <div class="reports-header">
          Отчёты
        </div>
        <div id="validation-message"></div>

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
                    v-model="firstReportForm.approxBeginDate"
                >
              </div>
              <div class="reports-element-form__field">
                <label for="approxEndDate">Плановый срок окончания:</label>
                <input
                    type="date"
                    id="approxEndDate"
                    v-model="firstReportForm.approxEndDate"
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
              id="firstReportLink"
          >
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
                    v-model="secondReportForm.contractId"
                >
                  <option v-for="(contract, index) in contracts"
                          :key="index"
                          :value="contract.id">{{ contract.name}}</option>
                </select>
              </div>
              <div class="reports-element-form__button">
                <button type="submit" class="reports-element-form-button">Получить ссылку на отчёт</button>
              </div>
            </form>
          </div>
          <div
              class="reports-element-link"
              id="secondReportLink"
          >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import {checkValid} from "@/mixins/validation";
import {maxMinDates} from "@/mixins/maxMinDates";

export default {
  name: "ReportsPage",
  data(){
    return {
      isFirstLinkGot: false,
      isSecondLinkGot: false,
      firstReportForm: {
        approxBeginDate: null,
        approxEndDate: null
      },
      secondReportForm: {
        contractId: null
      }
    }
  },
  props: {
    mode: String
  },
  computed: {
    ...mapGetters(['getContracts']),
    contracts() {
      return this.getContracts
    }

  },
  mixins: [checkValid, maxMinDates],
  methods: {
    ...mapActions(['loadContracts']),
    formSubmit(formNumber) {
      this.formNumber = formNumber
      this.validation()
      if (this.isValidForm){
        switch(formNumber) {
          case 1:
            this.loadFirstReport(this.firstReportForm)
            break
          case 2:
            this.loadSecondReport(this.secondReportForm.contractId)
            break
        }
      } else {
        console.log('Данные для получения отчета не прошли валидацию.')
      }
    },
    async loadFirstReport(form) {
      try {
        let obj = {}
        obj.beginDate = form.approxBeginDate
        obj.endDate = form.approxEndDate
        let host = document.location.host
        host = host.split(':')
        let response = await fetch(`http://${host[0]}:8080/api/reports`, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': localStorage.getItem('access_token')
          },
          body: JSON.stringify(obj)
        })
        if(response.ok) {
          let blob = await response.blob();
          let url = window.URL.createObjectURL(blob, { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

          let a = document.createElement('a');
          a.href = url;
          a.download = "contracts.xlsx";
          a.innerHTML = 'Ссылка на скачивание отчета с договорами за указанный период.'
          const firstLinkBlock = document.getElementById('firstReportLink')
          firstLinkBlock.innerHTML=''
          firstLinkBlock.appendChild(a);

          console.log(`Договоры за плановый период ${form.approxBeginDate}-${form.approxEndDate} успешно загружены.`)
        } else {
          alert("Ошибка HTTP: " + response.status);
        }
      } catch(error) {
        console.error(error)
      }
    },
    async loadSecondReport(id) {
      try {
        let host = document.location.host
        host = host.split(':')
        let response = await fetch(`http://${host[0]}:8080/api/reports/contract_id=${id}`, {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json',
            'Authorization': `${localStorage.getItem('access_token')}`
          }
        })
        if(response.ok) {
          let blob = await response.blob();
          let url = window.URL.createObjectURL(blob, { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
          let a = document.createElement('a');
          a.href = url;
          a.download = `stages${id}.xlsx`;
          a.innerHTML = 'Ссылка на скачивание отчета с этапами для выбранного договора.'
          const secondLinkBlock = document.getElementById('secondReportLink')
          secondLinkBlock.innerHTML=''
          secondLinkBlock.appendChild(a);

          console.log(`Этапы для договора с id ${id} успешно загружены.`)
        } else {
          alert("Ошибка HTTP: " + response.status);
        }
      } catch(error) {
        console.error(error)
      }
    },
  },
  created(){
    if (!this.contracts.length)
      this.loadContracts()
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
    margin-top: 20px;
  }

  .reports-element-link > a, .reports-element-link > a:hover {
    color: goldenrod;
    text-decoration: underline;
    margin-top: 10px;
    display: block;
  }

</style>