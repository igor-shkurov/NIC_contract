<template>
  <div class="modal">
    <div class="add-modal">
      <div class="add-modal-content">
        <div class="add-modal-header">
          <button class="add-modal-cancel-btn" @click="$emit('close')">
            <img class="add-modal-cancel-btn" src="../assets/icons/cancel.png" alt="" />
          </button>
        </div>
        <div class="add-modal-header-text">Добавление {{ this.cardHeader }}</div>
        <button class="add-button" @click="addObj">
          <div class="controls-button__header"> Добавить</div>
        </button>
        <div id="validation-message"></div>
        <div v-if="this.cardHeader === 'этапа' || this.cardHeader === 'договора с контрагентом'" id="inserting-validation-message"></div>

        <div class="add-modal-info">
          <div class="add-fields">
            <div
                class="add-fields-element"
                v-for="(key, index) in inputElemsKeys"
                :key="index"
            >
              <div
                  class="fields-element__title"
              >
                  {{inputElemsHeaders[index]}}:
              </div>
              <div
                  class="fields-element__edit fieldset"
                  v-if="(key === 'approxBeginDate' || key === 'approxEndDate' || key === 'beginDate' || key === 'endDate')"
              >
                <label v-if="(key === 'beginDate')&&!isValidBeginDate">
                  {{ getMaxMinDate(key)? 'Максимальное значение '+getMaxMinDate(key) : '' }}
                </label>
                <label v-if="(key === 'endDate')&&!isValidEndDate">
                  {{ getMaxMinDate(key)? 'Минимальное значение'+getMaxMinDate(key) : '' }}
                </label>
                <label v-if="(key === 'approxBeginDate')&&!isValidApproxBeginDate">
                  {{ getMaxMinDate(key)? 'Максимальное значение'+getMaxMinDate(key) : '' }}
                </label>
                <label v-if="(key === 'approxEndDate')&&!isValidApproxEndDate">
                  {{ getMaxMinDate(key)? 'Минимальное значение'+getMaxMinDate(key) : '' }}
                </label>

                <input
                    :id="getDateInputId(key)"
                    type="date"
                    v-model="addForm[key]"
                    @focusout="changeDateHandler(key)"
                >
              </div>
              <input
                  v-if="!(key === 'approxBeginDate' || key === 'approxEndDate' || key === 'beginDate' || key === 'endDate')"
                  type="text"

                  class="fields-element__edit"
                  v-model="addForm[key]"
              >
            </div>
            <div
                class="add-fields-element"
                v-if="mode === 'contractsCounterparty'"
            >
              <div class="fields-element__title">Организация-контрагент</div>
              <select
                  class="fields-element__edit select-element"
                  v-model="addForm['counterpartyId']"
              >
                <option
                    v-for="(counterparty, index) in this.counterparties"
                    :key="index"
                    :value="counterparty.id">
                  {{counterparty.name}}
                </option>
              </select>
            </div>

            <div
                class="add-fields-element"
                v-if="mode === 'contracts' || mode === 'contractsCounterparty'"
            >
              <div class="fields-element__title">Тип договора</div>

                <select
                    class="fields-element__edit select-element"
                    v-model="addForm['contractType']"
                >
                  <option value="PURCHASE">Закупка</option>
                  <option value="SUPPLY">Поставка</option>
                  <option value="WORK">Работы</option>
                </select>
            </div>
            <div
                class="add-fields-element"
                v-if="mode === 'users'"
            >
              <div class="fields-element__title">
                Роль пользователя:
              </div>
              <select
                  class="fields-element__edit"
                  v-model="addForm['role']"
              >
                <option value='USER'>USER</option>
                <option value='ADMIN'>ADMIN</option>
              </select>
            </div>
            <div
                class="add-fields-element"
                v-if="mode === 'users'"
            >
              <div class="fields-element__title">
                Пароль:
              </div>
              <input
                  class="fields-element__edit"
                  v-model="addForm['password']"
                  type="password"
              >
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters} from "vuex";
import {checkValid} from "@/mixins/validation";
import {inputElems} from "@/mixins/chooseInputFields";
import {maxMinDates} from "@/mixins/maxMinDates";
import {dateFormat} from "@/mixins/getDateFormat";

