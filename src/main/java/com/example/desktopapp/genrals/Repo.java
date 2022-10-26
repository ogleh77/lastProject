package com.example.desktopapp.genrals;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface Repo<T> extends IConnection {


    void insert(T t) throws SQLException;

    void delete(T t) throws SQLException;

    void update(T t) throws SQLException;

    ObservableList<T> fetchAll() throws SQLException;


}
