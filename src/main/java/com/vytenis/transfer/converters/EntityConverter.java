package com.vytenis.transfer.converters;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

public interface EntityConverter<E extends PanacheEntity, O> {

    E convertToEntity(O object);

    O convertFromEntity(E entity);
}
