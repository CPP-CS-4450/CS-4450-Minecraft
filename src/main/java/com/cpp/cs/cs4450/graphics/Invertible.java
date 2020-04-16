/***************************************************************
 * file: Invertible.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Defines an object that can be inverted
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

/**
 * Interface for object that can be inverted in the program
 */
public interface Invertible {

    /**
     * Inverts the object
     */
    void invert();

    /**
     * Checks if object is currently inverted
     *
     * @return True if inverted, false otherwise
     */
    boolean isInverted();

}
