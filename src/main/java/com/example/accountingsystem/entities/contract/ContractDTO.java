package com.example.accountingsystem.entities.contract;

import com.example.accountingsystem.entities.ExportableContract;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ContractDTO extends ExportableContract {
    @NotNull
    @Min(value = 0)
    public Long userId;
}
