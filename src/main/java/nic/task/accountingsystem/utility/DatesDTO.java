package nic.task.accountingsystem.utility;

import java.time.LocalDate;

public class DatesDTO {
    private LocalDate beginDate;
    private LocalDate endDate;

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
