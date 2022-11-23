<template>
  <div class="modal">
    <div class="modal-open">
      <div class="contract-modal-content">
        <div class="contract-modal-header">
          <button class="contract-modal-cancel-btn" @click="$emit('close')">
            <img class="contract-modal-cancel-btn" src="../assets/icons/cancel.png" alt="" />
          </button>
        </div>
        <div class="contract-modal-header-text">Карточка {{ this.cardHeader }}</div>

        <div class="contract-modal-info">
          <div class="contract-modal-controls">
            <button
                class="contract-modal-controls__button"
                @click="editMode ? editMode=false : editMode=true; idInputDisabled();"
                v-if="!editMode"
            >
              <img src="../assets/icons/edit.png" alt="">
              <div class="controls-button__header">
                Редактировать
              </div>
            </button>
            <button class="contract-modal-controls__button save-button" v-if="editMode" @click="updateObj">
              <img src="../assets/icons/save.png" alt="">
              <div class="controls-button__header"> Сохранить</div>
            </button>
            <button
                class="contract-modal-controls__button"
                @click="removeObj"
            >
              <img src="../assets/icons/remove.png" alt="">
              <div class="controls-button__header">Удалить</div>
            </button>
          </div>
          <div
              class="contract-modal-control-warning"
              v-if="editMode"
          >После изменений нажмите "Сохранить".</div>
          <div
              class="contract-fields"
          >
            <div
                class="contract-fields-element"
                v-for="(value, key, index) in obj"
                :key="index"
            >
                <div class="fields-element__title">
                  <div class="element-title__container">
                    {{fieldsHeaders[index]}}:
                  </div>
                </div>
              <div
                  class="fields-element__value"
                  :class="editMode ? 'noHover' : ''"
              >{{ value }}</div>
                <input
                    type="text"
                    class="fields-element__edit"
                    v-if="editMode"
                    v-model="newObj[key]"
                >
            </div>
          </div>
          <div
              class="if-container"
              v-if="mode === 'contracts'"
          >
            <div class="contract-modal-header-container">
              <div class="contract-modal-header-text">Список этапов:</div>
              <button
                  class="contract-modal-header-button"
                  @click="isOpenAddStage=true"
              >
                <img src="../assets/icons/add.png" alt="">
                <div class="contract-modal-header-button__header">Добавить</div>
              </button>
            </div>

            <list-all-inserted
                :mode="'stages'"
                :is-inserted="true"
            ></list-all-inserted>
            <div class="contract-modal-header-container">
              <div class="contract-modal-header-text">Список договоров с контрагентами:</div>
              <button
                  class="contract-modal-header-button"
                  @click="isOpenAddStage=true"
              >
                <img src="../assets/icons/add.png" alt="">
                <div class="contract-modal-header-button__header">Добавить</div>
              </button>
            </div>
            <list-all-inserted
                :mode="'contractsCounterparty'"
                :is-inserted="true"
            ></list-all-inserted>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
export default {
  name: 'contract-modal',
  components: {
    'list-all-inserted': ()=> import('@/pages/ListAll')
  },
  props: {
    obj: Object,
    mode: String
  },
  computed: {
    ...mapGetters(['getStages', 'getCounterparties', 'getContractsCounterparty', 'getUsers']),
    stages() {
      return this.getStages
    },
    counterparties() {
      return this.getCounterparties
    },
    contractsCounterparty() {
      return this.getContractsCounterparty
    },
    users() {
      return this.getUsers
    }
  },
  data() {
    return {
      editMode: false,
      newObj: null,
      fieldsHeaders: null,
      cardHeader: null,
      isOpenAddStage: false,
      isOpenAddContractCounterparty: false,
    }
  },
  methods: {
    idInputDisabled() {
      setTimeout(() => {
        const idInput = document.getElementsByClassName('fields-element__edit').item(0)
        idInput.setAttribute('disabled', 'true')
        idInput.style = 'background-color: #525252; font-weight: 400; color: #ababab'
      }, 0.000001)
    },
    getCardHeader() {
      let text = null
      switch (this.$props.mode){
        case 'contracts':
          text = 'договора'
          break
        case 'counterparties':
          text = 'контрагента'
          break
        case 'stages':
          text = 'этапа'
          break
        case 'contractsCounterparty':
          text = 'договора с контрагентом'
          break
        case 'users':
          text = 'пользователя'
          break
      }
      return text
    },
    getFieldsHeaders() {
      let headers = null
      switch (this.$props.mode){
        case 'contracts':
          headers = ['ID','Название','Тип договора','Плановые сроки начала','Плановые сроки окончания','Фактические сроки начала', 'Фактические сроки окончания', 'Сумма договора']
          break
        case 'counterparties':
          headers = ['ID','Название','Адрес','ИНН']
          break
        case 'stages':
          headers = ['ID','Название','Плановые сроки начала','Плановые сроки окончания','Фактические сроки начала', 'Фактические сроки окончания', 'Сумма этапа', 'Расходы на материалы', 'Расходы на зарплату']
          break
        case 'contractsCounterparty':
          headers = ['ID','Название','Тип договора','Организация-контрагент', 'Сумма договора', 'Плановые сроки начала','Плановые сроки окончания','Фактические сроки начала', 'Фактические сроки окончания', 'Сумма договора']
          break
        case 'users':
          headers = ['Логин', 'Пароль']
      }
      return headers
    },
    async updateObj() {
      this.editMode = false
      const values = document.getElementsByClassName('fields-element__value')
      for (let i = 0; i < values.length; i++) {
        values[i].innerHTML = this.newObj[Object.keys(this.obj)[i]]
      }
      //fetch body: newContract
      console.log(`PUT-request to update info about ${this.mode} object...`)
    },
    async removeObj() {
      //fetch body: id
      console.log(`DELETE-request with new info about ${this.mode} object...`)
    },
    ...mapActions(['loadStages', 'loadContractsCounterparty'])
  },
  created() {
    this.loadStages(this.obj.id)
    this.loadContractsCounterparty(this.obj.id)

    let clone = {};
    for (let key in this.obj) {
      clone[key] = this.obj[key];
    }
    this.newObj = clone;

    this.cardHeader = this.getCardHeader()
    this.fieldsHeaders = this.getFieldsHeaders()
  }

}
</script>

