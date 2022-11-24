package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.contract.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ConcurrentModificationException;
import java.util.List;

@Repository
public interface CounterpartyContractRepo extends JpaRepository<CounterpartyContract, Long> {
    @Query(value = "SELECT * FROM counterparty_contract cc WHERE cc.contract_id = :id",
            nativeQuery = true)
    List<CounterpartyContract> getCounterpartyContractsByContractId(@Param("id") Long id);

    List<CounterpartyContract> findCounterpartyContractByContract(Contract contract);

    @Query(value = "SELECT * FROM counterparty_contract c WHERE c.contract_id = :id",
            nativeQuery = true)
    List<CounterpartyContract> getCounterpartyContractByContractId(@Param("id") Long id);
}
