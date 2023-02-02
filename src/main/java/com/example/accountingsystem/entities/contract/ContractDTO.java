package com.example.accountingsystem.entities.contract;

import com.example.accountingsystem.entities.ExportableContract;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

public class ContractDTO extends ExportableContract {
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
