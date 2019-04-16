package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Balance extends PanacheEntity {

    private BigDecimal total;
    private BigDecimal reserved;

    public BigDecimal getAvailable() {
        return Objects.requireNonNullElse(total, BigDecimal.ZERO)
                .subtract(Objects.requireNonNullElse(reserved, BigDecimal.ZERO));
    }
}
