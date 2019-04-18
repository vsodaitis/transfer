package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class BalanceEntity extends PanacheEntity {

    public BigDecimal total;
    public BigDecimal reserved;

    public BigDecimal getAvailable() {
        BigDecimal first = total == null ? BigDecimal.ZERO : total;
        BigDecimal second = reserved == null ? BigDecimal.ZERO : reserved;
        return first.subtract(second);
    }
}
