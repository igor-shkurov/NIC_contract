<template>
  <div
      :class="this.inserting.isInserted ? '' : 'wrapper'"

  >
    <div
        :class="this.inserting.isInserted ? '' : 'mainWindow'"
    >
      <div class="btns-inner">
        <button
            class="table-add-button"
            @click="isOpenFilters=!isOpenFilters"
        >
          <img src="../assets/icons/filter.png" alt="">
          <div class="table-add-button__header">{{isOpenFilters? 'Скрыть фильтры' : 'Показать фильтры'}}</div>
        </button>
        <button
            class="table-add-button"
            @click="isOpenAddModal=true"
            id="addButton"
        >
          <img src="../assets/icons/add.png" alt="">
          <div class="table-add-button__header">Добавить</div>
        </button>
      </div>
      <div class="list-all-container">
        <table-template
            :arr-data="this.arrData"
            :mode="this.mode"
            :is-open-filters="isOpenFilters"
            @openModal="openModalWindow"
            @sendHeaders="getHeaders"
            @sendContractDates="getContractDates"
        >
        </table-template>
        <div :id="`${this.inserting.isInserted ? (this.mode === 'stages' ? 'stagesEmptyList' : 'contrCountEmptyList') : 'emptyList'}` " hidden ></div>
        <div :id="`${this.inserting.isInserted ? (this.mode === 'stages' ? 'stagesFilteredNotFound' : 'contrCountFilteredNotFound') : 'filteredNotFound'}`" hidden></div>
        <edit-modal
            v-if="this.isOpenModal"
            @close="closeEditModal"
            :obj="this.openObj"
            :mode="this.mode"
            :cardKeys="this.cardKeys"
            :cardFields="this.cardFields"
            :cardHeader="this.cardHeader"
            :contractDates="this.contractDates"
            :contractDatesIntoInsertingListAll="this.$props.contractDatesIntoInsertingListAll"
        >
        </edit-modal>
        <add-modal
          v-if="isOpenAddModal"
          @close="closeAddModal"
          :mode="this.mode"
          :id="this.inserting.openModalID"
          :cardKeys="this.cardKeys"
          :cardFields="this.cardFields"
          :cardHeader="this.cardHeader"
          :contractDates="this.contractDates"
          :contractDatesIntoInsertingListAll="this.$props.contractDatesIntoInsertingListAll"
        >
        </add-modal>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import EditModal from '../components/EditModal.vue'
import TableTemplate from '../components/TableTemplate'
import AddModal from "../components/AddModal";
import {checkAdmin} from "@/mixins/isAdmin";

export default {
  name: 'ListAll',
  components: {
    'table-template':TableTemplate,
    'edit-modal': EditModal,
    'add-modal': AddModal
  },
  props: {
    mode: String,
    inserting: Object,
    contractDatesIntoInsertingListAll: Object
  },
  mixins: [checkAdmin],
  data() {
      return {
        openObj: null,
        isOpenModal: false,
        isOpenAddModal: false,
        cardFields: null,
        cardHeader:  null,
        cardKeys: null,
        isOpenFilters: false,
        contractDates: {}
      }
    },
    computed: {
      ...mapGetters(['getContracts', 'getCounterparties', 'getStages', 'getContractsCounterparty', 'getUsers']),
      contracts(){
        return this.getContracts
      },
      counterparties(){
        return this.getCounterparties
      },
      stages() {
        return this.getStages
      },
      contractsCounterparty() {
        return this.getContractsCounterparty
      },
      users() {
        return this.getUsers
      },
      arrData() {
        switch(this.mode) {
          case 'contracts':
            return this.contracts
          case 'counterparties':
            return this.counterparties
          case 'stages':
            return this.stages
          case 'contractsCounterparty':
            return this.contractsCounterparty
          case 'users':
            return this.users
        }
        return ''
      }
    },
    methods: {
      openModalWindow(obj) {
        this.openObj = obj
        this.isOpenModal = true
      },
      closeEditModal() {
        this.openObj = null
        this.isOpenModal = false
        this.loadData()
      },
      closeAddModal(){
        this.isOpenAddModal = false;
        this.loadData()
      },
      getHeaders(headers){
        this.cardFields = headers.fieldsHeaders
        this.cardHeader = headers.cardHeader
        this.cardKeys = headers.keysElemData
      },
      ...mapActions(['loadContracts', 'loadCounterparties', 'loadStages', 'loadContractsCounterparty', 'loadUsers']),
      loadData() {
        switch (this.mode){
          case 'contracts':
            this.loadContracts()
            break
          case 'counterparties':
            this.loadCounterparties()
            break
          case 'stages':
            this.loadStages(this.inserting.openModalID)
            break
          case 'contractsCounterparty':
            this.loadContractsCounterparty(this.inserting.openModalID)
            break
          case 'users':
            this.loadUsers()
            break
        }
      },
      getContractDates(objWithDates){
        let obj = this.$props.contractDatesIntoInsertingListAll
        if(Object.entries(obj).length === 0)
          this.contractDates = objWithDates
      }
    },
  created() {
    this.loadData()
  },
  mounted() {
    let val = this.getIsAdminFromLocalStorage()
    const btn = document.getElementById('addButton')
    if(!val){
      btn.disabled=true
    }
    else {
      btn.disabled = false
    }

    if(this.$props.contractDatesIntoInsertingListAll)
      this.contractDates = this.$props.contractDatesIntoInsertingListAll
  }
}
</script>

