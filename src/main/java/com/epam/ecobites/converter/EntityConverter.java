package com.epam.ecobites.converter;

public interface EntityConverter<T, R> {

    T toDTO(R obj);

    R fromDTO(T obj);
}
