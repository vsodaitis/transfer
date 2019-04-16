package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Account extends PanacheEntity {

    @ManyToOne
    public User user;

    public AccountStatus status;

    @OneToOne
    public Balance balance;

    public String iban;

    public String currency;
}
