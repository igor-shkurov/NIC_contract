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

              <input
                  :type="(key === 'approxBeginDate' || key === 'approxEndDate' || key === 'beginDate' || key === 'endDate')? 'date' : 'text'"
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
                  v-model="addForm['counterparty']"
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

            </div>
          <div
              class="add-modal-warning"
              v-if="!isAllFieldsEntered"
          >
            Пожалуйста, введите все поля
          </div>
          <button class="add-button" @click="addObj">
            <div class="controls-button__header"> Добавить</div>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from "vuex";
import { validationMixin } from 'vuelidate'

export default {
  name: "AddModal",
  props: {
    mode: String,
    id: Number,
    cardKeys: Array,
    cardFields: Array,
    cardHeader: String
  },
  mixins: [validationMixin],
  data(){
    return {
      addForm: {}
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
        if (key !== 'contractType' && key !== 'counterparty')
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
    }
  },
  methods: {
    async addObj() {
      if (this.isValidForm()) {
        // валидация
        //отправляем новый объект
        console.log('Добавление...', this.addForm)
        switch (this.$props.mode) {
          case 'contracts':
            /*try {
              let response = await fetch(`http://localhost:8080/api/contracts`, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8',
                  'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify(this.addForm)
              })
              if(response.ok) {
                console.log(`Договор добавлен`)
              } else {
                alert("Ошибка HTTP в добавлении договора: " + response.status);
              }
            } catch(error) {
              console.error(error)
            }*/
            //console.log('ID: ', this.id)
            break
          case 'counterparties':
            /*try {
              let response = await fetch(`http://localhost:8080/api/counterparties`, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(this.addForm)
              })
              if(response.ok) {
                console.log(`Контрагент добавлен`)
              } else {
                alert("Ошибка HTTP в добавлении контрагента: " + response.status);
              }
            } catch(error) {
              console.error(error)
            }*/
            //console.log('ID: ', this.id)
            break
          case 'stages':
            /*try {
              let response = await fetch(`http://localhost:8080/api/stages/${this.id}`, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(this.addForm)
              })
              if(response.ok) {
                console.log(`Этап добавлен`)
              } else {
                alert("Ошибка HTTP в добавлении этапа: " + response.status);
              }
            } catch(error) {
              console.error(error)
            }*/
            console.log('ID: ', this.id)
            break
          case 'contractsCounterparty':
            /*try {
              let response = await fetch(`http://localhost:8080/api/contract_counterparties/${id}`, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(this.addForm)
              })
              if(response.ok) {
                console.log(`Договор добавлен`)
              } else {
                alert("Ошибка HTTP в добавлении договора с контрагентом: " + response.status);
              }
            } catch(error) {
              console.error(error)
            }*/
            console.log('ID: ', this.id)
            break
          case 'users':
            /*try {
              let response = await fetch(`http://localhost:8080/api/users`, {
                method: 'POST',
                headers: {
                  'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(this.addForm)
              })
              if(response.ok) {
                console.log(`Договор добавлен`)
              } else {
                alert("Ошибка HTTP в добавлении пользователя: " + response.status);
              }
            } catch(error) {
              console.error(error)
            }*/
            //console.log('ID: ', this.id)
            break
        }
        this.$emit('close')
      } else {
        console.log('Введенные данные не прошли валидацию.')
      }
    },
    ...mapActions(['loadCounterparties'])
  },
  validations: {

  },
  created() {
    this.loadCounterparties()
    this.newObj = {}
  }
}
</script>

<style scoped>
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
  margin-left: 5%;
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
}
button .add-modal-cancel-btn > img {
  position: fixed;
}
.add-modal-cancel-btn:hover{
  transform: translate(0, -2px);
}
.add-modal-cancel-btn:active{
  transform: translate(1px, 0);
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
  background-color: #707070;
  color: inherit;
  font: inherit;
  font-weight: 600;
}

.fields-element__edit:hover {
  background-color: #909090;
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
}

.add-button:hover{
  background-color: #909090;
  transform: translateY(-2px);
  box-shadow: 0 0 3px #282828;
}
.add-button:active{
  background-color: #606060;
  transform: translateY(2px);
  box-shadow: 0 0 3px #282828 inset;
}



</style>