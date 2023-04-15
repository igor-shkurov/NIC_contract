export const dateFormat = {
    methods: {
        getDateFormat(inputDate){
            inputDate = new Date(inputDate)
            let date, month, year;

            date = inputDate.getDate();
            month = inputDate.getMonth() + 1; // take care of the month's number
            year = inputDate.getFullYear();

            if (date < 10) {
                date = '0' + date;
            }

            if (month < 10) {
                month = '0' + month;
            }

            return `${date}.${month}.${year}`;
        }
    }
}