package com.vytenis.transfer.service;

import com.vytenis.transfer.dao.BalanceEntity;
import com.vytenis.transfer.dto.Balance;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@ApplicationScoped
public class BalanceService {

    @Transactional
    public void add(Balance balance, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Can not add negative sum");
        }

        BalanceEntity balanceEntity = BalanceEntity.findById(balance.getId());
        balanceEntity.total = balanceEntity.total.add(amount);
    }

    @Transactional
    public void subtract(Balance balance, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Can not subtract negative sum");
        }

        if (amount.compareTo(balance.getAvailable()) > 0) {
            throw new IllegalArgumentException("Not enough available funds");
        }

        BalanceEntity balanceEntity = BalanceEntity.findById(balance.getId());
        balanceEntity.total = balanceEntity.total.subtract(amount);
        if (!balanceEntity.isPersistent()) {
            balanceEntity.persist();
        }
    }
}
