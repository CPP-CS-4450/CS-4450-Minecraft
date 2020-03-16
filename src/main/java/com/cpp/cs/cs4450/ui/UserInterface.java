package com.cpp.cs.cs4450.ui;

public interface UserInterface {

    boolean up();

    boolean down();

    boolean left();

    boolean right();

    boolean forward();

    boolean backward();

    boolean quit();

    float getMouseHorizontalChange();

    float getMouseVerticalChange();

    void shutdown();

}
