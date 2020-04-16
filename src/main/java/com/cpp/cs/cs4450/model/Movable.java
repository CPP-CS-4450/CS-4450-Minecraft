/***************************************************************
 * file: Movable.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Defines behavior for an object that can move in the game
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model;

/**
 * Interface for an object that can be moved in the game
 */
public interface Movable {

    /**
     * Moves the object up
     *
     * @param distance amount to move
     */
    void moveUp(float distance);

    /**
     * Moves the object down
     *
     * @param distance amount to move
     */
    void moveDown(float distance);

    /**
     * Moves the object left
     *
     * @param distance amount to move
     */
    void moveLeft(float distance);

    /**
     * Moves the object right
     *
     * @param distance amount to move
     */
    void moveRight(float distance);

    /**
     * Moves the object forward
     *
     * @param distance amount to move
     */
    void moveForward(float distance);

    /**
     * Moves the object backwards
     *
     * @param distance amount to move
     */
    void moveBackwards(float distance);

}
