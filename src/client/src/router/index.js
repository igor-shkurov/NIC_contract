import VueRouter from 'vue-router'

import AuthPage from '../pages/AuthPage'
import NotFound from '../pages/404-page'
import ContractsLayout from "../pages/ContractsLayout";
import ContractsPage from "@/pages/ContractsPage";
import CounterpartiesPage from "@/pages/CounterpartiesPage";
import UsersPage from "@/pages/UsersPage";
import ReportsPage from "@/pages/ReportsPage";

export default new VueRouter ({
    mode: 'history',
    routes: [
        {
            path: '/',
            component: AuthPage
        },
        {
            path: '/auth',
            name: 'auth',
            component: AuthPage
        },
        {
            path: '/contracts',
            name: 'contractsLayout',
            component: ContractsLayout,
            children: [
                {
                    path: '',
                    name: 'contractsList',
                    component: ContractsPage,
                    props: { mode: 'contracts', inserting: {isInserted: false, openModalID: null}}
                }
            ]
        },
        {
            path: '/contracts/*',
            redirect: { name: 'contractsList', props: {mode: 'contracts', inserting: {isInserted: false, openModalID: null} } }
        },

        {
            path: '/counterparties',
            name: 'counterpartiesLayout',
            component: ContractsLayout,
            children: [
                {
                    path: '',
                    name: 'counterpartiesList',
                    component: CounterpartiesPage,
                    props: { mode: 'counterparties', inserting: {isInserted: false, openModalID: null} }
                }
            ]
        },
        {
            path: '/counterparties/*',
            redirect: { name: 'counterpartiesList', props: {mode: 'counterparties', inserting: {isInserted: false, openModalID: null}}}
        },
        {
            path: '/administration',
            name: 'administrationLayout',
            component: ContractsLayout,
            children: [
                {
                    path: '',
                    name: 'administrationList',
                    component: UsersPage,
                    props: { mode: 'users', inserting: {isInserted: false, openModalID: null}}
                }
            ]
        },
        {
            path: '/administration/*',
            redirect: { name: 'administartionList', props: {mode: 'users', inserting: {isInserted: false, openModalID: null}}}
        },
        {
            path: '/reports',
            name: 'reportsLayout',
            component: ContractsLayout,
            children: [
                {
                    path: '',
                    name: 'reportsList',
                    component: ReportsPage
                }
            ]
        },
        {
            path: '/reports/*',
            redirect: { name: 'reportsList'}
        },
        {
            path: '*',
            name: 'notFound',
            component: NotFound
        }
    ]
})