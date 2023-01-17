<template>
  <div class="modal">
    <div class="modal-open">
      <div class="edit-modal-content">
        <div class="edit-modal-header">
          <button class="edit-modal-cancel-btn" @click="$emit('close')">
            <img class="edit-modal-cancel-btn" src="../assets/icons/cancel.png" alt="" />
          </button>
        </div>
        <div class="edit-modal-header-text">Карточка {{ $props.cardHeader }}</div>

        <div class="edit-modal-info">
          <div class="edit-modal-controls">
            <button
                class="edit-modal-controls__button"
                @click="editMode ? editMode=false : editMode=true"
                v-if="!editMode"
            >
              <img src="../assets/icons/edit.png" alt="">
              <div class="controls-button__header">
                Редактировать
              </div>
            </button>
            <button class="edit-modal-controls__button save-button" v-if="editMode" @click="updateObj">
              <img src="../assets/icons/save.png" alt="">
              <div class="controls-button__header"> Сохранить</div>
            </button>
            <button
                class="edit-modal-controls__button"
                @click="removeObj"
            >
              <img src="../assets/icons/remove.png" alt="">
              <div class="controls-button__header">Удалить</div>
            </button>
          </div>
          <div
              class="edit-modal-control-warning"
              v-if="editMode"
          >После изменений нажмите "Сохранить".</div>
          <p class="invalid-feedback" v-if="!this.isValidForm">
            Пожалуйста, введите все поля
          </p>
          <div
              class="edit-fields"
          >
            <div
                class="edit-fields-element"
                v-for="(key, index) in inputElemsKeys"
                :key="index"
            >
              <div class="fields-element__title">
                {{inputElemsHeaders[index]}}:
              </div>
              <div class="fields-element__value">
                {{ $props.obj[key]}}
              </div>
              <input
                  :type="(key === 'approxBeginDate' || key === 'approxEndDate' || key === 'beginDate' || key === 'endDate')? 'date' : 'text'"
                  class="fields-element__edit"
                  v-if="editMode"
                  v-model="newObj[key]"
              >
            </div>

            <div
                class="edit-fields-element"
                v-if="mode === 'contractsCounterparty'"
            >
              <div class="fields-element__title">Организация-контрагент:</div>
              <div class="fields-element__value" id="select-counterparty__value">
                {{this.getCounterpartyName }}
              </div>
              <select
                  class="fields-element__edit"
                  v-model="newObj['counterpartyId']"
                  v-if="editMode"
              >
                <option
                    v-for="(counterparty, index) in this.counterparties"
                    :key="index"
                    :value="counterparty.id">
                  {{ counterparty.name}}
                </option>
              </select>
            </div>

            <div
                class="edit-fields-element"
                v-if="mode === 'contracts' || mode === 'contractsCounterparty'"
            >
              <div class="fields-element__title">
                Тип договора:
              </div>
              <div class="fields-element__value" id="select-contractType__value">
                {{ this.getContractType}}
              </div>
              <select
                  class="fields-element__edit"
                  v-model="newObj['contractType']"
                  v-if="editMode"
              >
                <option value="PURCHASE">Закупка</option>
                <option value="SUPPLY">Поставка</option>
                <option value="WORK">Работы</option>
              </select>
            </div>
          </div>
        </div>
        <div
            class="if-container"
            v-if="mode === 'contracts'"
        >
          <div class="edit-modal-header-container">
            <div class="edit-modal-header-text">Список этапов:</div>
          </div>
          <list-all-inserted
              :mode="'stages'"
              :inserting="{isInserted:true, openModalID: $props.obj.id}"
          ></list-all-inserted>
          <div class="edit-modal-header-container">
            <div class="edit-modal-header-text">Список договоров с контрагентами:</div>
          </div>
          <list-all-inserted
              :mode="'contractsCounterparty'"
              :inserting="{isInserted:true, openModalID: $props.obj.id}"
          ></list-all-inserted>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex"
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'
export default {
  name: 'edit-modal',
  components: {
    'list-all-inserted': ()=> import('@/pages/ListAll')
  },
  props: {
    obj: Object,
    mode: String,
    cardKeys: Array,
    cardFields: Array,
    cardHeader: String
  },
  mixins: [validationMixin],
  data() {
    return {
      editMode: false,
      newObj: null,
      isOpenAddStage: false,
      isOpenAddContractCounterparty: false,
      userForm: {},
      contractForm: {},
      contractCounterpartyForm: {},
      counterpartyForm: {},
      stageForm: {},
      isValidForm: true
    }
  },
  computed: {
    ...mapGetters(['getCounterparties']),
    counterparties(){
      return this.getCounterparties
    },
    inputElemsKeys(){
      let arr = []
      this.$props.cardKeys.forEach(key => {
        if (key !== 'contractType' && key !== 'counterpartyId')
          arr.push(key)
      })
      return arr
    },
    inputElemsHeaders(){
      let arr = []
      this.$props.cardFields.forEach(header => {
        if (header !== 'Тип договора' && header !== 'Организация-контрагент')
          arr.push(header)
      })
      return arr
    },
    getCounterpartyName() {
      let counterparty = this.counterparties.find((counterparty) => {
        if (counterparty.id === this.obj['counterpartyId'])
          return counterparty
      })
      return counterparty.name
    },
    getContractType() {
      let type = this.$props.obj['contractType']
      let res = ''
      switch (type) {
        case 'WORK':
          res = 'Работы'
          break
        case 'PURCHASE':
          res = 'Закупка'
          break
        case 'SUPPLY':
          res = 'Поставка'
          break
      }
      return res
    }
  },
  methods: {
    ...mapActions(['loadCounterparties']),
    async updateObj() {
      let url = ''
      let isValidForm = ''
      switch (this.$props.mode) {
        case 'contracts':
          url = `http://localhost:8080/api/contracts/update`
          for (let key in this.newObj) {
            this.contractForm[key] = this.newObj[key];
          }
          this.$v.contractForm.$touch()
          isValidForm = !this.$v.contractForm.$error
          break
        case 'counterparties':
          url = `http://localhost:8080/api/counterparties/${this.newObj.id}/update`
          for (let key in this.newObj) {
            this.counterpartyForm[key] = this.newObj[key];
          }
          this.$v.counterpartyForm.$touch()
          isValidForm = !this.$v.counterpartyForm.$error
          break
        case 'stages':
          url = `http://localhost:8080/api/stages/${this.newObj.id}/update`
          for (let key in this.newObj) {
            this.stageForm[key] = this.newObj[key];
          }
          this.$v.stageForm.$touch()
          isValidForm = !this.$v.stageForm.$error
          break
        case 'contractsCounterparty':
          url = `http://localhost:8080/api/contract_counterparties/${this.newObj.id}/update`
          for (let key in this.newObj) {
            this.contractCounterpartyForm[key] = this.newObj[key];
          }
          this.$v.contractCounterpartyForm.$touch()
          isValidForm = !this.$v.contractCounterpartyForm.$error
          break
        case 'users':
          url = `http://localhost:8080/api/users/${this.newObj.id}/update`
          for (let key in this.newObj) {
            this.userForm[key] = this.newObj[key];
          }
          this.$v.userForm.$touch()
          isValidForm = !this.$v.userForm.$error
          break
      }
      console.log(this.$v)
      this.isValidForm = isValidForm
      if (isValidForm) {
        console.log('Валидация прошла успешно.')
        this.editMode = false
        try {
          let response = await fetch(url, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify(this.newObj)
          })
          if (response.ok) {
            console.log(`Объект  ${this.mode} успешно отредактирован и сохранен. PUT-request отправлен. Изменения загружены в БД.`)
            this.$emit('close')
            console.log('response.ok')
          } else {
            alert("Ошибка HTTP: " + response.status);
            console.log('response NOT ok')
          }
        } catch (error) {
          console.error(error)
          console.log('ERROR FETCH')
          this.$emit('close')
        }
      } else {
        console.log('Введенные данные не прошли валидацию')
      }
    },
    async removeObj() {
      //fetch body: id
      console.log(`DELETE-request with new info about ${this.mode} object...`)
    },
  },
  validations: {
    userForm: {
      FIO: { required },
      username : { required },
      password : { required }
    },
    contractForm: {
      name: { required },
      contractType: { required },
      approxBeginDate: { required },
      approxEndDate: { required },
      beginDate: { required },
      endDate: {  required },
      sum: { required }
    },
    contractCounterpartyForm: {
      name: { required },
      contractType: { required },
      counterpartyId: { required },
      sum: { required },
      approxBeginDate: { required },
      approxEndDate: { required },
      beginDate: { required },
      endDate: {  required }
    },
    counterpartyForm: {
      name: { required },
      address: { required },
      inn: { required }
    },
    stageForm: {
      name: { required },
      approxBeginDate: { required },
      approxEndDate: { required },
      beginDate: { required },
      endDate: { required },
      sum: { required },
      approxCredit: { required },
      approxSalary: { required },
      credit: { required },
      salary: { required }
    }
  },
  created() {
    this.loadCounterparties()
    let clone = {};
    //глубокое копирование
    for (let key in this.obj) {
      clone[key] = this.obj[key];
    }
    this.newObj = clone
  },
  updated() {
    switch (this.$props.mode) {
      case 'contracts':
        this.contractForm = this.newObj
        break
      case 'counterparties':
        this.counterpartyForm = this.newObj
        break
      case 'stages':
        this.stageForm = this.newObj
        break
      case 'contractsCounterparty':
        this.contractCounterpartyForm = this.newObj
        break
      case 'users':
        this.userForm = this.newObj
        break
    }
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
.edit-modal-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 77vh;
  overflow-y:scroll;
  overflow-x: hidden;
  padding-bottom: 20px;
}
.edit-modal-header{
  display: flex;
  width: 100%;
  justify-content: flex-end;
  margin-right: 30px;
  margin-top: 25px;
}
.edit-modal-header-text{
  font-size: 22px;
  font-weight: 600;
  text-transform: uppercase;
  text-shadow: 1px 2px 3px rgba(0,0,0,0.5);
  text-align: center;
  width: 100%;
  justify-self: center;
  margin-left: 5%;
}
.edit-modal-info{
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
}
.edit-modal-info, .if-container {
  width:70%;
}
.edit-modal-cancel-btn{
  width: 30px;
  height: 30px;
  margin-top: -12px;
  margin-right: 10px;
  border: none;
  position: fixed;
}
button .edit-modal-cancel-btn > img {
  position: fixed;
}
.edit-modal-cancel-btn:hover{
  transform: translate(0, -2px);
}
.edit-modal-cancel-btn:active{
  transform: translate(1px, 0);
}
.edit-modal-content::-webkit-scrollbar {
  width: 12px;               /* ширина scrollbar */
}
.edit-modal-content::-webkit-scrollbar-track {
  background: inherit;        /* цвет дорожки */
}
.edit-modal-content::-webkit-scrollbar-thumb {
  background-color: #484848;    /* цвет плашки */
  border-radius: 20px;       /* закругления плашки */
  border: 2px solid #282828;  /* padding вокруг плашки */
  box-shadow: inset 0 0 4px rgba(0,0,0,0.4);
}
.edit-fields {
  display: grid;
  grid-row-gap: 10px;
  margin: 10px 0 0 0;
  width: 100%;
}
.edit-fields-element{
  display: flex;
  justify-content: center;
  align-items: stretch;
  word-wrap: break-word;
  width: 100%;
}
.fields-element__value:hover{
  background-color: #606060;
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
.edit-modal-controls, .edit-modal-header-container{
  display: flex;
  justify-content: flex-end;
  width: 100%;
}
.edit-modal-header-container{
  line-height: 1;
  margin-top: 20px;
}
.edit-modal-controls__button {
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
.edit-modal-controls__button:hover{
  transform: translateY(-2px);
  background-color: #808080;
}
.edit-modal-controls__button:active{
  transform: translateY(2px);
  background-color: #606060;
}
.edit-modal-controls__button > img {
  width: 30px;
}
.controls-button__header {
  display: flex;
  align-items: center;
  margin-left: 3px;
}
.edit-modal-control-warning{
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