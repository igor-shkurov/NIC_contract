export const inputElems = {
    computed: {
        inputElemsKeys(){
            let arr = []
            this.$props.cardKeys.forEach(key => {
                if (key !== 'contractType' && key !== 'counterpartyId' && key !== 'role')
                    arr.push(key)
            })
            return arr
        },
        inputElemsHeaders(){
            let arr = []
            this.$props.cardFields.forEach(header => {
                if (header !== 'Тип договора' && header !== 'Организация-контрагент' && header !== 'Роль')
                    arr.push(header)
            })
            return arr
        },
    }
}