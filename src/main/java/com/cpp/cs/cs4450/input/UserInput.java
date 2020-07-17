package com.cpp.cs.cs4450.input;

public interface UserInput {

    boolean up();

    boolean down();

    boolean left();

    boolean right();

    boolean forward();

    boolean backward();

    float horizontalDelta();

    float verticalDelta();

    boolean quit();

    boolean invert();

}
