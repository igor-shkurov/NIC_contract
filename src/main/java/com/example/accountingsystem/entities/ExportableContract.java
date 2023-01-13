package com.example.accountingsystem.entities;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class ExportableContract {
    public interface New {}
//    public interface Modify {}

    @Null(groups = New.class)
    public Long id;
    @NotEmpty
    @Size(min = 3, max = 30)
    public String name;
    public ContractType contractType;
    public LocalDate approxBeginDate;
    public LocalDate approxEndDate;
    public LocalDate beginDate;
    public LocalDate endDate;
    @DecimalMin(value = "0.0")
    public BigDecimal sum;
}
