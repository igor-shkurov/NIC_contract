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
                class="edit-modal-controls__button editButton"
                @click="changeEditMode"
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
                class="edit-modal-controls__button removeButton"
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

          <div id="validation-message"></div>
          <div v-if="this.cardHeader === 'этапа' || this.cardHeader === 'договора с контрагентом'" id="inserting-validation-message"></div>

          <div class="edit-fields">
            <div
                class="edit-fields-block"
                v-for="(key, index) in inputElemsKeys"
                :key="index"
            >

              <div class="edit-fields-element">
                <div class="fields-element__title">
                  {{inputElemsHeaders[index]}}:
                </div>
                <div class="fields-element__value">
                  {{ (key === 'approxBeginDate' || key === 'approxEndDate' || key === 'beginDate' || key === 'endDate')? getDateFormat($props.obj[key]) : $props.obj[key] }}
                </div>

                <div
                    class="fields-element__edit fieldset"
                     v-if="(key === 'approxBeginDate' || key === 'approxEndDate' || key === 'beginDate' || key === 'endDate')&&editMode&&!isOpenAddStage&&!isOpenAddContractCounterparty"
                >
                  <label v-if="(key === 'beginDate')&&!isValidBeginDate">
                    Максимальное значение {{getMaxMinDate(key)}}.
                  </label>
                  <label v-if="(key === 'endDate')&&!isValidEndDate">
                    Минимальное значение {{getMaxMinDate(key)}}.
                  </label>
                  <label v-if="(key === 'approxBeginDate')&&!isValidApproxBeginDate">
                    Максимальное значение {{getMaxMinDate(key)}}.
                  </label>
                  <label v-if="(key === 'approxEndDate')&&!isValidApproxEndDate">
                    Минимальное значение {{getMaxMinDate(key)}}.
                  </label>

                  <input
                      :id="getDateInputId(key)"
                      type="date"
                      @focusout="changeDateHandler(key)"
                      v-model="newObj[key]"
                  >
                </div>
                <input
                    v-if="!(key === 'approxBeginDate' || key === 'approxEndDate' || key === 'beginDate' || key === 'endDate')&&editMode"
                    type="text"
                    class="fields-element__edit"
                    v-model="newObj[key]"

                >

              </div>
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

            <div
                class="edit-fields-element"
                v-if="mode === 'users'"
            >
              <div class="fields-element__title">
                Роль пользователя:
              </div>
              <div class="fields-element__value" id="select-contractType__value">
                {{ this.$props.obj['role'] }}
              </div>
              <select
                  class="fields-element__edit"
                  v-model="newObj['role']"
                  v-if="editMode"
              >
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
              </select>
            </div>
          </div>
          <div class="edit-modal-controls" v-if="$props.mode === 'users'" id="changePassBtn">
            <button
                class="edit-modal-controls__button"
                @click="isOpenChangePass? isOpenChangePass=false : isOpenChangePass=true"
                v-if="!isOpenChangePass"
            >
              <img src="../assets/icons/key.png" alt="">
              <div class="controls-button__header">
                Сменить пароль
              </div>
            </button>
            <button class="edit-modal-controls__button save-button" v-if="isOpenChangePass" @click="changePassword">
              <img src="../assets/icons/key.png" alt="">
              <div class="controls-button__header"> Сохранить новый пароль</div>
            </button>
          </div>
          <div
              class="edit-modal-control-warning"
              v-if="isOpenChangePass"
          >После ввода нажмите "Сохранить новый пароль".</div>
          <div id="changePass-validation-message"></div>
          <div
              class="edit-fields"
              v-if="isOpenChangePass"
          >
            <div class="edit-fields-element">
              <div class="fields-element__title">
                Новый пароль:
              </div>
              <input
                  class="fields-element-password__edit"
                  v-model="changePassForm['password']"
                  type="password"
                  placeholder="Введите пароль"
              >
            </div>
            <div
                class="edit-fields-element"
            >
              <div class="fields-element__title">
                Подтверждение пароля:
              </div>
              <input
                  class="fields-element-password__edit"
                  v-model="changePassForm['confirmPassword']"
                  type="password"
                  placeholder="Подтвердите введенный пароль"
              >
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
              :contractDatesIntoInsertingListAll="this.$props.contractDates"
          ></list-all-inserted>
          <div class="edit-modal-header-container">
            <div class="edit-modal-header-text">Список договоров с контрагентами:</div>
          </div>
          <list-all-inserted
              :mode="'contractsCounterparty'"
              :inserting="{isInserted:true, openModalID: $props.obj.id}"
              :contractDatesIntoInsertingListAll="this.$props.contractDates"
          ></list-all-inserted>
        </div>
      </div>
    </div>

  </div>
</template>

<script>

