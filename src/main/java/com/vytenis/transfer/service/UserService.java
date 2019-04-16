package com.vytenis.transfer.service;

import com.vytenis.transfer.dao.User;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class UserService {

    public List<User> getAllUsers() {
        return User.listAll();
    }

    public User getUser(long id) {
        return User.findById(id);
    }

    public long addUser(User user) {
        user.persist();
        return user.id;
    }
}
