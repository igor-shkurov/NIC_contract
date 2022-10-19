package com.example.accountingsystem.entities.stage;

import com.example.accountingsystem.entities.contract.Contract;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(30)")
    private String name;

    @Column(columnDefinition = "date")
    private Date approxBeginDate;
    @Column(columnDefinition = "date")
    private Date approxEndDate;
    @Column(columnDefinition = "date")
    private Date beginDate;
    @Column(columnDefinition = "date")
    private Date endDate;

    @Column(columnDefinition = "numeric(18,2)")
    private float debit;
    @Column(columnDefinition = "numeric(18,2)")
    private float credit;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Contract contract;

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

    public float getDebit() {
        return debit;
    }

    public void setDebit(float debit) {
        this.debit = debit;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
