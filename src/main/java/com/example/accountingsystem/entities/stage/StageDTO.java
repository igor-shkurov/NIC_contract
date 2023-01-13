package com.example.accountingsystem.entities.stage;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class StageDTO {
    @Null
    public Long id;
    @NotBlank
    @Size(min = 2, max = 30)
    public String name;
    public LocalDate approxBeginDate;
    public LocalDate approxEndDate;
    public LocalDate beginDate;
    public LocalDate endDate;
    @NotEmpty
    @DecimalMin(value = "0.0")
    public BigDecimal sum;
    @NotEmpty
    @DecimalMin(value = "0.0")
    public BigDecimal approxSalary;
    @NotEmpty
    @DecimalMin(value = "0.0")
    public BigDecimal approxCredit;
    @NotEmpty
    @DecimalMin(value = "0.0")
    public BigDecimal salary;
    @NotEmpty
    @DecimalMin(value = "0.0")
    public BigDecimal credit;
    @NotEmpty
    @Min(value = 0)
    public long contractId;
}
