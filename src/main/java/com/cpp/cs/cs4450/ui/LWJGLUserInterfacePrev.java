/***************************************************************
 * file: LWJGLUserInterface.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Interacts with the user and interfaces with the game
 *
 ****************************************************************/
package com.cpp.cs.cs4450.ui;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * UserInterface implementation that uses LWJGL
 */
public class LWJGLUserInterfacePrev implements UserInterface {

    /**
     * Default Constructor
     */
    public LWJGLUserInterfacePrev() {
        try {
            Keyboard.create();
            Keyboard.enableRepeatEvents(true);
            Mouse.create();
            Mouse.setGrabbed(true);
        } catch (LWJGLException e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    /**
     * Signals program to move camera up
     *
     * @return True if user signals to move up, false otherwise
     */
    @Override
    public boolean moveUp() {
        return Keyboard.isKeyDown(Keyboard.KEY_SPACE);
    }

    /**
     * Signals program to move camera down
     *
     * @return True if user signals to move down, false otherwise
     */
    @Override
    public boolean moveDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
    }

    /**
     * Signals program to move camera left
     *
     * @return True if user signals to move left, false otherwise
     */
    @Override
    public boolean moveLeft() {
        return Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT);
    }

    /**
     * Signals program to move camera right
     *
     * @return True if user signals to move right, false otherwise
     */
    @Override
    public boolean moveRight() {
        return Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT);
    }

    /**
     * Signals program to move camera forward
     *
     * @return True if user signals to move forward, false otherwise
     */
    @Override
    public boolean moveForward() {
        return Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP);
    }

    /**
     * Signals program to move camera backward
     *
     * @return True if user signals to move backward, false otherwise
     */
    @Override
    public boolean moveBackward() {
        return Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN);
    }

    /**
     * Signals program to invert
     *
     * @return True if user signals to invert, false otherwise
     */
    @Override
    public boolean invert() {
        while (Keyboard.next()){
            if(Keyboard.getEventKey() == Keyboard.KEY_I){
                return Keyboard.getEventKeyState();
            }
        }

        return false;
    }

    /**
     * Signals program to quit
     *
     * @return True if user signals to quit, false otherwise
     */
    @Override
    public boolean quit() {
        return Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
    }

    /**
     * Getter for horizontal mouse change
     *
     * @return horizontal mouse change amount
     */
    @Override
    public float getHorizontalChange() {
        return Mouse.getDX();
    }

    /**
     * Getter for vertical mouse change
     *
     * @return vertical mouse change amount
     */
    @Override
    public float getVerticalChange() {
        return Mouse.getDY();
    }

    /**
     * Shuts down the UserInterface
     */
    @Override
    public void shutdown(){
        Keyboard.destroy();
        Mouse.destroy();
    }

}
