package com.example.accountingsystem.entities.counterparty_contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CounterpartyContractRepo extends JpaRepository<CounterpartyContract, Long> {
}
