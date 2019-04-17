package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class PaymentEntity extends PanacheEntity {

    public AccountEntity debtor;
    public AccountEntity beneficiary;
    public BigDecimal sum;
    public PaymentStatus status;
    public LocalDateTime date;
}
