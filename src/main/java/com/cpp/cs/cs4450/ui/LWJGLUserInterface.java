package com.cpp.cs.cs4450.ui;

import com.cpp.cs.cs4450.input.UserInput;

public class LWJGLUserInterface implements UserInterface {

    private final UserInput input;


    public LWJGLUserInterface(final UserInput input) {
        this.input = input;
    }


    @Override
    public boolean moveUp() {
        return input.up();
    }

    @Override
    public boolean moveDown() {
        return input.down();
    }

    @Override
    public boolean moveLeft() {
        return input.left();
    }

    @Override
    public boolean moveRight() {
        return input.right();
    }

    @Override
    public boolean moveForward() {
        return input.forward();
    }

    @Override
    public boolean moveBackward() {
        return input.backward();
    }

    @Override
    public boolean invert() {
        return input.invert();
    }

    @Override
    public boolean quit() {
        return input.quit();
    }

    @Override
    public float getHorizontalChange() {
        return input.horizontalDelta();
    }

    @Override
    public float getVerticalChange() {
        return input.verticalDelta();
    }

    @Override
    public void shutdown() {

    }

}