<style>
  .list-all-container {
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  table {
    width: 100%;
    table-layout: fixed;
    border-collapse: separate !important;
    border-spacing: 0;
    margin: 0px 0;
  }

  thead, tbody {
    table-layout: fixed;
    text-align: center;
  }
  tr:nth-child(2) th:first-child{border-left: 1px solid #FFF;}
  .bordered {
    border: solid #fff 1px;
    /*-moz-border-radius: 6px;*/
    /*-webkit-border-radius: 6px;*/
    border-radius: 6px;
  }
  .bordered tr:hover {
    background: slategray;
    transition: all 0.1s ease-in-out;
    box-shadow: inset 0px 0px 10px rgba(0,0,0,0.5);
  }

  .thead-contracts tr:hover, .thead-stages tr:hover, .thead-contracts-counterparty tr:hover, .thead-counterparties tr:hover {
    box-shadow: none;
  }

  .thead-contracts tr:active, .thead-stages tr:active, .thead-contracts-counterparty tr:active, .thead-counterparties tr:active {
    box-shadow: none;
  }

  .bordered tr:active{
    box-shadow: inset 0px 0px 20px rgba(0,0,0,0.5);
  }

  .bordered td, .bordered th {
    border-left: 1px solid #fff;
    border-top: 1px solid #fff;
    padding: 10px;
    text-align: center;
    word-wrap: break-word;
  }
  .bordered th {
    background-color: gray;
    text-shadow: 0 1px 0 rgba(0,0,0,0.5);
  }
  .bordered td:first-child, .bordered th:first-child {
    border-left: none;
  }
  .bordered tr:first-child th:first-child {
    border-radius: 6px 0 0 0;
  }
  .bordered tr:first-child th:last-child {
    border-radius: 0 6px 0 0;
  }
  .bordered th:only-child{
    border-radius: 6px 6px 0 0;
  }
  .bordered tr:last-child td:first-child {
    border-radius: 0 0 0 6px;
  }
  .bordered tr:last-child td:last-child {
    border-radius: 0 0 6px 0;
  }
  /*th:nth-child(1) {*/
  /*  width: 17%;*/
  /*}*/
  .table-add-button {
    align-self: flex-end;
    display: flex;
    align-items: center;
    font-size: 15px;
    font-weight: 600;
    background-color: #707070;
    border: 2px solid #454545;
    border-radius: 6px;
    padding: 3px 10px;
    margin-left: 3px;
  }
  .table-add-button:hover{
    transform: translateY(-2px);
    background-color: #808080;
  }
  .table-add-button:active{
    transform: translateY(2px);
    background-color: #606060;
  }
  .table-add-button > img {
    width: 30px;
  }
  .table-add-button__header{
    display: flex;
    align-items: center;
    margin-left: 3px;
  }
  .btns-inner {
    display: flex;
    flex-direction: row;
    justify-content: flex-end;
    width: 100%;
  }
  #addButton:disabled{
    background-color: #454545;
    color: grey;
  }
  #addButton:disabled:hover{
    transform: translateY(0px);
    cursor: default;
  }
</style>