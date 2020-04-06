/***************************************************************
 * file: Movable.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Interface defining the behavior of an object that can move.
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model;

public interface Movable {

    /*
    Moves the object up
     */
    void moveUp(float distance);

    /*
    Moves the object down
     */
    void moveDown(float distance);

    /*
    Moves the object left
     */
    void moveLeft(float distance);

    /*
    Moves the object right
     */
    void moveRight(float distance);

    /*
    Moves the object forward
     */
    void moveForward(float distance);

    /*
    Moves the object back
     */
    void moveBackwards(float distance);

}
