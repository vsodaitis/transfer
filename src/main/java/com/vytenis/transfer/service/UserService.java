package com.vytenis.transfer.service;

import com.vytenis.transfer.converters.UserConverter;
import com.vytenis.transfer.dao.UserEntity;
import com.vytenis.transfer.dto.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    UserConverter userConverter;

    public List<User> getAllUsers() {
        return UserEntity.<UserEntity>streamAll()
                .map(userConverter::convertFromEntity)
                .collect(Collectors.toList());
    }

    public User getUser(long id) {
        return userConverter.convertFromEntity(UserEntity.findById(id));
    }

    @Transactional
    public long addUser(User user) {
        UserEntity userEntity = userConverter.convertToEntity(user);
        userEntity.persist();
        return userEntity.id;
    }
}
