package ru.javawebinar.topjava.dao;


import java.util.List;

public interface Dao<T> {
    void add(T elem);

    void update(T elem);

    void delete(T elem);

    T getById(int id);

    List<T> getList();
}
