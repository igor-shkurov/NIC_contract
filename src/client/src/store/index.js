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
        firstReport: [],
        secondReport: [],
        cardHeader: []
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
        getFirstReport(state) {
            return state.firstReport
        },
        getSecondReport(state) {
            return state.secondReport
        },
        getCardHeader(state) {
            return state.cardHeader
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
        SET_FIRST_REPORT(state, payload) {
            state.firstReport = payload
        },
        SET_SECOND_REPORT(state, payload) {
            state.secondReport = payload
        },
        SET_CARD_HEADER(state, payload) {
            state.cardHeader = payload
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
                    headers: {'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoyMjc0MTI1OTM0fQ.EWkdapw8URtlQjGgnW40mmJY0_DoVKh6djU3yg6NpL0'}
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
                    headers: {'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoyMjc0MTI1OTM0fQ.EWkdapw8URtlQjGgnW40mmJY0_DoVKh6djU3yg6NpL0'}
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
                let response = await fetch(`http://localhost:8080/api/contract_counterparties/contract_id=${id}`, {
                    headers: {'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoyMjc0MTI1OTM0fQ.EWkdapw8URtlQjGgnW40mmJY0_DoVKh6djU3yg6NpL0'}
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
                    headers: {'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoyMjc0MTI1OTM0fQ.EWkdapw8URtlQjGgnW40mmJY0_DoVKh6djU3yg6NpL0'}
                });
                if(response.ok) {
                    const counterparties = await response.json();
                    console.log("Контрагенты:\n", counterparties)
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
                    headers: {'Authorization': 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjpbIlJPTEVfQURNSU4iXSwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgwL2xvZ2luIiwiZXhwIjoyMjc0MTI1OTM0fQ.EWkdapw8URtlQjGgnW40mmJY0_DoVKh6djU3yg6NpL0'}
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
        async loadFirstReport({commit}, beginDate, endDate) {
            try {
                let response = await fetch(`https://jsonplaceholder.typicode.com/users`/*, { //`https://jsonplaceholder.typicode.com/api/dowload/report/1`
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    body: JSON.stringify({
                        approxBeginDate: beginDate,
                        approxEndDate: endDate
                    }
                )}*/)
                if(response.ok) {
                    const contracts = await response.json();
                    commit("SET_FIRST_REPORT", contracts)
                    console.log(`Договоры за плановый период ${beginDate}-${endDate}успешно загружены.`)
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        },
        async loadSecondReport({commit}, id) {
            try {
                let response = await fetch(`https://jsonplaceholder.typicode.com/users`/*, { //`https://jsonplaceholder.typicode.com/api/dowload/report/1`
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    body: JSON.stringify({
                        id: id
                    }
                )}*/)
                if(response.ok) {
                    const stages = await response.json();
                    commit("SET_SECOND_REPORT", stages)
                    console.log(`Этапы для договора ${id} успешно загружены.`)
                } else {
                    alert("Ошибка HTTP: " + response.status);
                }
            } catch(error) {
                console.error(error)
            }
        }
    }
})