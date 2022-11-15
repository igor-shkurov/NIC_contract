<template>
  <div class="table-template">
    <table class="bordered">
      <thead-stages
        v-if="mode === 'stages'"
      ></thead-stages>
      <thead-contracts-counterparty
          v-if="mode === 'contractsCounterparty'"
      ></thead-contracts-counterparty>
      <thead-counterparties
          v-if="mode === 'counterparties'"
      ></thead-counterparties>
      <thead-contracts
        v-if="mode === 'contracts'"
      >
      </thead-contracts>
      <thead-users
        v-if="mode === 'users'"
      >
      </thead-users>
      <tbody>
      <tr
          v-for="(elemData, ind) in arrData"
          :key="ind"
          @click="openModalEvent(elemData.id)"
      >
        <td
          v-for="(value, key, index) in dataWithoutID(elemData)"
          :key="index"
        >{{ value }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
import THeadStages from '../components/THeadStages'
import THeadContractsCounterparty from '../components/THeadContractsCounterparty'
import THeadCounterparties from '../components/THeadCounterparties'
import THeadContracts from "../components/THeadContracts";
import THeadUsers from "@/components/THeadUsers";

export default {
  name: "TableTemplate.vue",
  components: {
    'thead-stages': THeadStages,
    'thead-contracts-counterparty': THeadContractsCounterparty,
    'thead-counterparties': THeadCounterparties,
    'thead-contracts': THeadContracts,
    'thead-users': THeadUsers
  },
  props: {
    arrData: Array,
    mode: String
  },
  methods: {
    dataWithoutID(elemData){
      let clone = {};
      for (let key in elemData) {
        if (key !== 'id'){
          clone[key] = elemData[key];
        }
      }
      return clone;
    },
    openModalEvent(id) {
      this.$emit('openModal', id)
    }
  }


}
</script>

<style>
.bordered {
  margin-top: 5px;
}
</style>