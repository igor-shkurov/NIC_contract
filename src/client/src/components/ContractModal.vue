<template>
  <div class="modal">
    <div class="modal-open">
      <div class="contract-modal-content">
        <div class="contract-modal-header">
          <button class="contract-modal-cancel-btn" @click="$emit('close')">
            <img class="contract-modal-cancel-btn" src="../assets/icons/cancel.png" alt="" />
          </button>
        </div>
        <div class="contract-modal-header-text">Карточка {{ $props.cardHeader }}</div>

        <div class="contract-modal-info">
          <div class="contract-modal-controls">
            <button
                class="contract-modal-controls__button"
                @click="editMode ? editMode=false : editMode=true"
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
                v-for="(key, index) in inputElemsKeys"
                :key="index"
            >
              <div class="fields-element__title">
                <div class="element-title__container">
                  {{inputElemsHeaders[index]}}:
                </div>
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
                class="contract-fields-element"
                v-if="mode === 'contractsCounterparty'"
            >
              <div class="fields-element__title"><div class="element-title__container">Организация-контрагент:</div></div>
              <div class="fields-element__value">
                {{ $props.obj['counterparty']}}
              </div>
              <select
                  class="fields-element__edit select-element"
                  v-model="newObj['counterparty']"
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
                class="contract-fields-element"
                v-if="mode === 'contracts' || mode === 'contractsCounterparty'"
            >
              <div class="fields-element__title">
                <div class="element-title__container">Тип договора:
                </div>
              </div>
              <div class="fields-element__value">
                {{ $props.obj['contractType']}}
              </div>
              <select
                  class="fields-element__edit select-element"
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
          <div class="contract-modal-header-container">
            <div class="contract-modal-header-text">Список этапов:</div>
          </div>
          <list-all-inserted
              :mode="'stages'"
              :inserting="{isInserted:true, openModalID: $props.obj.id}"
          ></list-all-inserted>
          <div class="contract-modal-header-container">
            <div class="contract-modal-header-text">Список договоров с контрагентами:</div>
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
import {mapActions, mapGetters} from "vuex";

export default {
  name: 'contract-modal',
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
  data() {
    return {
      editMode: false,
      newObj: null,
      isOpenAddStage: false,
      isOpenAddContractCounterparty: false,
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
    ...mapActions(['loadCounterparties']),
    async updateObj() {
      this.editMode = false
      const values = document.getElementsByClassName('fields-element__value')
      //const editValues = document.getElementsByClassName('fields-element__edit')
      for (let i = 0; i < values.length; i++) {
          values[i].innerHTML = this.newObj[this.cardKeys[i]]
       // console.log(editValues[i])
      }
      //fetch body: newContract
      console.log(`PUT-request to update info about ${this.mode} object...`)
    },
    async removeObj() {
      //fetch body: id
      console.log(`DELETE-request with new info about ${this.mode} object...`)
    }
  },
  created() {
    this.loadCounterparties()
    let clone = {};
    for (let key in this.obj) {
      if (key === 'counterparty'){
        let id = this.counterparties.find(counterparty => {
          if (counterparty.name === this.obj[key])
            return counterparty.id
        })
        clone[key] = id
      }
      else
        clone[key] = this.obj[key];
    }
    this.newObj = clone;
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
    margin-left: 5%;
  }
  .contract-modal-info{
    margin-top: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  .contract-modal-info, .if-container {
    width:70%;
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
    grid-row-gap: 10px;
    margin: 10px 0 0 0;
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
  .contract-modal-header-container{
    line-height: 1;
    margin-top: 20px;
  }
  .contract-modal-controls__button {
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
  .contract-modal-controls__button:hover{
    transform: translateY(-2px);
    background-color: #808080;
  }
  .contract-modal-controls__button:active{
    transform: translateY(2px);
    background-color: #606060;
  }
  .contract-modal-controls__button > img {
    width: 30px;
  }
  .controls-button__header {
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