package com.example.accountingsystem.entities.counterparty;

import com.example.accountingsystem.entities.contract.Contract;
import org.apache.commons.beanutils.BeanUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class CounterpartyService {
    private final CounterpartyRepo counterpartyRepo;

    private final CounterpartyMapper mapper;

    @Autowired
    public CounterpartyService(CounterpartyRepo counterpartyRepo) {
        this.counterpartyRepo = counterpartyRepo;
        mapper = Mappers.getMapper(CounterpartyMapper.class);
    }

    public List<CounterpartyDTO> getCounterparties() {
        List<Counterparty> entities = counterpartyRepo.findAll();
        return mapper.toListOfDTO(entities);
    }

    public Counterparty getCounterpartyById(long id) {
        return counterpartyRepo.findById(id).orElse(null);
    }

    public void updateCounterparty(CounterpartyDTO dto) {
        long id = dto.id;
        Counterparty updatingCp = mapper.DTOtoCounterparty(dto);
        Counterparty cpToBeUpdated = getCounterpartyById(id);
        if (cpToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(cpToBeUpdated, updatingCp);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            cpToBeUpdated.setId(id);
            counterpartyRepo.save(cpToBeUpdated);
        }
        else {
            counterpartyRepo.save(updatingCp);
        }
    }

    public void deleteCounterparty(long id) {
        counterpartyRepo.deleteById(id);
    }
}
