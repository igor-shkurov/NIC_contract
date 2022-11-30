package com.example.accountingsystem.entities.contract;

import com.example.accountingsystem.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ContractMapper  {
    @Mapping(target = "user_id", source = "entity.associatedUser")
    ContractDTO contractContractToDTO(Contract entity);

    Contract DTOtoContract(ContractDTO dto);

    List<ContractDTO> toListOfDTO(List<Contract> entities);

    default long fromUser(User user) {
        return user == null ? null : user.getId();
    }
}