export default {
  name: "AddModal",
  props: {
    mode: String,
    id: Number,   // id договора, у которого добавляется этап. null, если добавление не во вложенный список
    cardKeys: Array,
    cardFields: Array,
    cardHeader: String,
    contractDates: Object,
    contractsDatesIntoInsertingListAll: Object
  },
  mixins: [checkValid, inputElems, maxMinDates, dateFormat],
  data(){
    return {
      addForm: {},
      userForm: {},
      contractForm: {},
      contractCounterpartyForm: {},
      counterpartyForm: {},
      stageForm: {}
    }
  },
  computed: {
    ...mapGetters(['getCounterparties']),
    counterparties(){
      return this.getCounterparties
    }
  },
  methods: {
    async addObj() {

      let url = ''
      let host = document.location.host
      host = host.split(':')
      switch (this.$props.mode) {
        case 'contracts':
          url = `http://${host[0]}:8080/api/contracts/add`
          break
        case 'counterparties':
          url = `http://${host[0]}:8080/api/counterparties/add`
          break
        case 'stages':
          url = url = `http://${host[0]}:8080/api/stages/add`
          this.addForm['contractId']=this.id
          break
        case 'contractsCounterparty':
          url = `http://${host[0]}:8080/api/counterparty_contracts/add`
          this.addForm['contractId']=this.id
          break
        case 'users':
          url = `http://${host[0]}:8080/api/users/add`
          break
      }

      if(this.$props.mode === 'users')
        this.checkAddForm()
      else
        this.validation()

      if (this.isValidForm && this.isValidEndDate&&this.isValidBeginDate&&this.isValidApproxEndDate&&this.isValidApproxBeginDate) {
        try {
          let response = await fetch(url, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('access_token')
            },
            body: JSON.stringify(this.addForm)
          })
          if(response.ok) {
            console.log(`Добавление в ${this.$props.mode}...`)
            this.$emit('close')
          } else if(response.status === 403) {
            alert('Для добавления объекта нужны права администратора.')
          } else if(this.mode==='counterparties' && response.status === 409) {
            alert('Организация-контрагент с таким ИНН уже существует.')
          } else if(this.mode==='contractsCounterparty' && response.status === 304) {
            alert('Невозможно выбрать данную организацию-КА. Возможно, она была удалена или изменена.')
          } else {
            alert("Ошибка HTTP в добавлении: " + response.status);
          }
        } catch(error) {
          console.error(error)
        }
      } else {
        console.log('Введенные данные не прошли валидацию.')
      }
    },
    ...mapActions(['loadCounterparties'])
  },
  created() {
    if(!this.counterparties)
      this.loadCounterparties()
  },
  updated() {
    switch (this.$props.mode) {
      case 'contracts':
        this.contractForm = this.addForm
        break
      case 'counterparties':
        this.counterpartyForm = this.addForm
        break
      case 'stages':
        this.stageForm = this.addForm
        break
      case 'contractsCounterparty':
        this.contractCounterpartyForm  = this.addForm
        break
      case 'users':
        this.userForm = this.addForm
        break
    }
  }
}
</script>

<style scoped>
.modal{
  position: fixed;
  z-index: 2;
  display: block;
  top: 10vh;
  left:10vw;
  width: 80%;
  height: 80%;
  background-color: #545453;
  border: 1px solid #FFF;
  border-radius: 6px;
  box-shadow: inset 0 0 10px rgba(0,0,0,0.5);
  min-width: 750px;
}

