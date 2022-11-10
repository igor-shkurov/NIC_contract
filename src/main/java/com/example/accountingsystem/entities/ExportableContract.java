package com.example.accountingsystem.entities;

import java.time.LocalDate;

public interface ExportableContract {
    Long getId();
    String getName();
    ContractType getContractType();
    LocalDate getApproxBeginDate();
    LocalDate getApproxEndDate();
    LocalDate getBeginDate();
    LocalDate getEndDate();
    float getSum();
}
