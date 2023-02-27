export const filtration = {
    methods: {
        getFilteredByInputSearch(arrData, elemKey, searchKey) {
            return arrData.filter(elem => elem[elemKey].toLowerCase().includes(searchKey.trim().toLowerCase()))
        },
        getFilteredByType(arrData, elemKey, type) {
            return arrData.filter(elem => elem[elemKey] === type)
        },
        getFilteredByFrom(arrData, keyFrom, dataFrom) {
            return arrData.filter(elem => parseInt(elem[keyFrom]) >= parseInt(dataFrom))
        },
        getFilteredByTo(arrData, keyTo, dataTo) {
            return arrData.filter(elem => parseInt(elem[keyTo]) <= parseInt(dataTo))
        },
        getFilteredByFromData(arrData, keyFrom, dataFrom) {
            return arrData.filter(elem => new Date(elem[keyFrom]) >= new Date(dataFrom))
        },
        getFilteredByToData(arrData, keyTo, dataTo) {
            return arrData.filter(elem => new Date(elem[keyTo]) <= new Date(dataTo))
        }
    }
}