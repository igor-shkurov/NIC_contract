package nic.task.accountingsystem.utility;

import java.time.LocalDate;

public class DatesDTO {
    private LocalDate approxBeginDate;
    private LocalDate approxEndDate;

    public LocalDate getBeginDate() {
        return approxBeginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.approxBeginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return approxEndDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.approxEndDate = endDate;
    }
}
