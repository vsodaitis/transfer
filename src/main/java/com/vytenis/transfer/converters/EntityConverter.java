package com.vytenis.transfer.converters;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.util.Objects;
import java.util.function.Function;

public interface EntityConverter<E extends PanacheEntity, O> {

    default <T, R> R computeIfNonNull(T object, Function<T, R> computer) {
        if (Objects.nonNull(object)) {
            return computer.apply(object);
        }
        return null;
    }

    E convertToEntity(O object);

    O convertFromEntity(E entity);
}
