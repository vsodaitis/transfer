package com.vytenis.transfer.converters;

import com.vytenis.transfer.dao.AccountEntity;
import com.vytenis.transfer.dto.Account;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AccountConverter implements EntityConverter<AccountEntity, Account> {

    @Inject
    UserConverter userConverter;

    @Inject
    BalanceConverter balanceConverter;

    @Override
    public AccountEntity convertToEntity(Account object) {
        if (object == null) {
            return null;
        }

        AccountEntity entity = new AccountEntity();
        entity.id = object.getId();
        entity.balance = balanceConverter.convertToEntity(object.getBalance());
        entity.currency = object.getCurrency();
        entity.iban = object.getIban();
        entity.status = object.getStatus();
        entity.user = userConverter.convertToEntity(object.getUser());
        return entity;
    }

    @Override
    public Account convertFromEntity(AccountEntity entity) {
        if (entity == null) {
            return null;
        }

        Account account = new Account();
        account.setId(entity.id);
        account.setBalance(balanceConverter.convertFromEntity(entity.balance));
        account.setCurrency(entity.currency);
        account.setIban(entity.iban);
        account.setStatus(entity.status);
        account.setUser(userConverter.convertFromEntity(entity.user));
        return account;
    }
}
