package com.vytenis.transfer.service;

import com.vytenis.transfer.dao.AccountStatus;
import com.vytenis.transfer.dao.PaymentStatus;
import com.vytenis.transfer.dto.Account;
import com.vytenis.transfer.dto.Payment;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ApplicationScoped
public class MoneyTransferService {

    private final ConcurrentMap<Account, Lock> lockMap = new ConcurrentHashMap<>();

    @Inject
    AccountService accountService;

    @Inject
    BalanceService balanceService;

    @Inject
    PaymentHistoryService paymentHistoryService;

    public Payment addMoney(String iban, BigDecimal sum) {
        Account account = accountService.findByIban(iban).orElseThrow(NoSuchElementException::new);
        Payment payment = new Payment();
        payment.setBeneficiary(account);
        payment.setSum(sum);
        Lock lock = lockMap.computeIfAbsent(account, acc -> new ReentrantLock());
        lock.lock();
        try {
            payment.setDate(LocalDateTime.now());
            validateAccount(account);
            balanceService.add(account.getBalance(), sum);
            payment.setStatus(PaymentStatus.COMPLETED);
        } catch (IllegalArgumentException e) {
            payment.setStatus(PaymentStatus.DECLINED);
        } finally {
            lock.unlock();
            lockMap.remove(account);
        }
        payment.setId(paymentHistoryService.addPayment(payment));
        return payment;
    }

    private void validateAccount(Account account) {
        if (account.getStatus() != AccountStatus.ACTIVE) {
            throw new IllegalArgumentException("Account is not active");
        }
    }
}
