package nic.task.accountingsystem.entities.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface ContractRepo extends JpaRepository<Contract, Long> {
    @Query(value = "SELECT * FROM contract c WHERE c.approx_begin_date>=:beginDate and c.approx_end_date<=:endDate and c.associated_user_id=:userId ;",
            nativeQuery = true)
    List<Contract> getContractsByGivenPeriod(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate, @Param("userId") long userId);

    @Query(value = "SELECT * FROM contract c WHERE c.approx_begin_date>=:beginDate and c.approx_end_date<=:endDate ;",
            nativeQuery = true)
    List<Contract> getContractsByGivenPeriod(@Param("beginDate") Date beginDate, @Param("endDate") Date endDate);

    List<Contract> getContractsByAssociatedUserId(long id);
}
