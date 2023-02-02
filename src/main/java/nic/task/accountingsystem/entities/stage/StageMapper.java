package nic.task.accountingsystem.entities.stage;

import nic.task.accountingsystem.entities.contract.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface StageMapper {
    @Mapping(target = "contractId", source = "entity.contract")
    StageDTO stageToDTO(Stage entity);

    Stage DTOtoStage(StageDTO dto);

    List<StageDTO> toListOfDTO(List<Stage> entities);

    default long fromContract(Contract contract) {
        return contract == null ? null : contract.getId();
    }
}
