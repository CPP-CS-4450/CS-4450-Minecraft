/***************************************************************
 * file: UserInterface.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: System that interfaces between the user and the program
 *
 ****************************************************************/
package com.cpp.cs.cs4450.ui;

/**
 * Interface for the program to interact with the user
 */
public interface UserInterface {

    /**
     * Signals program to move camera up
     *
     * @return True if user signals to move up, false otherwise
     */
    boolean moveUp();

    /**
     * Signals program to move camera down
     *
     * @return True if user signals to move down, false otherwise
     */
    boolean moveDown();

    /**
     * Signals program to move camera left
     *
     * @return True if user signals to move left, false otherwise
     */
    boolean moveLeft();

    /**
     * Signals program to move camera right
     *
     * @return True if user signals to move right, false otherwise
     */
    boolean moveRight();

    /**
     * Signals program to move camera forward
     *
     * @return True if user signals to move forward, false otherwise
     */
    boolean moveForward();

    /**
     * Signals program to move camera backward
     *
     * @return True if user signals to move backward, false otherwise
     */
    boolean moveBackward();

    /**
     * Signals program to invert
     *
     * @return True if user signals to invert, false otherwise
     */
    boolean invert();

    /**
     * Signals program to quit
     *
     * @return True if user signals to quit, false otherwise
     */
    boolean quit();

    /**
     * Getter for horizontal mouse change
     *
     * @return horizontal mouse change amount
     */
    float getHorizontalChange();

    /**
     * Getter for vertical mouse change
     *
     * @return vertical mouse change amount
     */
    float getVerticalChange();

    /**
     * Shuts down the UserInterface
     */
    void shutdown();

}
