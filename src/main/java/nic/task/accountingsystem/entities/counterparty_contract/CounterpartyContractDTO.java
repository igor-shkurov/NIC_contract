package nic.task.accountingsystem.entities.counterparty_contract;

import nic.task.accountingsystem.entities.ExportableContractDTO;

import javax.validation.constraints.Min;

public class CounterpartyContractDTO extends ExportableContractDTO {
    @Min(value = 0, groups = {New.class, Modify.class})
    private Long counterpartyId;

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