<style>
  .modal{
    position: fixed;
    display: block;
    top: 10vh;
    left:10vw;
    width: 80%;
    height: 80%;
    background-color: #545453;
    border: 1px solid #FFF;
    border-radius: 6px;
    box-shadow: inset 0 0 10px rgba(0,0,0,0.5);
  }

  .contract-modal-content {
    display: flex;
    flex-direction: column;
    align-items: center;
    height: 77vh;
    overflow-y:scroll;
    overflow-x: hidden;
    padding-bottom: 20px;

  }
  .contract-modal-header{
    display: flex;
    width: 100%;
    justify-content: flex-end;
    margin-right: 30px;
    margin-top: 25px;
  }
  .contract-modal-header-text{
    font-size: 22px;
    font-weight: 600;
    text-transform: uppercase;
    text-shadow: 1px 2px 3px rgba(0,0,0,0.5);
    text-align: center;
    width: 100%;
    justify-self: center;
    margin-left: 10%;
  }
  .contract-modal-info{
    width:70%;
    margin-top: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .contract-modal-cancel-btn{
    width: 30px;
    height: 30px;
    margin-top: -12px;
    margin-right: 10px;
    border: none;
    position: fixed;
  }
  button .contract-modal-cancel-btn > img {
    position: fixed;
  }
  .contract-modal-cancel-btn:hover{
    transform: translate(0, -2px);
  }
  .contract-modal-cancel-btn:active{
    transform: translate(1px, 0);
  }
  .contract-modal-content::-webkit-scrollbar {
    width: 12px;               /* ширина scrollbar */
  }
  .contract-modal-content::-webkit-scrollbar-track {
    background: inherit;        /* цвет дорожки */
  }
  .contract-modal-content::-webkit-scrollbar-thumb {
    background-color: #484848;    /* цвет плашки */
    border-radius: 20px;       /* закругления плашки */
    border: 2px solid #282828;  /* padding вокруг плашки */
    box-shadow: inset 0 0 4px rgba(0,0,0,0.4);
  }
  .contract-fields {
    display: grid;
    grid-template-rows: repeat(8, 1fr);
    grid-row-gap: 10px;
    margin: 10px;
    width: 100%;
  }
  .contract-fields-element{
    display: flex;
    justify-content: center;
    align-items: stretch;
    word-wrap: break-word;
    width: 100%;
  }
  .fields-element__value:hover{
    background-color: #606060;
  }
  .noHover{
    pointer-events: none;
    color: #ababab;
  }

  .fields-element__title{
    background-color: #ababab;
    display: flex;
    justify-content: center;
    align-items: center;
    border: 4px solid #454545;
    border-radius: 6px;
    width: 20%;
    padding: 5px 0;
    font-weight: 600;
    margin-right: 10px;
    text-align: center;
    text-shadow: 1px 0.5px 0 rgba(0,0,0,0.5);
  }
  .fields-element__value, .fields-element__edit {
    background-color: #525252;
    border: 4px solid #454545;
    border-radius: 6px;
    width: 80%;
    padding: 5px 10px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .fields-element__edit{
    width: 40%;
    margin-left: 10px;
    background-color: #A0A0A0;
    color: inherit;
    font: inherit;
    font-weight: 600;
  }

  .fields-element__edit:hover {
    background-color: #C0C0C0;
  }

  .contract-modal-controls, .contract-modal-header-container{
    display: flex;
    justify-content: flex-end;
    width: 100%;
  }
  .contract-modal-controls__button, .contract-modal-header-button {
    display: flex;
    align-items: center;
    font-size: 15px;
    font-weight: 600;
    background-color: #707070;
    border: 2px solid #454545;
    border-radius: 6px;
    margin-left: 5px;
    padding: 3px 10px;
  }
  .contract-modal-controls__button:hover, .contract-modal-header-button:hover{
    transform: translateY(-2px);
    background-color: #808080;
  }
  .contract-modal-controls__button:active, .contract-modal-header-button:active{
    transform: translateY(2px);
    background-color: #606060;
  }
  .contract-modal-controls__button > img, .contract-modal-header-button >img {
    width: 30px;
  }
  .controls-button__header, .contract-modal-header-button__header {
    display: flex;
    align-items: center;
    margin-left: 3px;
  }
  .contract-modal-control-warning{
    color: #bdbdbd;
    align-self: flex-end;
    margin-top: 5px;
    text-align: right;
  }
   .save-button {
    justify-self: center;
   }
   .save-button button {
     border: none;
     text-align: center;
     font: inherit;
     padding: 5px 10px;
   }

</style>