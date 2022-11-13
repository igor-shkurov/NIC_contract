<template>
  <div class="header wrapper">
    <div class="header mainWindow">
      <header>
        <div class="links">
          <router-link
              :to="{ name: 'contractsList', props: { mode: 'contracts', isInserted: false} }"
              class="header-links"
          >
            Договоры
          </router-link>
          <router-link
              :to="{ name: 'counterpartiesList', props: { mode: 'counterparties', isInserted: false} }"
              class="header-links"
          >
            Организации-контрагенты
          </router-link>
          <router-link
              :to="{ name: 'reportsList' }"
              class="header-links"
          >
            Отчёты
          </router-link>
          <router-link
              :to="{ name: 'administrationList', props: { mode: 'users', isInserted: false} }"
              class="header-links"
          >
            Администрирование
          </router-link>
        </div>
        <router-link
            :to="{ name: 'auth' }"
            class="header-links log-out"
        >
          Выйти
        </router-link>
      </header>
    </div>
  </div>

</template>

<script>
export default {
  data() {
    return {
      auth: false
    }
  },
  created() {
    this.auth = localStorage.getItem('auth') !== null
  },
  methods: {
    changeUserState() {
      if (this.auth) {
        localStorage.removeItem('auth')
        this.$router.push({ name: 'auth'})
      } else {
        localStorage.setItem('auth', true)
        this.auth = true
      }
    }
  }

}
</script>

<style>
  header {
    display: flex;
    min-width: 85%;
    justify-content: space-between;
    margin-top: 20px;
    font-weight: 600;
    padding-bottom: 5px;
    border-bottom: 1px solid #454545;
  }
  .links {
    display: flex;
    justify-content: flex-start;
  }
  .header-links {
    margin-left: 20px;
    text-align: center;
  }
  .header-links:nth-child(1){
    margin-left: 0;
  }
  .header-links:hover{
    color: #ababab;
  }
  .header-links:active{
    color: #ccccccaf;
  }
  .log-out:hover {
  color: goldenrod;
  }
</style>