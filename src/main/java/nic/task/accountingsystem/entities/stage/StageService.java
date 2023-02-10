package nic.task.accountingsystem.entities.stage;

import nic.task.accountingsystem.entities.contract.Contract;
import nic.task.accountingsystem.entities.contract.ContractService;
import nic.task.accountingsystem.entities.user.CustomUserDetailsService;
import nic.task.accountingsystem.entities.user.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.math3.util.Pair;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    public HttpStatus addStage(StageDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        Contract contract = contractService.getContractById(dto.getContractId());
        if (contract == null) {
            return HttpStatus.NOT_FOUND;
        }
        if (!Objects.equals(contract.getAssociatedUser().getId(), currentUser.getId()) && currentUser.getRole() != User.Role.ADMIN) {
            return HttpStatus.FORBIDDEN;
        }
        Stage entity = mapper.DTOtoStage(dto);
        entity.setContract(contractService.getContractById(dto.getContractId()));

        stageRepo.save(entity);
        return HttpStatus.CREATED;
    }

    public Stage getStageById(long id) { return stageRepo.findById(id).orElse(null); }

    public Pair<List<StageDTO>, HttpStatus> getStagesByContractId(long id) {
        User currentUser = userDetailsService.getCurrentUser();
        Contract contract = contractService.getContractById(id);
        if (contract == null) {
            return new Pair<>(null, HttpStatus.NOT_FOUND);
        }
        if (!Objects.equals(currentUser.getId(), contract.getAssociatedUser().getId()) && currentUser.getRole() != User.Role.ADMIN) {
            return new Pair<>(null, HttpStatus.FORBIDDEN);
        }
        List<Stage> list = stageRepo.getStagesByContractId(id);
        return new Pair<>(mapper.toListOfDTO(list), HttpStatus.OK);
    }

    public HttpStatus updateStage(StageDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        long id = dto.getId();
        Stage updatingStage = mapper.DTOtoStage(dto);
        updatingStage.setContract(contractService.getContractById(dto.getContractId()));

        Stage stageToBeUpdated = getStageById(id);

        if (!Objects.equals(stageToBeUpdated.getContract().getAssociatedUser().getId(), currentUser.getId()) ||
            !Objects.equals(updatingStage.getContract().getAssociatedUser().getId(), currentUser.getId()) &&
                currentUser.getRole() != User.Role.ADMIN)
        {
            return HttpStatus.FORBIDDEN;
        }

        if (stageToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(stageToBeUpdated, updatingStage);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            stageToBeUpdated.setId(id);
            stageRepo.save(stageToBeUpdated);
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public HttpStatus deleteStage(long id) {
        Optional<Stage> opt = stageRepo.findById(id);
        User currentUser = userDetailsService.getCurrentUser();
        if (opt.isPresent()) {
            if (currentUser.getRole() != User.Role.ADMIN) {
                if (!Objects.equals(opt.get().getContract().getAssociatedUser().getId(), currentUser.getId())) {
                    return HttpStatus.FORBIDDEN;
                }
            }
            stageRepo.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}
