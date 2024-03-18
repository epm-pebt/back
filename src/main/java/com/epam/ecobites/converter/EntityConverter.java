package com.epam.ecobites.converter;

public interface EntityConverter<T, R> {

    T convertToDTO(R obj);

    R convertToEntity(T obj);
}
