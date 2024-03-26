package com.example.demo.service;

import java.util.List;

public interface CrudService<T, I> {
    T save(T entity);

    T update(T entity, I id);

    T findOne(I id);

    List<T> findAll();

    boolean delete(I id);
}
