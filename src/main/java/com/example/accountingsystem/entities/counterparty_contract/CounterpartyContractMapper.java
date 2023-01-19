package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.counterparty.Counterparty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface CounterpartyContractMapper {
    @Mapping(target="contractId", source="entity.contract")
    @Mapping(target="counterpartyId", source="entity.counterparty")
    CounterpartyContractDTO counterpartyContractToDTO(CounterpartyContract entity);

    CounterpartyContract DTOtoCounterpartyContract(CounterpartyContractDTO dto);

    List<CounterpartyContractDTO> toListOfDTO(List<CounterpartyContract> entities);

    default long fromContract(Contract contract) {
        return (contract == null) ? null : contract.getId();
    }

    default long fromCounterparty(Counterparty cp) {
        return (cp == null) ? null : cp.getId();
    }
}