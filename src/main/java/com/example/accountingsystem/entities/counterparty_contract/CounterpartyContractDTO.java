package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.ContractType;
import com.example.accountingsystem.entities.ExportableContract;

import java.time.LocalDate;

public class CounterpartyContractDTO implements ExportableContract {
    public long id;
    public String name;
    public ContractType contractType;
    public LocalDate approxBeginDate;
    public LocalDate approxEndDate;
    public LocalDate beginDate;
    public LocalDate endDate;
    public float sum;
    public long counterparty_id;
    public long contract_id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ContractType getContractType() {
        return contractType;
    }

    @Override
    public LocalDate getApproxBeginDate() {
        return approxBeginDate;
    }

    @Override
    public LocalDate getApproxEndDate() {
        return approxEndDate;
    }

    @Override
    public LocalDate getBeginDate() {
        return beginDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public float getSum() {
        return sum;
    }
}
