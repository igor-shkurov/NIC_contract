package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.counterparty.Counterparty;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class CounterpartyContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(30)")
    private String name;

    public enum ContractType {  /* @todo: вынести enum из класса, чтобы избавиться от дупликата в Contract */
        PURCHASE, SUPPLY, WORK;
    };

    @Enumerated(EnumType.STRING)
    private ContractType contractType;
    @Column(columnDefinition = "date")
    private Date approxBeginDate;
    @Column(columnDefinition = "date")
    private Date approxEndDate;
    @Column(columnDefinition = "date")
    private Date beginDate;
    @Column(columnDefinition = "date")
    private Date endDate;

    @Column(columnDefinition = "numeric(18,2)")
    private float sum;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Contract contract;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Counterparty counterparty;

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

    public Date getApproxBeginDate() {
        return approxBeginDate;
    }

    public void setApproxBeginDate(Date approxBeginDate) {
        this.approxBeginDate = approxBeginDate;
    }

    public Date getApproxEndDate() {
        return approxEndDate;
    }

    public void setApproxEndDate(Date approxEndDate) {
        this.approxEndDate = approxEndDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }
}
