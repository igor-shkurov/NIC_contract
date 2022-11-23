<template>
  <form class="sign-in" @submit.prevent="formSubmit">
    <div class="form-container">
      <div class="form-header">{{ isSignInForm ? 'Авторизация' : 'Регистрация'}} в учётной системе:</div>
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
        <div class="form-element">
          <label for="password" class="form-element__label">Пароль:</label>
          <input
              type="password"
              class="form-element__field"
              placeholder="Password"
              v-model="form.password"
          >
        </div>
        <div class="form-element">
          <button
              type="submit"
              class="auth-sign-in"
          >{{ isSignInForm ? 'Войти' : 'Зарегистрироваться' }}
          </button>
        </div>
      </div>
      <div class="form-sign-up">
        <a
            href="#"
            @click.prevent="mode = isSignInForm ? 'signUp' : 'signIn'"
        >{{ isSignInForm ? 'Регистрация аккаунта' : 'Вернуться к авторизации' }}</a>
      </div>
    </div>
  </form>
</template>

<script>
export default {
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
  computed: {
    isSignInForm() {
      return this.mode === 'signIn'
    }
  },
  methods: {
    formSubmit() {
      if (this.isSignInForm) {
        this.signIn()
      } else {
        this.signUp()
      }
    },
    async signIn() {
      try {
        let res = await fetch('https://jsonplaceholder.typicode.com/users'/*, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          credentials: 'include',
          body: JSON.stringify({
            login: this.form.login,
            password: this.form.password
          })
        }*/)
        if(res.ok){
          console.log('???-request with the check of the user...')
          console.log('Авторизация прошла успешно')
          window.location.href='http://localhost:8081/contracts'
        } else {
          alert("Ошибка HTTP / Ошибка входа...?: " + res.status);
        }
      } catch (error) {
        console.error(error)
      }
    },
    async signUp() {
      try {
        let res = await fetch('https://jsonplaceholder.typicode.com/users')
        if(res.ok){
          console.log("POST-request with the new user's data...")
          console.log('Регистрация прошла успешно')
          alert('Регистрация прошла успешно! Вернитесь к авторизации')
        } else {
          alert("Ошибка HTTP: " + res.status);
        }
      } catch (error) {
        console.error(error)
      }
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
.form-sign-up {
  margin-top: 12px;
  text-decoration: underline;
  color: rgb(255,255,255,0.3);
}
.form-sign-up > a:hover{
  transform: translateY(-2px);
}
.form-sign-up > a:active {
  transform: translateY(1px);
}


</style>