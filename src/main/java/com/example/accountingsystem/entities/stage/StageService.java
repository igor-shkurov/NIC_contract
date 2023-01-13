package com.example.accountingsystem.entities.stage;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import org.apache.commons.beanutils.BeanUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StageService {
    private final StageRepo stageRepo;
    private final ContractService contractService;
    private final CustomUserDetailsService userDetailsService;

    private final StageMapper mapper;

    @Autowired
    public StageService(StageRepo stageRepo, ContractService contractService, CustomUserDetailsService userDetailsService) {
        this.stageRepo = stageRepo;
        this.contractService = contractService;
        this.userDetailsService = userDetailsService;
        mapper = Mappers.getMapper(StageMapper.class);
    }

    public boolean addStage(StageDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        Contract contract = contractService.getContractById(dto.getContractId());
        if (!Objects.equals(contract.getAssociatedUser().getId(), currentUser.getId()) && currentUser.getRole() != User.Role.ADMIN) {
            return false;
        }
        Stage entity = mapper.DTOtoStage(dto);
        entity.setContract(contractService.getContractById(dto.getContractId()));

        stageRepo.save(entity);
        return true;
    }

    public Stage getStageById(long id) { return stageRepo.findById(id).orElse(null); }

    public List<StageDTO> getStagesByContractId(long id) {
        User currentUser = userDetailsService.getCurrentUser();
        Contract contract = contractService.getContractById(id);
        List<Stage> entities = null;
        if (Objects.equals(currentUser.getId(), contract.getAssociatedUser().getId()) || currentUser.getRole() == User.Role.ADMIN) {
            entities = stageRepo.getStagesByContractId(id);
        }
        return mapper.toListOfDTO(entities);
    }

    public boolean updateStage(StageDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        long id = dto.getId();
        Stage updatingStage = mapper.DTOtoStage(dto);
        updatingStage.setContract(contractService.getContractById(dto.getContractId()));

        Stage stageToBeUpdated = getStageById(id);

        if (!Objects.equals(stageToBeUpdated.getContract().getAssociatedUser().getId(), currentUser.getId()) ||
            !Objects.equals(updatingStage.getContract().getAssociatedUser().getId(), currentUser.getId()) &&
                currentUser.getRole() != User.Role.ADMIN)
        {
            return false;
        }

        if (stageToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(stageToBeUpdated, updatingStage);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            stageToBeUpdated.setId(id);
            stageRepo.save(stageToBeUpdated);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean deleteStage(long id) {
        Optional<Stage> opt = stageRepo.findById(id);
        User currentUser = userDetailsService.getCurrentUser();
        if (opt.isPresent()) {
            if (currentUser.getRole() != User.Role.ADMIN) {
                if (!Objects.equals(opt.get().getContract().getAssociatedUser().getId(), currentUser.getId())) {
                    return false;
                }
            }
            stageRepo.deleteById(id);
            return true;
        }
        return false;
    }
}
