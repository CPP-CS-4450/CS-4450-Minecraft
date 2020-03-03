package com.cpp.cs.cs4450.graphics;

public class GraphicsException extends RuntimeException {

    public GraphicsException(final String e){
        super(e);
    }

    public GraphicsException(final Exception e){
        super(e);
    }

}
