package com.vytenis.transfer.dao;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class UserEntity extends PanacheEntity {

    public String fullName;
}
