package com.example.accountingsystem.entities;

import java.time.LocalDate;

public abstract class ExportableContract {
    public long id;
    public String name;
    public ContractType contractType;
    public LocalDate approxBeginDate;
    public LocalDate approxEndDate;
    public LocalDate beginDate;
    public LocalDate endDate;
    public float sum;
}
