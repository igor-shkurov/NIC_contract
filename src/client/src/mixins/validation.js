import {maxLength, minLength, minValue, numeric, required, helpers} from "vuelidate/lib/validators";
import {validationMixin} from "vuelidate";

const alphaWithoutWhitespaces = helpers.regex('alphaWithoutWhitespaces', /^[а-яА-ЯёЁa-zA-Z\s]*$/)

export const checkValid = {
    data() {
        return {
            isValidForm: true
        }
    },
    mixins: [validationMixin],
    validations: {
        userForm: {
            FIO: { required, minLength: minLength(5), maxLength: maxLength(50), alphaWithoutWhitespaces},
            username : { required , minLength: minLength(3), maxLength: maxLength(50)},
            role: { required}
        },
        contractForm: {
            name: { required, minLength: minLength(3), maxLength: maxLength(30)},
            contractType: { required },
            approxBeginDate: { required },
            approxEndDate: { required },
            beginDate: { required },
            endDate: {  required},
            sum: { required, minValue: minValue(0), numeric }
        },
        contractCounterpartyForm: {
            name: { required, minLength: minLength(3), maxLength: maxLength(30) },
            contractType: { required },
            counterpartyId: { required },
            sum: { required, minValue: minValue(0), numeric },
            approxBeginDate: { required },
            approxEndDate: { required },
            beginDate: { required },
            endDate: {  required }
        },
        counterpartyForm: {
            name: { required, minLength: minLength(3), maxLength: maxLength(30) },
            address: { required, minLength: minLength(5), maxLength: maxLength(50) },
            inn: { required, minLength: minLength(10), maxLength: maxLength(10), numeric }
        },
        stageForm: {
            name: { required, minLength: minLength(3), maxLength: maxLength(30) },
            approxBeginDate: { required },
            approxEndDate: { required },
            beginDate: { required },
            endDate: { required },
            sum: { required, minValue: minValue(0), numeric },
            approxCredit: { required, minValue: minValue(0), numeric },
            approxSalary: { required, minValue: minValue(0), numeric },
            credit: { required, minValue: minValue(0), numeric },
            salary: { required, minValue: minValue(0), numeric }
        },
        firstReportForm: {
            approxBeginDate: { required},
            approxEndDate: { required}
        },
        secondReportForm: {
            contractId: {required}
        },
        changePassForm: {
            id: {},
            password: {required, minLength: minLength(3), maxLength: maxLength(50)},
            confirmPassword: {required}
        },
        addForm: {
            FIO: { required, minLength: minLength(5), maxLength: maxLength(50), alphaWithoutWhitespaces},
            username : { required , minLength: minLength(3), maxLength: maxLength(50)},
            role: { required},
            password : { required, minLength: minLength(3), maxLength: maxLength(50)}
        }
    },
    methods: {
        validation(){
            this.$v.$touch()
            let msgElem = document.getElementById('validation-message')
            msgElem.innerHTML=''
            let validMsg = this.checkValidation()

            if(validMsg) {
                const msg = document.createElement('span')
                msg.innerHTML = validMsg
                msgElem.appendChild(msg)
            }
            else {

                console.log('Введенные данные для получения отчета прошли валидацию.')
            }
        },
        checkValidation() {
            let s = ''
            let form;
            switch (this.$props.mode) {
                case 'contracts':
                    form = this.$v.contractForm
                    if (form.name.$invalid) {
                        this.isValidForm = false
                        s = 'Название договора должно содержать от 3 до 30 символов(букв, цифр и символов).'
                    } else if (form.sum.$invalid) {
                        this.isValidForm = false
                        s = 'Сумма договора должна быть числом, больше нуля.'
                    } else if (form.$error) {
                        this.isValidForm = false
                        s = 'Пожалуйста, введите все поля.'
                    } else this.isValidForm = true
                    break
                case 'counterparties':
                    form = this.$v.counterpartyForm
                    if (form.name.$invalid) {
                        this.isValidForm = false
                        s = 'Название организации-контрагента должно содержать от 3 до 30 символов(букв, цифр и символов).'
                    } else if (form.address.$invalid) {
                        this.isValidForm = false
                        s = 'Адрес организации-контрагента должен содержать от 5 до 50 символов(букв, цифр и символов).'
                    } else if (form.inn.$invalid) {
                        this.isValidForm = false
                        s = 'ИНН организации-контрагента должен содержать 10 цифр.'
                    } else if (form.$error) {
                        this.isValidForm = false
                        s = 'Пожалуйста, введите все поля.'
                    } else this.isValidForm = true
                    break
                case 'stages':
                    form = this.$v.stageForm
                    if (form.name.$invalid) {
                        this.isValidForm = false
                        s = 'Название этапа должно содержать от 3 до 30 символов(букв, цифр и символов).'
                    } else if (form.sum.$invalid) {
                        this.isValidForm = false
                        s = 'Сумма этапа должна быть числом, больше нуля.'
                    } else if (form.approxSalary.$invalid) {
                        this.isValidForm = false
                        s = 'Плановые расходы этапа на зарплату должны быть числом, больше нуля.'
                    } else if (form.approxCredit.$invalid) {
                        this.isValidForm = false
                        s = 'Плановые расходы этапа на материалы должны быть числом, больше нуля.'
                    }else if(form.credit.$invalid){
                        this.isValidForm = false
                        s = 'Фактические расходы этапа на материалы должны быть числом, больше нуля.'
                    } else if(form.salary.$invalid){
                        this.isValidForm = false
                        s = 'Фактические расходы этапа на зарплату должны быть числом, больше нуля.'
                    } else if (form.$error){
                        this.isValidForm = false
                        s = 'Пожалуйста, введите все поля.'
                    } else this.isValidForm = true
                    break

                case 'users':
                    form = this.$v.userForm
                    if (form.FIO.$invalid) {
                        this.isValidForm = false
                        s = 'ФИО пользователя должно содержать от 5 до 50 символов(только букв, латиницы/кириллицы).'
                    } else if (form.username.$invalid) {
                        this.isValidForm = false
                        s = 'Имя пользователя(username) должно содержать от 3 до 50 символов(букв, цифр и символов).'
                    } else if (form.$error) {
                        this.isValidForm = false
                        s = 'Пожалуйста, введите все поля.'
                    } else this.isValidForm = true
                    break

                case 'contractsCounterparty':
                    form = this.$v.contractCounterpartyForm
                    if (form.name.$invalid) {
                        this.isValidForm = false
                        s = 'Название договора с контрагентом должно содержать от 3 до 30 символов(букв, цифр и символов).'
                    } else if (form.sum.$invalid) {
                        this.isValidForm = false
                        s = 'Сумма договора с контрагентом должна быть числом, больше нуля.'
                    } else if (form.$error) {
                        this.isValidForm = false
                        s = 'Пожалуйста, введите все поля.'
                    } else this.isValidForm = true
                    break


                case 'reports':
                    if(this.formNumber === 1){
                        form = this.$v.firstReportForm
                        if (form.approxBeginDate.$invalid) {
                            this.isValidForm = false
                            s = 'Пожалуйста, для формирования отчета введите плановую дату начала действия договоров.'
                        } else if (form.approxEndDate.$invalid) {
                            this.isValidForm = false
                            s = 'Пожалуйста, для формирования отчета введите плановую дату окончания действия договоров.'
                        } else this.isValidForm = true
                    } else if(this.formNumber === 2) {
                        form = this.$v.secondReportForm
                        if (form.contractId.$invalid) {
                            this.isValidForm = false
                            s = 'Пожалуйста, для формирования отчета с этапами выберите договор из списка.'
                        } else this.isValidForm = true
                    }
                    break
            }
            return s
        },
        checkChangePass() {
            let s = ''
            this.$v.changePassForm.$touch()
            let msgElem = document.getElementById('changePass-validation-message')
            msgElem.innerHTML=''

            let form = this.$v.changePassForm
            if (form.password.$invalid) {
                this.isValidForm = false
                s = 'Новый пароль должен содержать от 3 до 50 символов(букв, цифр и символов).'
            } else if (form.$error) {
                this.isValidForm = false
                s = 'Пожалуйста, введите все поля.'
            } else if (this.changePassForm.confirmPassword !== this.changePassForm.password) {
                this.isValidForm = false
                s = 'Подтвержденный пароль не совпадает с введенным выше'
            } else this.isValidForm = true
            let validMsg = s
            console.log(s)
            if(validMsg) {
                const msg = document.createElement('span')
                msg.innerHTML = validMsg
                msgElem.appendChild(msg)
            }
            else {
                console.log('Введенные данные для получения отчета прошли валидацию.')
            }
        },
        checkAddForm() {
            let s = ''
            this.$v.changePassForm.$touch()
            let msgElem = document.getElementById('validation-message')
            msgElem.innerHTML=''

            let form = this.$v.addForm
            if (form.FIO.$invalid) {
                this.isValidForm = false
                s = 'ФИО пользователя должно содержать от 5 до 50 символов(только букв, латиницы/кириллицы).'
            } else if (form.username.$invalid) {
                this.isValidForm = false
                s = 'Имя пользователя(username) должно содержать от 3 до 50 символов(букв, цифр и символов).'
            } else if (form.role.$invalid) {
                this.isValidForm = false
                s = 'Введите роль пользователя.'
            } else if (form.password.$invalid) {
                this.isValidForm = false
                s = 'Новый пароль должен содержать от 3 до 50 символов(букв, цифр и символов).'
            } else if (form.$error) {
                this.isValidForm = false
                s = 'Пожалуйста, введите все поля.'
            } else this.isValidForm = true

            let validMsg = s
            if(validMsg) {
                const msg = document.createElement('span')
                msg.innerHTML = validMsg
                msgElem.appendChild(msg)
            }
            else {
                console.log('Введенные данные для получения отчета прошли валидацию.')
            }
            console.log(this.addForm)
        }
    }
}