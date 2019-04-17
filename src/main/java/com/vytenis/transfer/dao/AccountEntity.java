package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class AccountEntity extends PanacheEntity {

    @ManyToOne
    public UserEntity user;

    public AccountStatus status;

    @OneToOne
    public BalanceEntity balance;

    public String iban;

    public String currency;
}
