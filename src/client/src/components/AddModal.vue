<template>
  <div class="modal">
    <div class="add-modal">
      <div class="add-modal-content">
        <div class="add-modal-header">
          <button class="add-modal-cancel-btn" @click="$emit('close')">
            <img class="add-modal-cancel-btn" src="../assets/icons/cancel.png" alt="" />
          </button>
        </div>
        <div class="add-modal-header-text">Карточка {{ this.$props.cardHeader }}</div>

        <div class="add-modal-info">
          <div class="add-fields">

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
export default {
  name: "AddModal",
  props: {
    fieldsHeaders: Array,
    cardHeader: String,
  },
  data(){
    return {
      addForm: {

      }
    }
  },
  methods: {
    addObj(){

    }
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
  grid-template-rows: repeat(8, 1fr);
  grid-row-gap: 10px;
  margin: 10px 0 0 0;
  width: 100%;
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
.fields-element__edit {
  border: 4px solid #454545;
  border-radius: 6px;
  padding: 5px 10px;
  display: flex;
  justify-content: center;
  align-items: center;
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

</style>