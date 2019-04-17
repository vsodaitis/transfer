package com.vytenis.transfer.resource;

import com.vytenis.transfer.dto.User;
import com.vytenis.transfer.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResource {

    @Inject
    UserService userService;

    @PUT
    public Long addUser(User user) {
        return userService.addUser(user);
    }

    @GET
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
