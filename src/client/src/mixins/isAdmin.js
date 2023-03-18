export const checkAdmin = {
    data() {
        return {
            isAdmin: false
        }
    },
    methods: {
        async checkAdmin(){
              try{
                  let response = await fetch('http://host.docker.internal:8080/api/users/is_admin', {
                      method: 'GET',
                      headers: {
                          'Authorization': localStorage.getItem('access_token')
                      }
                  })
                  if (response.ok) {
                      this.isAdmin = true
                  } else if(response.status === 403) {
                      this.isAdmin = false
                  } else {
                      alert("Ошибка запроса: " + response.status);
                  }
              }
              catch (error) {
                  console.error(error)
              }

              if(!this.isAdmin)
                localStorage.setItem('is_admin', 'false')
              else
                localStorage.setItem('is_admin', 'true')

        },
        getIsAdminFromLocalStorage() {
            let res = localStorage.getItem('is_admin')
            if(this.$props.mode === 'counterparties'){
                if(res === 'true')
                    return true
                else if (res === 'false')
                    return false
            } else
                return true

        },
        printAccessMessage(){
            alert('Эта функция доступна только для админа')
        }
    }
}
