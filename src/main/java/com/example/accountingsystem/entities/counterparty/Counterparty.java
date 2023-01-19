package com.example.accountingsystem.entities.counterparty;

import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContract;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Counterparty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(columnDefinition = "varchar(30)", unique = true)
    private String name;
    @Column(columnDefinition = "varchar(50)")
    private String address;
    @Column(columnDefinition = "char(10)", unique = true)
    private String inn;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "counterparty")
    private List<CounterpartyContract> counterpartyContracts;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public List<CounterpartyContract> getCounterpartyContracts() {
        return counterpartyContracts;
    }

    public void setCounterpartyContracts(List<CounterpartyContract> counterpartyContracts) {
        this.counterpartyContracts = counterpartyContracts;
    }
}
