package com.vytenis.transfer.service;

import com.vytenis.transfer.converters.AccountConverter;
import com.vytenis.transfer.converters.UserConverter;
import com.vytenis.transfer.dao.AccountEntity;
import com.vytenis.transfer.dao.BalanceEntity;
import com.vytenis.transfer.dto.Account;
import com.vytenis.transfer.dto.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class AccountService {

    @Inject
    UserConverter userConverter;

    @Inject
    AccountConverter accountConverter;

    public List<Account> getUserAccounts(User user) {
        return AccountEntity.<AccountEntity>stream("user", userConverter.convertToEntity(user))
                .map(accountConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public long addUserAccount(Account account) {
        AccountEntity accountEntity = accountConverter.convertToEntity(account);
        BalanceEntity balanceEntity = accountEntity.balance;
        balanceEntity.persist();
        accountEntity.persist();
        return accountEntity.id;
    }

    public Optional<Account> findByIban(String iban) {
        try {
            return Optional.of(accountConverter.convertFromEntity(AccountEntity.find("iban", iban).singleResult()));
        } catch (NoResultException | NonUniqueResultException e) {
            return Optional.empty();
        }
    }
}
