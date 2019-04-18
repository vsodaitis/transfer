package com.vytenis.transfer.service;

import com.vytenis.transfer.dao.AccountStatus;
import com.vytenis.transfer.dao.PaymentStatus;
import com.vytenis.transfer.dto.Account;
import com.vytenis.transfer.dto.Payment;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class MoneyTransferService {

    private static final Logger log = Logger.getAnonymousLogger();

    private final ConcurrentMap<Account, Lock> lockMap = new ConcurrentHashMap<>();

    @Inject
    AccountService accountService;

    @Inject
    BalanceService balanceService;

    @Inject
    PaymentHistoryService paymentHistoryService;

    public Payment topUp(String iban, BigDecimal sum, String currency) {
        Account account = accountService.findByIban(iban).orElseThrow(NoSuchElementException::new);
        return transfer(null, account, sum, currency);
    }

    public Payment transfer(String debtorIban, String beneficiaryIban, BigDecimal sum, String currency) {
        Account debtor = accountService.findByIban(debtorIban).orElseThrow(NoSuchElementException::new);
        Account beneficiary = accountService.findByIban(beneficiaryIban).orElseThrow(NoSuchElementException::new);
        return transfer(debtor, beneficiary, sum, currency);
    }

    private Payment transfer(Account debtor, Account beneficiary, BigDecimal sum, String currency) {
        Payment payment = new Payment();
        payment.setDebtor(debtor);
        payment.setBeneficiary(beneficiary);
        payment.setSum(sum);
        payment.setCurrency(currency);
        List<Account> accounts = Stream.of(debtor, beneficiary)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        List<Lock> locks = accounts.stream()
                .map(account -> lockMap.computeIfAbsent(account, acc -> new ReentrantLock()))
                .collect(Collectors.toList());
        locks.forEach(Lock::lock);
        try {
            payment.setDate(LocalDateTime.now());
            accounts.stream()
                    .peek(this::validateAccount)
                    .forEach(account -> validateCurrency(account, currency));
            if (debtor != null) {
                balanceService.subtract(debtor.getBalance(), sum);
            }
            balanceService.add(beneficiary.getBalance(), sum);
            payment.setStatus(PaymentStatus.COMPLETED);
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            payment.setStatus(PaymentStatus.DECLINED);
        } finally {
            locks.forEach(Lock::unlock);
            accounts.forEach(lockMap::remove);
        }
        return paymentHistoryService.addPayment(payment);
    }

    private void validateAccount(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Account is not active");
        }
    }

    private void validateCurrency(Account account, String currency) {
        if (currency == null || !currency.equals(account.getCurrency())) {
            throw new IllegalArgumentException("Wrong currency in payment");
        }
    }
}
