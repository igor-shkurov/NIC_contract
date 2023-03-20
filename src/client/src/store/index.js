import Vue from 'vue'
import Vuex from 'vuex'
import router from '../router/index.js'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        contracts: [],
        stages: [],
        counterparties: [],
        contractsCounterparty: [],
        users: [],
        cardHeader: [],
        isAuthorized: !!(localStorage.getItem('access_token'))
    },
    getters: {
        getContracts(state) {
            return state.contracts
        },
        getStages(state) {
            return state.stages
        },
        getCounterparties(state) {
            return state.counterparties
        },
        getContractsCounterparty(state) {
            return state.contractsCounterparty
        },
        getUsers(state){
            return state.users
        },
        getCardHeader(state) {
            return state.cardHeader
        },
        checkAuthorized(state) {
            return state.isAuthorized
        }
    },
    mutations: {
        SET_CONTRACTS(state, payload){
            state.contracts = payload
        },
        SET_STAGES(state, payload) {
            state.stages = payload
        },
        SET_COUNTERPARTIES(state, payload) {
            state.counterparties = payload
        },
        SET_CONTRACTS_COUNTERPARTY(state, payload) {
            state.contractsCounterparty = payload
        },
        SET_USERS(state, payload) {
            state.users = payload
        },
        SET_CARD_HEADER(state, payload) {
            state.cardHeader = payload
        },
        SET_AUTHORIZED(state, payload) {
            state.isAuthorized = payload
        },
        UNAUTHORIZE(state) {
            state.contracts = null
            state.users = null
            state.stages = null
            state.counterparties = null
            state.contractsCounterparty = null
            state.isAuthorized = false
        }
    },
    actions: {
        loadCardHeader({ commit }, mode) {
            let text = null
            switch (mode){
                case 'contracts':
                    text = 'договора'
                    break
                case 'counterparties':
                    text = 'контрагента'
                    break
                case 'stages':
                    text = 'этапа'
                    break
                case 'contractsCounterparty':
                    text = 'договора с контрагентом'
                    break
                case 'users':
                    text = 'пользователя'
                    break
            }
            commit("SET_CARD_HEADER", text)
        },
        async loadContracts({commit}) {
            try {
                let host = document.location.host
                host = host.split(':')
                let response = await fetch( `http://${host[0]}:8080/api/contracts`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                });
                if(response.ok){
                    const contracts = await response.json();
                    commit("SET_CONTRACTS", contracts)
                    console.log('Договоры загружены успешно.')
                } else if (response.status === 401) {
                    await router.push({name: 'auth'})
                } else {
                    console.log("Ошибка HTTP: " + response.status);
                }
            } catch (error) {
                console.error(error)
            }
        },
        async loadStages({commit}, id) {
            try {
                let host = document.location.host
                host = host.split(':')
                let response = await fetch( `http://${host[0]}:8080/api/stages/contract_id=${id}`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                });
                if(response.ok) {
                    const stages = await response.json();
                    commit("SET_STAGES", stages)
                    console.log(`Этапы для договора №${id} загружены успешно.`)
                } else if (response.status === 401) {
                    await router.push({name: 'auth'})
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async loadContractsCounterparty({commit}, id) {
            try {
                let host = document.location.host
                host = host.split(':')
                let response = await fetch( `http://${host[0]}:8080/api/counterparty_contracts/contract_id=${id}`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                });
                if(response.ok) {
                    const contractsCounterparty = await response.json();
                    commit("SET_CONTRACTS_COUNTERPARTY", contractsCounterparty)
                    console.log(`Договоры контрагентов для договора №${id} загружены успешно.`)
                } else if (response.status === 401) {
                    await router.push({name: 'auth'})
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async loadCounterparties({commit}) {
            try {
                let host = document.location.host
                host = host.split(':')
                let response = await fetch( `http://${host[0]}:8080/api/counterparties`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                });
                if(response.ok) {
                    const counterparties = await response.json();
                    commit("SET_COUNTERPARTIES", counterparties)
                    console.log(`Перечень контрагентов успешно загружен.`)
                } else if (response.status === 401) {
                    await router.push({name: 'auth'})
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async loadUsers({commit}) {
            try {
                let host = document.location.host
                host = host.split(':')
                let response = await fetch( `http://${host[0]}:8080/api/users`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                })
                if(response.ok) {
                    const users = await response.json();
                    commit("SET_USERS", users)
                    console.log(`Пользователи успешно загружены.`)
                } else if (response.status === 401) {
                    await router.push({name: 'auth'})
                } else if (response.status === 403) {
                    await router.push({name: 'administrationForbidden'})
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async login({commit}, formBody) {
            try {
                let host = document.location.host
                host = host.split(':')
                let res = await fetch( `http://${host[0]}:8080/login`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    },
                    body: formBody
                })
                if (res.ok) {
                    let obj = await res.json()  // тут токены
                    localStorage.setItem('access_token', `Bearer ${obj['access_token']}`)
                    localStorage.setItem('refresh_token', obj['refresh_token'])
                    commit('SET_AUTHORIZED', true)
                    console.log('Авторизация прошла успешно')
                } else {
                    alert("Неверный юзер: " + res.status);
                    localStorage.setItem('access_token', '')
                    localStorage.setItem('refresh_token', '')
                    commit('SET_AUTHORIZED', false)
                    console.log('Авторизация не прошла')
                }
            } catch (error) {
                console.error(error)
                localStorage.setItem('access_token', '')
                localStorage.setItem('refresh_token', '')
                commit('SET_AUTHORIZED', false)
            }
        },
        logout({commit}){
            commit('UNAUTHORIZE')
        }
    }
})