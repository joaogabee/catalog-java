package com.example.dscatalog.exceptions;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
@Data
public class StandardErrors implements Serializable
{
    @Serial
    private static final long  serialVersionUID = 1L;

    private Instant timestamp;
    private Integer status;
    private String error;
    private String Message;
    private String path;

    public StandardErrors() {

    }
}
