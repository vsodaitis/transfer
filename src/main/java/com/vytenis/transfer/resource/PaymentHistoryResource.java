package com.vytenis.transfer.resource;

import com.vytenis.transfer.dto.Account;
import com.vytenis.transfer.dto.Payment;
import com.vytenis.transfer.service.PaymentHistoryService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/history")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentHistoryResource {

    @Inject
    PaymentHistoryService paymentHistoryService;

    @GET
    public List<Payment> getAllPayments() {
        return paymentHistoryService.getAllPayments();
    }

    @POST
    @Path("/beneficiary")
    public List<Payment> getByBeneficiary(Account account) {
        return paymentHistoryService.getByBeneficiary(account);
    }

    @POST
    @Path("/debtor")
    public List<Payment> getByDebtor(Account account) {
        return paymentHistoryService.getByDebtor(account);
    }
}
