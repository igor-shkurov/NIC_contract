package nic.task.accountingsystem.entities.counterparty;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.math3.util.Pair;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
public class CounterpartyService {
    private final CounterpartyRepo counterpartyRepo;

    private final CounterpartyMapper mapper;

    @Autowired
    public CounterpartyService(CounterpartyRepo counterpartyRepo) {
        this.counterpartyRepo = counterpartyRepo;
        mapper = Mappers.getMapper(CounterpartyMapper.class);
    }

    public Pair<List<CounterpartyDTO>, HttpStatus> getCounterparties() {
        List<Counterparty> entities = counterpartyRepo.findAll();
        return new Pair<>(mapper.toListOfDTO(entities), HttpStatus.OK);
    }

    public Counterparty getCounterpartyById(long id) {
        return counterpartyRepo.findById(id).orElse(null);
    }

    public HttpStatus addCounterparty(CounterpartyDTO dto) {
        if (counterpartyRepo.existsCounterpartyByInn(dto.getInn())) {
            return HttpStatus.CONFLICT;
        }
        Counterparty counterparty = mapper.DTOtoCounterparty(dto);
        counterpartyRepo.save(counterparty);
        return HttpStatus.CREATED;
    }

    public HttpStatus updateCounterparty(CounterpartyDTO dto) {
        long id = dto.getId();
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
            return HttpStatus.OK;
        }
        else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public HttpStatus deleteCounterparty(long id) {
        Optional<Counterparty> opt = counterpartyRepo.findById(id);
        if (opt.isPresent()) {
            if (!opt.get().getCounterpartyContracts().isEmpty()) {
                return HttpStatus.CONFLICT;
            }
            counterpartyRepo.deleteById(id);
            return HttpStatus.OK;
        }
        return HttpStatus.NOT_FOUND;
    }
}
