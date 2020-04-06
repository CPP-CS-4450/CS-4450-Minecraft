/***************************************************************
 * file: LWJGLUserInterface.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Implementaion of UserInterface that uses LWJGL
 *
 ****************************************************************/
package com.cpp.cs.cs4450.ui;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class LWJGLUserInterface implements UserInterface {

    public LWJGLUserInterface() {
        try {
            Keyboard.create();
            Keyboard.enableRepeatEvents(true);
            Mouse.create();
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
    public boolean quit() {
        return Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
    }

    @Override
    public float getMouseHorizontalChange() {
        return Mouse.getDX();
    }

    @Override
    public float getMouseVerticalChange() {
        return Mouse.getDY();
    }

    @Override
    public void shutdown(){
        Keyboard.destroy();
        Mouse.destroy();
    }

}
