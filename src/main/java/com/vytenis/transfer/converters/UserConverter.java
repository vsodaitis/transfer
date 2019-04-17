package com.vytenis.transfer.converters;

import com.vytenis.transfer.dao.UserEntity;
import com.vytenis.transfer.dto.User;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserConverter implements EntityConverter<UserEntity, User> {

    @Override
    public UserEntity convertToEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.fullName = user.getFullName();
        return userEntity;
    }

    @Override
    public User convertFromEntity(UserEntity entity) {
        User user = new User();
        user.setId(entity.id);
        user.setFullName(entity.fullName);
        return user;
    }
}
