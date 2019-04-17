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
import java.util.logging.Logger;

@ApplicationScoped
public class MoneyTransferService {

    private final static Logger log = Logger.getGlobal();

    private final ConcurrentMap<Account, Lock> lockMap = new ConcurrentHashMap<>();

    @Inject
    AccountService accountService;

    @Inject
    BalanceService balanceService;

    @Inject
    PaymentHistoryService paymentHistoryService;

    public Payment topUp(String iban, BigDecimal sum, String currency) {
        Account account = accountService.findByIban(iban).orElseThrow(NoSuchElementException::new);
        Payment payment = new Payment();
        payment.setBeneficiary(account);
        payment.setSum(sum);
        payment.setCurrency(currency);
        Lock lock = lockMap.computeIfAbsent(account, acc -> new ReentrantLock());
        lock.lock();
        try {
            payment.setDate(LocalDateTime.now());
            validateAccount(account);
            validateCurrency(account, currency);
            balanceService.add(account.getBalance(), sum);
            payment.setStatus(PaymentStatus.COMPLETED);
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            payment.setStatus(PaymentStatus.DECLINED);
        } finally {
            lock.unlock();
            lockMap.remove(account);
        }
        payment.setId(paymentHistoryService.addPayment(payment));
        return payment;
    }

    public Payment transfer(String debtorIban, String beneficiaryIban, BigDecimal sum, String currency) {
        Account debtor = accountService.findByIban(debtorIban).orElseThrow(NoSuchElementException::new);
        Account beneficiary = accountService.findByIban(beneficiaryIban).orElseThrow(NoSuchElementException::new);
        Payment payment = new Payment();
        payment.setDebtor(debtor);
        payment.setBeneficiary(beneficiary);
        payment.setSum(sum);
        payment.setCurrency(currency);
        Lock debtorLock = lockMap.computeIfAbsent(debtor, acc -> new ReentrantLock());
        Lock beneficiaryLock = lockMap.computeIfAbsent(beneficiary, acc -> new ReentrantLock());
        debtorLock.lock();
        beneficiaryLock.lock();
        try {
            payment.setDate(LocalDateTime.now());
            validateAccount(debtor);
            validateAccount(beneficiary);
            validateCurrency(debtor, currency);
            validateCurrency(beneficiary, currency);
            balanceService.subtract(debtor.getBalance(), sum);
            balanceService.add(beneficiary.getBalance(), sum);
            payment.setStatus(PaymentStatus.COMPLETED);
        } catch (IllegalArgumentException e) {
            log.info(e.getMessage());
            payment.setStatus(PaymentStatus.DECLINED);
        } finally {
            debtorLock.unlock();
            beneficiaryLock.unlock();
            lockMap.remove(debtorLock);
            lockMap.remove(beneficiaryLock);
        }
        payment.setId(paymentHistoryService.addPayment(payment));
        return payment;
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
