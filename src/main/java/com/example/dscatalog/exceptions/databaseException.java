package com.example.dscatalog.exceptions;

public class databaseException extends RuntimeException{
    private static final long  serialVersionUID = 1L;

    public databaseException(String msg)
    {
        super(msg);
    }

}
