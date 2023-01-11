package com.example.accountingsystem.entities.counterparty;

import org.springframework.validation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

public class CounterpartyDTO {
    @Null
    private Long id;
    @NotEmpty
    @Size(min = 2)
    private String name;
    private String address;
    private String inn;

    public CounterpartyDTO() {
    }

    public CounterpartyDTO(Long id, String name, String address, String inn) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.inn = inn;
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
