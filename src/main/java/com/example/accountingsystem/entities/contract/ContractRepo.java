package com.example.accountingsystem.entities.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ContractRepo
        extends JpaRepository<Contract, Long> {
    @Query(value = "SELECT * FROM contract c WHERE c.begin_date>=:beginDate and c.end_date<=:endDate ;",
            nativeQuery = true)
    List<Contract> getContractsByGivenPeriod(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

//    List<Contract> findContractsByBeginDateIsAfterAndEndDateIsBefore(LocalDate beginDate, LocalDate endDate);
}
