<template>
  <form action="http://localhost:8081/contracts" class="sign-in" @submit.prevent="signIn">
    <div class="form-container">
      <div class="form-header">Авторизация в учётной системе:</div>
      <div class="form-group-element">
        <div class="form-element">
          <label for="login" class="form-element__label">Логин:</label>
          <input
              id="login"
              class="form-element__field"
              placeholder="Login"
              v-model.trim="form.login"
          >
        </div>
        <p v-if="$v.form.login.$dirty && !$v.form.login.required" class="invalid-feedback sign-in">
          Введите логин
        </p>
        <div class="form-element">
          <label for="password" class="form-element__label">Пароль:</label>
          <input
              type="password"
              class="form-element__field"
              placeholder="Password"
              v-model="form.password"
          >
        </div>
        <p v-if="$v.form.password.$dirty && !$v.form.password.required" class="invalid-feedback">
          Введите пароль
        </p>
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
export default {
  mixins: [validationMixin],
  data() {
    return {
      mode: 'signIn',
      form: {
        login: '',
        password: ''
      },
      errors: []
    }
  },
  methods: {
    isValidForm() {
      this.$v.form.$touch()
      return !this.$v.form.$error
    },
    async signIn() {
      if(this.isValidForm()){
        try {
          let res = await fetch('http://localhost:8080/login', {
            method: 'POST',
            headers: {
              'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: JSON.stringify({
              login: this.form.login,
              password: this.form.password
            })
        })
          if(res.ok){
            //console.log(res)
            //const token = await res.json()
            //console.log(token)
            console.log('???-request with the check of the user...')
            //console.log(token)
            alert("!")
            console.log('Авторизация прошла успешно')
            window.location.href='http://localhost:8081/contracts'
          } else {
            alert("Неверный юзер: " + res.status);
          }
        } catch (error) {
          console.error(error)
        }
      } else {
        console.log('Введенные данные не прошли валидацию.')
      }
    }
  },
  validations: {
    form: {
      login: { required},
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