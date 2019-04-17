package com.vytenis.transfer.service;

import com.vytenis.transfer.converters.AccountConverter;
import com.vytenis.transfer.converters.PaymentConverter;
import com.vytenis.transfer.dao.PaymentEntity;
import com.vytenis.transfer.dto.Account;
import com.vytenis.transfer.dto.Payment;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PaymentHistoryService {

    @Inject
    PaymentConverter paymentConverter;

    @Inject
    AccountConverter accountConverter;

    @Transactional
    public long addPayment(Payment payment) {
        PaymentEntity paymentEntity = paymentConverter.convertToEntity(payment);
        paymentEntity.persist();
        return paymentEntity.id;
    }

    public List<Payment> getAllPayments() {
        return PaymentEntity.<PaymentEntity>streamAll(Sort.descending("date"))
                .map(paymentConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    public List<Payment> getByBeneficiary(Account beneficiary) {
        return getByField("beneficiary", accountConverter.convertToEntity(beneficiary));
    }

    public List<Payment> getByDebtor(Account debtor) {
        return getByField("debtor", accountConverter.convertToEntity(debtor));
    }

    private List<Payment> getByField(String field, Object object) {
        return PaymentEntity.<PaymentEntity>stream(field, object)
                .map(paymentConverter::convertFromEntity)
                .collect(Collectors.toList());
    }
}
