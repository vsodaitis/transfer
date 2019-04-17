package com.vytenis.transfer.resource;

import com.vytenis.transfer.dto.Payment;
import com.vytenis.transfer.dto.TransferRequest;
import com.vytenis.transfer.service.MoneyTransferService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/transfer")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MoneyTransferResource {

    @Inject
    MoneyTransferService moneyTransferService;

    @POST
    @Path("/topup")
    public Payment topUp(TransferRequest transferRequest) {
        return moneyTransferService.topUp(
                transferRequest.getBeneficiary(),
                transferRequest.getSum(),
                transferRequest.getCurrency());
    }

    @POST
    public Payment transfer(TransferRequest transferRequest) {
        return moneyTransferService.transfer(
                transferRequest.getDebtor(),
                transferRequest.getBeneficiary(),
                transferRequest.getSum(),
                transferRequest.getCurrency());
    }
}
