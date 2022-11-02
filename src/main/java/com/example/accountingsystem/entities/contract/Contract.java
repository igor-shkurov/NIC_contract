package com.example.accountingsystem.entities.contract;

import com.example.accountingsystem.entities.ContractType;
import com.example.accountingsystem.entities.ExportableContract;
import com.example.accountingsystem.entities.user.User;
import org.aspectj.apache.bcel.classfile.Module;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Contract implements ExportableContract {
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
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User associatedUser;

    @Column(columnDefinition = "numeric(18,2)")
    private float sum;

    public Contract() {
    }

    public Contract(String name, ContractType contractType, LocalDate approxBeginDate, LocalDate approxEndDate, LocalDate beginDate, LocalDate endDate, float sum) {
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

    public float getSum() {
        return sum;
    }

    public void setSum(float sum) {
        this.sum = sum;
    }
}
