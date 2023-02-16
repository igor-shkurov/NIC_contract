package nic.task.accountingsystem.entities.contract;

import nic.task.accountingsystem.entities.ExportableContractDTO;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class ContractDTO extends ExportableContractDTO {
    @Null(groups = {New.class})
    @NotNull(groups = {Modify.class})
    @Min(value = 1, groups = {Modify.class})
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}