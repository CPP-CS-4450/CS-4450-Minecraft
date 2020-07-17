package com.cpp.cs.cs4450.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class KeyboardMouseUserInput implements UserInput {


    public KeyboardMouseUserInput(){
        try {
            if(!Keyboard.isCreated()) Keyboard.create();
            Keyboard.enableRepeatEvents(true);

            if(!Mouse.isCreated()) Mouse.create();
            Mouse.setGrabbed(true);
        } catch (LWJGLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }


    @Override
    public boolean up() {
        return Keyboard.isKeyDown(Keyboard.KEY_SPACE);
    }

    @Override
    public boolean down() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
    }

    @Override
    public boolean left() {
        return Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT);
    }

    @Override
    public boolean right() {
        return Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
    }

    @Override
    public boolean forward() {
        return Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP);
    }

    @Override
    public boolean backward() {
        return Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);
    }

    @Override
    public float horizontalDelta() {
        return Mouse.getDX();
    }

    @Override
    public float verticalDelta() {
        return Mouse.getDY();
    }

    @Override
    public boolean quit() {
        return Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
    }

    @Override
    public boolean invert() {
        while (Keyboard.next()){
            if(Keyboard.getEventKey() == Keyboard.KEY_I){
                return Keyboard.getEventKeyState();
            }
        }

        return false;
    }
}
