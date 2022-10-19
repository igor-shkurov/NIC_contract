package com.example.accountingsystem.entities.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepo
        extends JpaRepository<Contract, Long> {

}