import {mapGetters, mapActions} from "vuex"
import { checkValid} from "@/mixins/validation";
import {inputElems} from "@/mixins/chooseInputFields";
import {checkAdmin} from "@/mixins/isAdmin";
import {dateFormat} from "@/mixins/getDateFormat";
import {maxMinDates} from "@/mixins/maxMinDates";

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
    cardHeader: String,
    contractDates: Object,
    contractsDatesIntoInsertingListAll: Object
  },
  mixins: [checkValid, inputElems, checkAdmin, dateFormat, maxMinDates],
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
      isOpenChangePass: false,
      changePassForm: {}
    }
  },
  computed: {
    ...mapGetters(['getCounterparties']),
    counterparties(){
      return this.getCounterparties
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
      let host = document.location.host
      host = host.split(':')
      switch (this.$props.mode) {
        case 'contracts':
          url = `http://${host[0]}:8080/api/contracts/update`
          break
        case 'counterparties':
          url = `http://${host[0]}:8080/api/counterparties/update`
          break
        case 'stages':
          url = `http://${host[0]}:8080/api/stages/update`
          break
        case 'contractsCounterparty':
          url = `http://${host[0]}:8080/api/counterparty_contracts/update`
          break
        case 'users':
          url = `http:/${host[0]}:8080/api/users/update`
          break
      }
      this.validation()


      if(this.isValidForm&&this.isValidEndDate&&this.isValidBeginDate&&this.isValidApproxEndDate&&this.isValidApproxBeginDate){
        this.editMode = false
        try {
          let response = await fetch(url, {
            method: 'PUT',
            headers: {
              'Content-Type': 'application/json',
              'Authorization': localStorage.getItem('access_token')
            },
            body: JSON.stringify(this.newObj)
          })
          if (response.ok) {
            console.log(`Объект  ${this.mode} успешно отредактирован и сохранен.`)
            this.$emit('close')
          } else if(response.status === 403) {
            alert('Для редактирования объекта нужны права администратора.')
          } else if(this.mode==='counterparties' && response.status === 409) {
            alert('Организация-контрагент с таким ИНН уже существует.')
          } else {
            alert("Ошибка редактирования: " + response.status);
          }
        } catch (error) {
          console.error(error)
        }
      } else {
        console.log('Введенные данные не прошли валидацию')
      }
    },

    async removeObj() {
      let url =''
      let host = document.location.host
      host = host.split(':')
      let ans = true;
      switch (this.$props.mode) {
        case 'contracts':
          url = `http://${host[0]}:8080/api/contracts/delete/contract_id=${this.obj['id']}`
          ans = confirm('Вы уверены, что хотите удалить договор? Вместе с ним удалятся его этапы и договоры с контрагентами.')
          break
        case 'counterparties':
          url = `http://${host[0]}:8080/api/counterparties/delete/counterparty_id=${this.obj['id']}`
          ans = confirm('Вы уверены, что хотите удалить контрагента?')
          break
        case 'stages':
          url = `http://${host[0]}:8080/api/stages/delete/stage_id=${this.obj['id']}`
          ans = confirm('Вы уверены, что хотите удалить этап договора?')
          break
        case 'contractsCounterparty':
          url = `http://${host[0]}:8080/api/counterparty_contracts/delete/contract_id=${this.obj['id']}`
          ans = confirm('Вы уверены, что хотите удалить договор с контрагентом?')
          break
        case 'users':
          url = `http://${host[0]}:8080/api/users/delete/user_id=${this.obj['id']}`
          ans = confirm('Вы уверены, что хотите удалить пользователя? Вместе с ним удалятся все закрепленные за этим пользователем договоры.')
          break
      }
      if(ans){
        try {
          let response = await fetch(url, {
            method: 'DELETE',
            headers: {
              'Authorization': localStorage.getItem('access_token')
            }
          })
          if(response.ok) {
            console.log(`Объект ${this.mode} с id ${this.obj['id']} успешно удален.`)
            this.$emit('close')
          } else if(response.status === 403) {
            alert('Для удаления объекта нужны права администратора.')
          } else if(response.status === 409) {
            alert('Объект невозможно удалить из-за наличия привязанных к нему договоров.')
          } else {
            alert("Ошибка HTTP в удалении договора: " + response.status);
            this.$emit('close')
          }
        } catch(error) {
          console.error(error)
        }
      }
    },
    async changePassword(){
      if(this.$props.mode === 'users') {  // доп защита
        let host = document.location.host
        host = host.split(':')
        let url = `http://${host[0]}:8080/api/users/change_password`
        this.checkChangePass()

        if(this.isValidForm){
          this.isOpenChangePass = !this.isOpenChangePass
          let obj = {
            id: this.changePassForm.id,
            password: this.changePassForm.password
          }
          try {
            let response = await fetch(url, {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('access_token')
              },
              body: JSON.stringify(obj)
            })
            if (response.ok) {
              console.log(`Новый пароль для пользователя с id {${this.changePassForm['id']}} установлен.`)
              this.$emit('close')
            } else {
              alert("Ошибка установки нового пароля: " + response.status);
            }
          } catch (error) {
            console.error(error)
            this.$emit('close')
          }
        } else {
          console.log('Введенный пароль не прошел валидацию')
        }
      }
    },
    changeEditMode(){
      this.editMode = !this.editMode
      setTimeout(()=>{
        let dateInputList = ''
        if(this.mode==='contracts'|| this.mode==='counterparties' || this.mode === 'users')
          dateInputList = document.querySelectorAll("input[type='date']")
        else{
         let insertModal = document.getElementsByClassName("modal")[1]
          dateInputList = insertModal.querySelectorAll("input[type='date']")
        }
        for(let ind=0; ind<dateInputList.length; ind++){
          let node = dateInputList[ind]
          let prop = ''
          switch(node.id){
            case 'inputApproxBeginDate':
              prop = 'approxBeginDate'
              break
            case 'inputBeginDate':
              prop = 'beginDate'
              break
            case 'inputApproxEndDate':
              prop = 'approxEndDate'
              break
            case 'inputEndDate':
              prop = 'endDate'
              break
            default:
              return
          }
          this.setMaxMinDate(prop)
        }
      }, 100)


    }
  },

  created() {
    this.changePassForm['id']=this.$props.obj['id']
    if(!this.counterparties)
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
  },
  mounted() {
    let val = this.getIsAdminFromLocalStorage()
    const btns = document.getElementsByClassName('editButton')
    const btns2 = document.getElementsByClassName('removeButton')
    if(!val){
      for(let i=0; i<btns.length;i++){
        btns[i].disabled = true
      }
      for(let i=0; i<btns2.length;i++){
        btns2[i].disabled = true
      }
    }
    else {
      for(let i=0; i<btns.length;i++){
        btns[i].disabled = false
      }
      for(let i=0; i<btns2.length;i++){
        btns2[i].disabled = false
      }
    }
  }
}
</script>

