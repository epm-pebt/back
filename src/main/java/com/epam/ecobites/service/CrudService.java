package com.epam.ecobites.service;

import java.util.List;

public interface CrudService<T, R> {
    T create(T entity);
    T get(R id);
    List<T> getAll();
    T update(R id, T entity);
    void delete(R id);
}
