import Vue from 'vue'
import Vuex from 'vuex'

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
                let response = await fetch(`http://localhost:8080/api/contracts`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                });
                if(response.ok){
                    const contracts = await response.json();
                    commit("SET_CONTRACTS", contracts)
                    console.log('Договоры загружены успешно.')
                } else {
                    console.log("Ошибка HTTP: " + response.status);
                }
            } catch (error) {
                console.error(error)
            }
        },
        async loadStages({commit}, id) {
            try {
                let response = await fetch(`http://localhost:8080/api/stages/contract_id=${id}`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                });
                if(response.ok) {
                    const stages = await response.json();
                    commit("SET_STAGES", stages)
                    console.log(`Этапы для договора №${id} загружены успешно.`)
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async loadContractsCounterparty({commit}, id) {
            try {
                let response = await fetch(`http://localhost:8080/api/counterparty_contracts/contract_id=${id}`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                });
                if(response.ok) {
                    const contractsCounterparty = await response.json();
                    commit("SET_CONTRACTS_COUNTERPARTY", contractsCounterparty)
                    console.log(`Договоры контрагентов для договора №${id} загружены успешно.`)
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async loadCounterparties({commit}) {
            try {
                let response = await fetch(`http://localhost:8080/api/counterparties`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                });
                if(response.ok) {
                    const counterparties = await response.json();
                    commit("SET_COUNTERPARTIES", counterparties)
                    console.log(`Перечень контрагентов успешно загружен.`)
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async loadUsers({commit}) {
            try {
                let response = await fetch(`http://localhost:8080/api/users`, {
                    headers: {'Authorization': localStorage.getItem('access_token')}
                })
                if(response.ok) {
                    const users = await response.json();
                    commit("SET_USERS", users)
                    console.log(`Пользователи успешно загружены.`)
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async loadFirstReport({commit}, form) {
            try {
                let obj = {}
                obj.beginDate = form.approxBeginDate
                obj.endDate = form.approxEndDate
                let response = await fetch(`http://localhost:8080/api/reports`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': localStorage.getItem('access_token')
                    },
                    body: JSON.stringify(obj)
                })
                if(response.ok) {
                    let blob = await response.blob();
                    let url = window.URL.createObjectURL(blob, { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });

                    let a = document.createElement('a');
                    a.href = url;
                    a.download = "contracts.xlsx";
                    a.innerHTML = 'Ссылка на скачивание отчета с договорами за указанный период.'
                    const firstLinkBlock = document.getElementById('firstReportLink')
                    firstLinkBlock.innerHTML=''
                    firstLinkBlock.appendChild(a);

                    commit("SET_FIRST_REPORT", '')
                    console.log(`Договоры за плановый период ${form.approxBeginDate}-${form.approxEndDate} успешно загружены.`)
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async loadSecondReport({commit}, id) {
            try {
                let response = await fetch(`http://localhost:8080/api/reports/contract_id=${id}`, {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `${localStorage.getItem('access_token')}`
                    }
                })
                if(response.ok) {
                    let blob = await response.blob();
                    let url = window.URL.createObjectURL(blob, { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
                    let a = document.createElement('a');
                    a.href = url;
                    a.download = `stages${id}.xlsx`;
                    a.innerHTML = 'Ссылка на скачивание отчета с этапами для выбранного договора.'
                    const secondLinkBlock = document.getElementById('secondReportLink')
                    secondLinkBlock.innerHTML=''
                    secondLinkBlock.appendChild(a);

                    commit("SET_SECOND_REPORT", '')
                    console.log(`Этапы для договора с id ${id} успешно загружены.`)
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async login({commit}, formBody) {
            try {
                let res = await fetch('http://localhost:8080/login', {
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
                    commit('SET_ACCESS_TOKEN', `Bearer ${obj['access_token']}`)
                    commit('SET_REFRESH_TOKEN', obj['refresh_token'])

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
        }
    }
})