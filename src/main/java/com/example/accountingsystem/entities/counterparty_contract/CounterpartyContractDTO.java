package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.ContractType;
import com.example.accountingsystem.entities.ExportableContract;

import java.time.LocalDate;

public class CounterpartyContractDTO extends ExportableContract {
    public long counterpartyId;
    public long contractId;
}
