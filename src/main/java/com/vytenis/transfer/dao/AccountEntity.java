package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.*;

@Entity
public class AccountEntity extends PanacheEntity {

    @ManyToOne
    public UserEntity user;

    @Enumerated(EnumType.STRING)
    public AccountStatus status;

    @OneToOne
    public BalanceEntity balance;

    public String iban;

    public String currency;
}
