package com.vytenis.transfer.converters;

import com.vytenis.transfer.dao.BalanceEntity;
import com.vytenis.transfer.dto.Balance;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BalanceConverter implements EntityConverter<BalanceEntity, Balance> {

    @Override
    public BalanceEntity convertToEntity(Balance object) {
        if (object == null) {
            return null;
        }

        BalanceEntity balanceEntity = new BalanceEntity();
        balanceEntity.id = object.getId();
        balanceEntity.reserved = object.getReserved();
        balanceEntity.total = object.getTotal();
        return balanceEntity;
    }

    @Override
    public Balance convertFromEntity(BalanceEntity entity) {
        if (entity == null) {
            return null;
        }

        Balance balance = new Balance();
        balance.setId(entity.id);
        balance.setReserved(entity.reserved);
        balance.setTotal(entity.total);
        return balance;
    }
}
