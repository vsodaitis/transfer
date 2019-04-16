package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Account extends PanacheEntity {

    private User user;
    private AccountStatus status;
    private Balance balance;
    private String iban;
    private String currency;
}
