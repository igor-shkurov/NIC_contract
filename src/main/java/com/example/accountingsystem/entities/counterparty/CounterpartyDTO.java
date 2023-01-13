package com.example.accountingsystem.entities.counterparty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CounterpartyDTO {
    @Null
    private Long id;
    @NotBlank
    @Size(min = 3, max = 30)
    private String name;
    @NotBlank
    @Size(min = 5, max = 50)
    private String address;
    @NotBlank
    @Pattern(regexp="[\\d]{10}")
    private String inn;

    public CounterpartyDTO() {
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
