package com.example.accountingsystem.entities.counterparty_contract;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CounterpartyContractMapper {
    @Mapping(target="id", source="entity.id")
    @Mapping(target="name", source="entity.name")
    @Mapping(target="contractType", source="entity.contractType")
    @Mapping(target="approxBeginDate", source="entity.approxBeginDate")
    @Mapping(target="approxEndDate", source="entity.approxEndDate")
    @Mapping(target="beginDate", source="entity.beginDate")
    @Mapping(target="endDate", source="entity.endDate")
    @Mapping(target="sum", source="entity.sum")
    CounterpartyContractDTO counterpartyContractToDTO(CounterpartyContract entity);

    @Mapping(target="id", source="dto.id")
    @Mapping(target="name", source="dto.name")
    @Mapping(target="contractType", source="dto.contractType")
    @Mapping(target="approxBeginDate", source="dto.approxBeginDate")
    @Mapping(target="approxEndDate", source="dto.approxEndDate")
    @Mapping(target="beginDate", source="dto.beginDate")
    @Mapping(target="endDate", source="dto.endDate")
    @Mapping(target="sum", source="dto.sum")
    CounterpartyContract DTOtoCounterpartyContract(CounterpartyContractDTO dto);
}