package com.example.accountingsystem.entities.stage;

import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StageRepo extends JpaRepository<Stage, Long> {
    @Query(value = "SELECT * FROM stage s WHERE s.contract_id = :id",
            nativeQuery = true)
    List<Stage> getStagesByContractId(@Param("id") Long id);
}
