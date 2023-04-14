package nic.task.accountingsystem.entities.stage;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class StageDTO {
    public interface New {}
    public interface Modify {}

    @Null(groups = {New.class})
    @NotNull(groups = {Modify.class})
    @Min(value = 0, groups = {Modify.class})
    private Long id;

    @NotBlank(groups = {New.class, Modify.class})
    @Size(min = 1, max = 30, groups = {New.class, Modify.class})
    private String name;

    private LocalDate approxBeginDate;
    private LocalDate approxEndDate;
    private LocalDate beginDate;
    private LocalDate endDate;

    @DecimalMin(value = "0.0", groups = {New.class, Modify.class})
    private BigDecimal sum;

    @DecimalMin(value = "0.0", groups = {New.class, Modify.class})
    private BigDecimal approxSalary;

    @DecimalMin(value = "0.0", groups = {New.class, Modify.class})
    private BigDecimal approxCredit;

    @DecimalMin(value = "0.0", groups = {New.class, Modify.class})
    private BigDecimal salary;

    @DecimalMin(value = "0.0",groups = {New.class, Modify.class})
    private BigDecimal credit;

    @NotNull(groups = {New.class, Modify.class})
    @Min(value = 0, groups = {New.class, Modify.class})
    private long contractId;

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

    public BigDecimal getApproxSalary() {
        return approxSalary;
    }

    public void setApproxSalary(BigDecimal approxSalary) {
        this.approxSalary = approxSalary;
    }

    public BigDecimal getApproxCredit() {
        return approxCredit;
    }

    public void setApproxCredit(BigDecimal approxCredit) {
        this.approxCredit = approxCredit;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public long getContractId() {
        return contractId;
    }

    public void setContractId(long contractId) {
        this.contractId = contractId;
    }
}
