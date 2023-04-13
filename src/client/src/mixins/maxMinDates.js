export const maxMinDates = {
    methods: {
        getDateInputId(key){
            switch(key){
                case 'endDate':
                    return 'inputEndDate'
                case 'approxEndDate':
                    return 'inputApproxEndDate'
                case 'approxBeginDate':
                    return 'inputApproxBeginDate'
                case 'beginDate':
                    return 'inputBeginDate'
            }
            return ''
        },
        getIndexForHTMLElem(selector){
            console.log(document.querySelectorAll(selector))
            let ind = ''
            if(this.mode==='contracts'|| this.mode==='counterparties' || this.mode === 'users')
                ind = 0
            else if(document.querySelectorAll(selector).length===1){
                ind = 0
            } else {
                ind = 1
            }
            console.log('index html: ', ind)
            return ind
        },
        getMaxMinDate(key){
            let res = ''
            switch(key){
                case 'endDate':
                    res = this.newObj!== undefined? this.newObj['beginDate'] : this.addForm['beginDate']
                    break
                case 'approxEndDate':
                    res = this.newObj!== undefined? this.newObj['approxBeginDate'] : this.addForm['approxBeginDate']
                    break
                case 'beginDate':
                    res = this.newObj!== undefined? this.newObj['endDate'] : this.addForm['endDate']
                    break
                case 'approxBeginDate':
                    res = this.newObj!== undefined? this.newObj['approxEndDate'] : this.addForm['approxEndDate']
                    break
            }
            if(res)
                return this.getDateFormat(res)
            return ''
        },
        setMaxMinDate(key){
            console.log('in SetMaxMinDate')
            console.log(this.newObj)
            let selector1= '', selector2 = '', ind = '', attr1 = '', attr2 = '', newValue = '', value = ''
            switch(key){
                case 'endDate':     // this.newObj? - проверка edit или add режим модалки
                    ind = this.getIndexForHTMLElem('#inputEndDate')
                    if(this.newObj!== undefined? this.newObj['beginDate'] : this.addForm['beginDate']) {
                        value = this.newObj!== undefined? this.newObj['beginDate'] : this.addForm['beginDate']
                        selector1 = '#inputEndDate'
                        attr1 = 'min'
                    }
                    if(this.newObj!== undefined? this.newObj['endDate'] : this.addForm['endDate']){
                        newValue = this.newObj!== undefined? this.newObj['endDate'] : this.addForm['endDate']
                        selector2 = '#inputBeginDate'
                        attr2 = 'max'
                    }
                    break
                case 'approxEndDate':
                    ind = this.getIndexForHTMLElem('#inputApproxEndDate')
                    if(this.newObj!== undefined? this.newObj['approxBeginDate'] : this.addForm['approxBeginDate']) {
                        value = this.newObj!== undefined? this.newObj['approxBeginDate'] : this.addForm['approxBeginDate']
                        selector1 = '#inputApproxEndDate'
                        attr1 = 'min'
                    }
                    if(this.newObj!== undefined? this.newObj['approxEndDate'] : this.addForm['approxEndDate']){
                        newValue = this.newObj!== undefined? this.newObj['approxEndDate'] : this.addForm['approxEndDate']
                        selector2 = '#inputApproxBeginDate'
                        attr2 = 'max'
                    }
                    break
                case 'approxBeginDate':
                    ind = this.getIndexForHTMLElem('#inputApproxBeginDate')
                    if(this.newObj!== undefined? this.newObj['approxEndDate'] : this.addForm['approxEndDate']) {
                        value = this.newObj!== undefined? this.newObj['approxEndDate'] : this.addForm['approxEndDate']
                        selector1 = '#inputApproxBeginDate'
                        attr1 = 'max'
                    }
                    if (this.newObj!== undefined? this.newObj['approxBeginDate'] : this.addForm['approxBeginDate']){
                        newValue = this.newObj!== undefined? this.newObj['approxBeginDate'] : this.addForm['approxBeginDate']
                        attr2 = 'min'
                        selector2 = '#inputApproxEndDate'
                    }
                    break
                case 'beginDate':
                    ind = this.getIndexForHTMLElem('#inputBeginDate')
                    if(this.newObj!== undefined? this.newObj['endDate'] : this.addForm['endDate']) {
                        value = this.newObj!== undefined? this.newObj['endDate'] : this.addForm['endDate']
                        selector1 = '#inputBeginDate'
                        attr1 = 'max'
                    }
                    if(this.newObj!== undefined? this.newObj['beginDate'] : this.addForm['beginDate']){
                        newValue = this.newObj!== undefined? this.newObj['beginDate'] : this.addForm['beginDate']
                        attr2 = 'min'
                        selector2 = '#inputEndDate'
                    }
                    break
            }
            if(value)
                document.querySelectorAll(selector1)[ind]?.setAttribute(attr1, value)
            if(newValue)
                document.querySelectorAll(selector2)[ind]?.setAttribute(attr2, newValue)
        },
        changeDateHandler(key){
            console.log('in changeDateHandler')
            if(key === 'approxBeginDate' || key === 'approxEndDate' || key === 'beginDate' || key === 'endDate')
                this.setMaxMinDate(key)
            this.checkDates()
        }
    }
}