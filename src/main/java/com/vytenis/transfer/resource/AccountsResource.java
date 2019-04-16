package com.vytenis.transfer.resource;

import com.vytenis.transfer.dao.Account;
import com.vytenis.transfer.dao.User;
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
    private UserService userService;

    @Inject
    private AccountService accountService;

    @GET
    @Path("/{userId}")
    public List<Account> getAccounts(@PathParam("userId") Long userId) {
        User user = userService.getUser(userId);
        return accountService.getUserAccounts(user);
    }
}
