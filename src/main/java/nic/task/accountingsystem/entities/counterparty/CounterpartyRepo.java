package nic.task.accountingsystem.entities.counterparty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterpartyRepo extends JpaRepository<Counterparty, Long> {
    Boolean existsCounterpartyByInn(String inn);
}