.add-modal-content {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 77vh;
  overflow-y:scroll;
  overflow-x: hidden;
  padding-bottom: 20px;

}
.add-modal-header{
  display: flex;
  width: 100%;
  justify-content: flex-end;
  margin-right: 30px;
  margin-top: 25px;
}
.add-modal-header-text{
  font-size: 22px;
  font-weight: 600;
  text-transform: uppercase;
  text-shadow: 1px 2px 3px rgba(0,0,0,0.5);
  text-align: center;
  width: 100%;
  justify-self: center;
  /*margin-left: 5%;*/
}
.add-modal-info{
  width:70%;
  margin-top: 10px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 0 10%;
  min-width: 810px;
}
.add-modal-cancel-btn{
  width: 30px;
  height: 30px;
  margin-top: -12px;
  margin-right: 10px;
  border: none;
  position: fixed;
  z-index: 3;
  transform: translate(0, 0);
  transition: transform .1s ease-in;
}
button .add-modal-cancel-btn > img {
  position: fixed;
  z-index: 3;
}
.add-modal-cancel-btn:hover{
  transform: translate(0, -2px);
  transition: transform .1s ease-in;
}
.add-modal-cancel-btn:active{
  transform: translate(1px, 0);
  transition: transform .1s ease-in;
}
.add-modal-content::-webkit-scrollbar {
  width: 12px;               /* ширина scrollbar */
}
.add-modal-content::-webkit-scrollbar-track {
  background: inherit;        /* цвет дорожки */
}
.add-modal-content::-webkit-scrollbar-thumb {
  background-color: #484848;    /* цвет плашки */
  border-radius: 20px;       /* закругления плашки */
  border: 2px solid #282828;  /* padding вокруг плашки */
  box-shadow: inset 0 0 4px rgba(0,0,0,0.4);
}
.add-fields {
  display: grid;
  grid-row-gap: 10px;
  margin: 15px 0;
  width: 100%;
}
.add-fields-element{
  display: flex;
  justify-items: center;
  width: 100%;
}

.fields-element__title{
  background-color: #ababab;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 4px solid #454545;
  border-radius: 6px;
  width: 30%;
  padding: 5px 10px;
  font-weight: 600;
  margin-right: 10px;
  text-align: center;
  text-shadow: 1px 0.5px 0 rgba(0,0,0,0.5);
}
.fields-element__edit {
  border: 4px solid #454545;
  border-radius: 6px;
  padding: 5px 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 70%;
  margin-left: 10px;
  background-color: #909090;
  color: inherit;
  font: inherit;
  font-weight: 600;
  transition: background-color .1s ease-in;
}

.fields-element__edit:hover {
  background-color: #707070;
  transition: background-color .1s ease-in;
}
.add-button{
  display: flex;
  align-items: center;
  font-size: 15px;
  font-weight: 600;
  background-color: #707070;
  border: 2px solid #454545;
  border-radius: 6px;
  padding: 10px 30px;
  transform: translateY(0);
  transition: transform .1s ease-in;
  margin: 15px 0 0 0;
}

.add-button:hover{
  background-color: #909090;
  transform: translateY(-2px);
  box-shadow: 0 0 3px #282828;
  transition: transform .1s ease-in;
}
.add-button:active{
  background-color: #606060;
  transform: translateY(2px);
  transition: transform .1s ease-in;
  box-shadow: 0 0 3px #282828 inset;
}

.fieldset {
  display: flex;
  flex-flow: column nowrap;
  width: 70%;
  align-self: stretch;
  padding: 0 !important;
  position: relative;
  z-index: 3;
  transition: background-color .1s ease-in;
}
.fieldset:hover input{
  background-color: #C0C0C0;
  transition: background-color .1s ease-in;
}
.fieldset label:hover {
  pointer-events: none;
}

.fieldset label {
  position: absolute;
  z-index: 3;
  top: 2px;
  left: 11px;
  font-size: 12px;
  color: #454545;
  text-align: left;
}
.fieldset input{
  color: #FFFFFF;
  width: 100%;
  background-color: #909090;
  box-shadow: none;
  font-family: inherit;
  font-size: inherit;
  font-weight: 600;
  border: none;
  padding: 12px 10px;
  line-height: 100%;
  transition: background-color .1s ease-in;
}
.fieldset input:hover{
  background-color: #707070;
  border: none;
  transition: background-color .1s ease-in;
}
.fieldset input:focus{
  outline: none;
}
.newInvalidMsg{
  margin: 10px 0 0 0;
}

</style>