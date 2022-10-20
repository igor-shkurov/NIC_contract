package com.example.accountingsystem.entities.counterparty;

import javax.persistence.*;

@Entity
@Table
public class Counterparty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "varchar(30)")
    private String name;
    @Column(columnDefinition = "varchar(50)")
    private String address;
    @Column(columnDefinition = "char(12)")
    private String inn;

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
}
