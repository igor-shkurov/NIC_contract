package com.example.accountingsystem.entities.contract;

import com.example.accountingsystem.entities.ExportableContract;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ContractDTO extends ExportableContract {
    @NotNull(groups = {New.class, Modify.class})
    @Min(value = 0, groups = {New.class, Modify.class})
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
