<template>
  <form class="sign-in" @submit.prevent="signIn">
    <div class="form-container">
      <div class="form-header">Авторизация в учётной системе:</div>
      <div class="form-group-element">
        <div class="form-element">
          <label for="login" class="form-element__label">Логин:</label>
          <input
              id="login"
              class="form-element__field"
              placeholder="Login"
              v-model.trim="form.username"
          >
        </div>
        <span v-if="$v.form.username.$dirty && !$v.form.username.required">
          Введите логин
        </span>
        <div class="form-element">
          <label for="password" class="form-element__label">Пароль:</label>
          <input
              type="password"
              class="form-element__field"
              placeholder="Password"
              v-model="form.password"
          >
        </div>
        <span v-if="$v.form.password.$dirty && !$v.form.password.required">
          Введите пароль
        </span>
        <div class="form-element buttonSubmit">
          <button
              type="submit"
              class="auth-sign-in"
          >
            Войти
          </button>
        </div>
      </div>
    </div>
  </form>
</template>

<script>
import { validationMixin } from 'vuelidate'
import { required } from 'vuelidate/lib/validators'
import {mapActions, mapGetters} from "vuex";
import {checkAdmin} from "@/mixins/isAdmin";
export default {
  mixins: [validationMixin, checkAdmin],
  data() {
    return {
      mode: 'signIn',
      form: {
        username: '',
        password: ''
      },
      errors: []
    }
  },
  computed: {
    ...mapGetters(['checkAuthorized']),
    isAuthorized(){
      return this.checkAuthorized
    }
  },
  methods: {
    ...mapActions(['login']),
    isValidForm() {
      this.$v.form.$touch()
      return !this.$v.form.$error
    },

    async signIn() {
      if(this.isValidForm()){
        let formBody = [];

        for (let property in this.form) {
          let encodedKey = encodeURIComponent(property);
          let encodedValue = encodeURIComponent(this.form[property]);
          formBody.push(encodedKey + "=" + encodedValue);             // конкретно в таком формате должны прийти данные с username и password на сервер для аутентификации
        }                                                             // в другом формате аутентификация не срабатывала
        formBody = formBody.join("&");                                //
        await this.login(formBody)
        if(this.isAuthorized) {
          await this.checkAdmin()


          await this.$router.push({name: 'contractsList'});

        }

      } else {
        console.log('Введенные данные не прошли валидацию.')
      }
    }
  },
  validations: {
    form: {
      username: { required},
      password: { required }
    }
  }
  
}
</script>

<style>
.form-container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: #FFF 1px solid;
  border-radius: 3px;
  padding: 30px 30px 20px 30px;

}
.form-header{
  font-size: 30px;
  margin-bottom: 10px;
  text-align: center;
  font-weight: 500;
}
.form-group-element {
  display: flex;
  flex-direction: column;
  min-width: 270px;
}
.form-element {
  font-size: 20px;
  display: flex;
  min-width: 260px;
  justify-content: space-between;
  align-items: center;
}

.form-element__field {
  display: block;
  font-family: inherit;
  padding: 2px;
  font-size: 1rem;
  font-weight: 400;
  color: #212529;
  background-color: #fff;
  border: 1px solid #bdbdbd;
  border-radius: 0.25rem;
}
.form-element__label {
  display: block;
  margin-bottom: 0.25rem;
}

.auth-sign-in{
  border: 1px solid #FFF;
  padding: 1px 7px;
  border-radius: 3px;
  height: 30px;

}
.auth-sign-in:hover, .auth-sign-in:focus{
  background-color: rgb(0, 0, 0, 0.3);
}
.form-element:nth-child(3) {
  justify-content: center;
  margin-top: 5px;
}
.form-sign-up > a:hover{
  transform: translateY(-2px);
}
.form-sign-up > a:active {
  transform: translateY(1px);
}
.buttonSubmit {
  display: flex;
  justify-content: center;
}

</style>