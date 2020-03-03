package com.cpp.cs.cs4450.ui;

public class UserInterfaceException extends RuntimeException {

    public UserInterfaceException(final String e){
        super(e);
    }

    public UserInterfaceException(final Exception e){
        super(e);
    }

}
