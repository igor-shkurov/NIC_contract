package com.example.accountingsystem.entities.contract;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(30)")
    private String name;

    public enum ContractType {  /* @todo: вынести enum из класса, чтобы избавиться от дупликата в CounterpartyContract */
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

    public Contract() {
    }

    public Contract(String name, ContractType contractType, Date approxBeginDate, Date approxEndDate, Date beginDate, Date endDate, float sum) {
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
}
