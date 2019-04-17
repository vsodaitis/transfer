package com.vytenis.transfer.converters;

import com.vytenis.transfer.dao.PaymentEntity;
import com.vytenis.transfer.dto.Payment;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class PaymentConverter implements EntityConverter<PaymentEntity, Payment> {

    @Inject
    AccountConverter accountConverter;

    @Override
    public PaymentEntity convertToEntity(Payment object) {
        PaymentEntity entity = new PaymentEntity();
        entity.id = object.getId();
        entity.beneficiary = computeIfNonNull(object.getBeneficiary(), accountConverter::convertToEntity);
        entity.debtor = computeIfNonNull(object.getDebtor(), accountConverter::convertToEntity);
        entity.sum = object.getSum();
        entity.currency = object.getCurrency();
        entity.status = object.getStatus();
        entity.date = object.getDate();
        return entity;
    }

    @Override
    public Payment convertFromEntity(PaymentEntity entity) {
        Payment payment = new Payment();
        payment.setId(entity.id);
        payment.setBeneficiary(accountConverter.convertFromEntity(entity.beneficiary));
        payment.setDebtor(accountConverter.convertFromEntity(entity.debtor));
        payment.setSum(entity.sum);
        payment.setCurrency(entity.currency);
        payment.setStatus(entity.status);
        payment.setDate(entity.date);
        return payment;
    }
}
