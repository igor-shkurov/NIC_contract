package nic.task.accountingsystem.entities.contract;

import nic.task.accountingsystem.entities.user.CustomUserDetailsService;
import nic.task.accountingsystem.entities.user.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.math3.util.Pair;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContractService {

    private final ContractRepo contractRepo;
    private final CustomUserDetailsService userDetailsService;

    private final ContractMapper mapper;

    @Autowired
    public ContractService(ContractRepo contractRepo, CustomUserDetailsService userDetailsService) {
        this.contractRepo = contractRepo;
        this.userDetailsService = userDetailsService;
        this.mapper = Mappers.getMapper(ContractMapper.class);
    }

    public Pair<List<ContractDTO>, HttpStatus> getContractsForUser() {
        User user = userDetailsService.getCurrentUser();
        if (user.getRole() == User.Role.ADMIN) {
            return new Pair<>(mapper.toListOfDTO(contractRepo.findAll()), HttpStatus.OK);
        }
        List<Contract> entities = contractRepo.getContractsByAssociatedUserId(user.getId());
        return new Pair<>(mapper.toListOfDTO(entities), HttpStatus.OK);
    }

    public HttpStatus addContract(ContractDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        Contract contract = mapper.DTOtoContract(dto);
        contract.setAssociatedUser(currentUser);

        contractRepo.save(contract);
        return HttpStatus.CREATED;
    }

    public Pair<ContractDTO, HttpStatus> getContractDtoById(long id) {
        User currentUser = userDetailsService.getCurrentUser();
        Optional<Contract> opt = contractRepo.findById(id);
        if (opt.isPresent()) {
            Contract contract = opt.get();
            if (!Objects.equals(contract.getAssociatedUser().getId(), userDetailsService.getCurrentUser().getId()) && currentUser.getRole() != User.Role.ADMIN ) {
                return new Pair<>(null, HttpStatus.FORBIDDEN);
            }
            ContractDTO dto = mapper.contractContractToDTO(contract);
            return new Pair<>(dto, HttpStatus.OK);
        }
        return new Pair<>( null, HttpStatus.NOT_FOUND);
    }

    public Contract getContractById(long id) {
        Optional<Contract> opt = contractRepo.findById(id);
        return opt.orElse(null);
    }

    public List<ContractDTO> getContractsByGivenPeriod(LocalDate beginDate, LocalDate endDate) {
        User currentUser = userDetailsService.getCurrentUser();
        List<Contract> entities;
        if (currentUser.getRole() == User.Role.ADMIN) {
            entities = contractRepo.getContractsByGivenPeriod(Date.valueOf(beginDate), Date.valueOf(endDate));
        }
        else {
            entities = contractRepo.getContractsByGivenPeriod(Date.valueOf(beginDate), Date.valueOf(endDate), currentUser.getId());
        }
        return mapper.toListOfDTO(entities);
    }

    public HttpStatus updateContract(ContractDTO dto) {
        User currentUser = userDetailsService.getCurrentUser();
        long id = dto.getId();
        Contract updatingContract = mapper.DTOtoContract(dto);
        updatingContract.setAssociatedUser(userDetailsService.getUserById(dto.getUserId()));
        Contract contractToBeUpdated = getContractById(id);

        if (currentUser.getRole() != User.Role.ADMIN) {
            if (!Objects.equals(dto.getUserId(), contractToBeUpdated.getAssociatedUser().getId()) || !Objects.equals(dto.getUserId(), currentUser.getId())) {
                return HttpStatus.FORBIDDEN;
            }
        }

        if (contractToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(contractToBeUpdated, updatingContract);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            contractToBeUpdated.setId(id);
            contractRepo.save(contractToBeUpdated);
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public HttpStatus deleteContract(long id) {
        Optional<Contract> opt = contractRepo.findById(id);
        User currentUser = userDetailsService.getCurrentUser();
        if (opt.isPresent()) {
            if (currentUser.getRole() != User.Role.ADMIN) {
                if (!Objects.equals(opt.get().getAssociatedUser().getId(), currentUser.getId())) {
                    return HttpStatus.FORBIDDEN;
                }
            }
            contractRepo.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}
