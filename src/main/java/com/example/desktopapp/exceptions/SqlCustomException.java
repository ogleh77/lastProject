package com.example.desktopapp.exceptions;

import java.sql.SQLException;

public class SqlCustomException extends SQLException {

    public SqlCustomException(String message) {
        super(message);
    }
}
