package nic.task.accountingsystem.entities.stage;

import nic.task.accountingsystem.entities.contract.Contract;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "varchar(30)")
    private String name;

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

    @Column(columnDefinition = "numeric(18,2)")
    private float approxSalary;
    @Column(columnDefinition = "numeric(18,2)")
    private float approxCredit;

    @Column(columnDefinition = "numeric(18,2)")
    private float salary;
    @Column(columnDefinition = "numeric(18,2)")
    private float credit;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
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

    public float getApproxSalary() {
        return approxSalary;
    }

    public void setApproxSalary(float approxSalary) {
        this.approxSalary = approxSalary;
    }

    public float getApproxCredit() {
        return approxCredit;
    }

    public void setApproxCredit(float approxCredit) {
        this.approxCredit = approxCredit;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    @JsonIgnore
    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
}
