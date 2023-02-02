package nic.task.accountingsystem.entities;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class ExportableContract {
    public interface New {}
    public interface Modify {}

    @Null(groups = {New.class})
    @NotNull(groups = {Modify.class})
    @Min(value = 0, groups = {Modify.class})
    private Long id;

    @NotEmpty(groups = {New.class, Modify.class})
    @Size(min = 3, max = 30, groups = {New.class, Modify.class})
    private String name;

    private ContractType contractType;
    private LocalDate approxBeginDate;
    private LocalDate approxEndDate;
    private LocalDate beginDate;
    private LocalDate endDate;

    @DecimalMin(value = "0.0", groups = {New.class, Modify.class})
    private BigDecimal sum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public LocalDate getApproxBeginDate() {
        return approxBeginDate;
    }

    public void setApproxBeginDate(LocalDate approxBeginDate) {
        this.approxBeginDate = approxBeginDate;
    }

    public LocalDate getApproxEndDate() {
        return approxEndDate;
    }

    public void setApproxEndDate(LocalDate approxEndDate) {
        this.approxEndDate = approxEndDate;
    }

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

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
