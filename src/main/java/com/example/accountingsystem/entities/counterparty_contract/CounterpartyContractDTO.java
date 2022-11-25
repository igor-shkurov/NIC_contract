package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.ContractType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;

public class CounterpartyContractDTO {
    public long id;
    public String name;
    public ContractType contractType;
    public LocalDate approxBeginDate;
    public LocalDate approxEndDate;
    public LocalDate beginDate;
    public LocalDate endDate;
    public float sum;
    public long counterparty_id;
    @JsonIgnore
    public long contract_id;
}
