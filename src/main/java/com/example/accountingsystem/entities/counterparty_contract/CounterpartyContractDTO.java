package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.ExportableContract;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class CounterpartyContractDTO extends ExportableContract {
    @NotEmpty(groups = {New.class, Modify.class})
    @Min(value = 0, groups = {New.class, Modify.class})
    private Long counterpartyId;

    @NotEmpty(groups = {New.class, Modify.class})
    @Min(value = 0, groups = {New.class, Modify.class})
    private Long contractId;

    public Long getCounterpartyId() {
        return counterpartyId;
    }

    public void setCounterpartyId(Long counterpartyId) {
        this.counterpartyId = counterpartyId;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }
}
