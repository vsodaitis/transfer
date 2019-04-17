package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class PaymentEntity extends PanacheEntity {

    @ManyToOne
    public AccountEntity debtor;

    @ManyToOne
    public AccountEntity beneficiary;

    public BigDecimal sum;

    public String currency;

    @Enumerated(EnumType.STRING)
    public PaymentStatus status;

    public LocalDateTime date;
}
