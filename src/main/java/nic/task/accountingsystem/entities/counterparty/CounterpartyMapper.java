package nic.task.accountingsystem.entities.counterparty;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CounterpartyMapper {
    CounterpartyDTO CounterpartyToDTO(Counterparty entity);

    Counterparty DTOtoCounterparty(CounterpartyDTO dto);

    List<CounterpartyDTO> toListOfDTO(List<Counterparty> entities);
}