<style>
.modal{
  position: fixed;
  z-index: 0;
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
  z-index: 1;
}
button .edit-modal-cancel-btn > img {
  position: fixed;
  z-index: 1;
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
.edit-fields-block{
  width: 100%;
  text-align: end;
}
.edit-fields-element{
  display: flex;
  justify-content: center;
  align-items: stretch;
  word-wrap: break-word;
  width: 100%;
}
.fields-element__value:hover, .fields-element-password__edit:hover{
  background-color: #606060;
  transition: background-color .1s ease-in;
}
.fields-element__title {
  background-color: #ababab;
  display: flex;
  justify-content: center;
  align-items: center;
  border: 4px solid #454545;
  border-radius: 6px;
  width: 20%;
  padding: 5px 5px;
  font-weight: 600;
  margin-right: 10px;
  text-align: center;
  text-shadow: 1px 0.5px 0 rgba(0,0,0,0.5);
}
.fields-element__value, .fields-element__edit, .fields-element-password__edit {
  background-color: #525252;
  transition: background-color .1s ease-in;
  border: 4px solid #454545;
  border-radius: 6px;
  width: 80%;
  padding: 5px 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  color: #FFFFFF;
}
.fields-element-password__edit {
  width: 40%;
}
.fields-element__edit {
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
  transform: translateY(0);
  transition: transform .1s ease-in;
}
.edit-modal-controls__button:hover{
  transform: translateY(-2px);
  transition: transform .1s ease-in;
  background-color: #808080;
}
.edit-modal-controls__button:active{
  transform: translateY(2px);
  transition: transform .1s ease-in;
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
#changePassBtn {
  margin-top: 15px;
}
.editButton:disabled, .removeButton:disabled{
  background-color: #454545;
  color: grey;
}
.editButton:disabled:hover, .removeButton:disabled:hover{
  transform: translateY(0px);
  transition: transform .1s ease-in;
  cursor: default;

}
.fieldset {
  display: flex;
  flex-flow: column nowrap;
  width: 41%;
  align-self: stretch;
  padding: 0 !important;
  position: relative;
  z-index: 1;
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
  z-index: 1;
  top: 2px;
  left: 11px;
  font-size: 12px;
  color: #454545;
  text-align: left;
}
.fieldset input{
  color: #FFFFFF;
  width: 100%;
  background-color: #A0A0A0;
  box-shadow: none;
  font-family: inherit;
  font-size: inherit;
  font-weight: 600;
  border: none;
  padding: 5px 10px;
  line-height: 100%;
  transition: background-color .1s ease-in;
}
.fieldset input:hover{
  background-color: #C0C0C0;
  border: none;
  transition: background-color .1s ease-in;
}
.fieldset input:focus{
  outline: none;
}
</style>