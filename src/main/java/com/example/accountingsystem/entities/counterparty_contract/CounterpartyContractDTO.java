package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.ExportableContract;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CounterpartyContractDTO extends ExportableContract {
    @NotEmpty
    @Min(value = 0)
    public Long counterpartyId;
    @NotEmpty
    @Min(value = 0)
    public Long contractId;
}
