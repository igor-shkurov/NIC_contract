package com.example.accountingsystem.entities.counterparty_contract;

import com.example.accountingsystem.entities.ContractType;
import com.example.accountingsystem.entities.ExportableContract;
import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.counterparty.Counterparty;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class CounterpartyContract implements ExportableContract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(columnDefinition = "numeric(18,2)")
    private float sum;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Contract contract;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }

    @JsonIgnore
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @JsonIgnore
    public Counterparty getCounterparty() {
        return counterparty;
    }

    public void setCounterparty(Counterparty counterparty) {
        this.counterparty = counterparty;
    }
}
