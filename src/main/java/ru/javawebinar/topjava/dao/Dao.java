package ru.javawebinar.topjava.dao;


import java.util.List;

public interface Dao<T, E> {
    T add(T elem);

    T update(T elem);

    T delete(int id);

    T getById(int id);

    List<E> getList();
}
