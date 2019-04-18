package com.vytenis.transfer.resource;

import com.vytenis.transfer.dto.Account;
import com.vytenis.transfer.dto.User;
import com.vytenis.transfer.service.AccountService;
import com.vytenis.transfer.service.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountsResource {

    @Inject
    UserService userService;

    @Inject
    AccountService accountService;

    @GET
    @Path("/{userId}")
    public List<Account> getAccounts(@PathParam("userId") Long userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return accountService.getUserAccounts(user);
    }

    @POST
    public long addAccount(Account account) {
        return accountService.addUserAccount(account);
    }
}
