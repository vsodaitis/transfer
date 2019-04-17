package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class BalanceEntity extends PanacheEntity {

    public BigDecimal total;
    public BigDecimal reserved;
}
