package com.vytenis.transfer.service;

import com.vytenis.transfer.dao.Account;
import com.vytenis.transfer.dao.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class AccountService {

    public List<Account> getUserAccounts(User user) {
        return Account.list("user", user);
    }
}
