<template>
  <div
      :class="this.inserting.isInserted ? '' : 'wrapper'"

  >
    <div
        :class="this.inserting.isInserted ? '' : 'mainWindow'"
    >
      <table-template
        :arr-data="this.getArrData()"
        :mode="this.mode"
        @openModal="openModalWindow"
      >
      </table-template>
      <contract-modal
        v-if="isOpenModal"
        @close="closeModalWindow"
        :obj="this.openObj"
        :mode="this.mode"
      >
      </contract-modal>
    </div>
  </div>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'
import ContractModal from '../components/ContractModal.vue'
import TableTemplate from '../components/TableTemplate'

export default {
  name: 'ListAll',
  components: {
    TableTemplate,
    'contract-modal': ContractModal
  },
  props: {
    mode: String,
    inserting: Object
  },
  data() {
      return {
        arrData: null,
        isOpenModal: false,
        openObj: null
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
      }
    },
    methods: {
      openModalWindow(id) {
        this.openObj = this.arrData[id-1]
        this.isOpenModal = true
      },
      closeModalWindow() {
        this.openObj = null
        this.isOpenModal = false
      },
      ...mapActions(['loadContracts', 'loadCounterparties', 'loadStages', 'loadContractsCounterparty', 'loadUsers']),
      getArrData() {
        let data = null
        switch(this.mode) {
          case 'contracts':
            data = this.contracts
            break
          case 'counterparties':
            data = this.counterparties
            break
          case 'stages':
            data = this.stages
            break
          case 'contractsCounterparty':
            data = this.contractsCounterparty
            break
          case 'users':
            data=this.users
            break
        }
        this.arrData = data
        return data
      }
    },
  mounted() {
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
  }
}
</script>

<style>
  table {
    width: 100%;
    table-layout: fixed;
    border-collapse: separate !important;
    border-spacing: 0;
    margin: 30px 0;
  }

  thead, tbody {
    table-layout: fixed;
    text-align: center;
  }
  tr:nth-child(2) th:first-child{border-left: 1px solid #FFF;}
  .bordered {
    border: solid #fff 1px;
    -moz-border-radius: 6px;
    -webkit-border-radius: 6px;
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
  th:nth-child(1) {
    width: 17%;
  }
</style>