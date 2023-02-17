package nic.task.accountingsystem.entities.contract;

import nic.task.accountingsystem.entities.ContractType;
import nic.task.accountingsystem.entities.counterparty_contract.CounterpartyContract;
import nic.task.accountingsystem.entities.stage.Stage;
import nic.task.accountingsystem.entities.user.User;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "varchar(30)")
    private String name;

    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @Column(columnDefinition = "date")

    private LocalDate approxBeginDate;
    @Column(columnDefinition = "date")
    private LocalDate approxEndDate;
    @Column(columnDefinition = "date")
    private LocalDate beginDate;
    @Column(columnDefinition = "date")
    private LocalDate endDate;
//    @Formula("if (select count(*) from stage s where contract_id = id) <> 0 )" +
//            "(select sum(s.sum) from stage s where s.contract_id = id)")
    @Column(columnDefinition = "numeric(18,2)")
    private BigDecimal sum;

    @ManyToOne(fetch = FetchType.EAGER)
    private User associatedUser;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "contract")
    private List<CounterpartyContract> counterpartyContracts;

    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "contract")
    private List<Stage> stages;

    public Contract() {
    }

    public Contract(String name, ContractType contractType, LocalDate approxBeginDate, LocalDate approxEndDate, LocalDate beginDate, LocalDate endDate, BigDecimal sum) {
        this.name = name;
        this.contractType = contractType;
        this.approxBeginDate = approxBeginDate;
        this.approxEndDate = approxEndDate;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContractType getContractType() {
        return contractType;
    }

    public void setContractType(ContractType contractType) {
        this.contractType = contractType;
    }

    public LocalDate getApproxBeginDate() {
        return approxBeginDate;
    }

    public void setApproxBeginDate(LocalDate approxBeginDate) {
        this.approxBeginDate = approxBeginDate;
    }

    public LocalDate getApproxEndDate() {
        return approxEndDate;
    }

    public void setApproxEndDate(LocalDate approxEndDate) {
        this.approxEndDate = approxEndDate;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public User getAssociatedUser() {
        return associatedUser;
    }

    public void setAssociatedUser(User associatedUser) {
        this.associatedUser = associatedUser;
    }

    public List<CounterpartyContract> getCounterpartyContracts() {
        return counterpartyContracts;
    }

    public void setCounterpartyContracts(List<CounterpartyContract> counterpartyContracts) {
        this.counterpartyContracts = counterpartyContracts;
    }

    public List<Stage> getStages() {
        return stages;
    }

    public void setStages(List<Stage> stages) {
        this.stages = stages;
    }
}
