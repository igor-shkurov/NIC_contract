<template>
  <div class="add-modal">
      <div class="add-modal-content">
        <div class="add-modal-header">
          <button class="add-modal-cancel-btn" @click="$emit('close')">
            <img class="add-modal-cancel-btn" src="../assets/icons/cancel.png" alt="" />
          </button>
        </div>
        <div class="add-modal-header-text">Карточка {{ this.cardHeader }}</div>

        <div class="add-modal-info">
          <div class="add-modal-controls">
            <button class="add-modal-controls__button save-button" v-if="addMode" @click="addObj">
              <img src="../assets/icons/save.png" alt="">
              <div class="controls-button__header"> Добавить</div>
            </button>
          </div>
          <div
              class="contract-modal-control-warning"
          >После ввода полей не забудьте нажать "Добавить".</div>
          <div
              class="contract-fields"
          >
          </div>
        </div>
      </div>
    </div>
</template>

<script>
export default {
  name: "AddModal"
}
</script>

<style scoped>

</style>