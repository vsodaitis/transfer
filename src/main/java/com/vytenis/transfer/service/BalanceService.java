package com.vytenis.transfer.service;

import com.vytenis.transfer.converters.BalanceConverter;
import com.vytenis.transfer.dao.BalanceEntity;
import com.vytenis.transfer.dto.Balance;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.math.BigDecimal;

@ApplicationScoped
public class BalanceService {

    @Inject
    BalanceConverter balanceConverter;

    @Transactional
    public void add(Balance balance, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Can not add negative sum");
        }

        BalanceEntity balanceEntity = balanceConverter.convertToEntity(balance);
        balanceEntity.total = balanceEntity.total.add(amount);
        if (!balanceEntity.isPersistent()) {
            balanceEntity.persist();
        }
    }

    @Transactional
    public void subtract(Balance balance, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Can not subtract negative sum");
        }

        if (amount.compareTo(balance.getAvailable()) < 0) {
            throw new IllegalArgumentException("Not enough available funds");
        }

        BalanceEntity balanceEntity = balanceConverter.convertToEntity(balance);
        balanceEntity.total = balanceEntity.total.subtract(amount);
        if (!balanceEntity.isPersistent()) {
            balanceEntity.persist();
        }
    }
}
