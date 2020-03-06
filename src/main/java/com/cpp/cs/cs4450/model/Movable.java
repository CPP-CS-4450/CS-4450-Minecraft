/***************************************************************
 * file: Movable.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 1
 * date last modified: 3/05/2020
 *
 * purpose: Interface that allows object to move
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model;

public interface Movable {

    //Moves up
    void moveUp(float distance);

    //Moves down
    void moveDown(float distance);

    //Moves left
    void moveLeft(float distance);

    //Moves right
    void moveRight(float distance);

    //Moves forward
    void moveForward(float distance);

    //Moves backward
    void moveBackwards(float distance);

}
