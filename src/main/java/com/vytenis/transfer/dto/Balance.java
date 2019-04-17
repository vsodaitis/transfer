package com.vytenis.transfer.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class Balance {

    private Long id;
    private BigDecimal total;
    private BigDecimal reserved;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getReserved() {
        return reserved;
    }

    public void setReserved(BigDecimal reserved) {
        this.reserved = reserved;
    }

    public BigDecimal getAvailable() {
        return Objects.requireNonNullElse(total, BigDecimal.ZERO)
                .subtract(Objects.requireNonNullElse(reserved, BigDecimal.ZERO));
    }
}
