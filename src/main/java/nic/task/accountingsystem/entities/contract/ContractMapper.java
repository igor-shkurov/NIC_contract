package nic.task.accountingsystem.entities.contract;

import nic.task.accountingsystem.entities.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface ContractMapper  {
    @Mapping(target = "userId", source = "entity.associatedUser")
    ContractDTO contractContractToDTO(Contract entity);

    Contract DTOtoContract(ContractDTO dto);

    List<ContractDTO> toListOfDTO(List<Contract> entities);

    default long fromUser(User user) {
        return user == null ? null : user.getId();
    }
}
