/***************************************************************
 * file: Bounded.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: This interface defines an object that has bounds to be used for collision detection
 *
 ****************************************************************/
package com.cpp.cs.cs4450.util;

/**
 * Interface that defines behavior for an object
 * that has bounds. This is used for collision detection.
 */
public interface Bounded {

    /**
     * Getter for object's bounds
     *
     * @return Bounds of object
     */
    Bound getBounds();

    /**
     * Getter for object's bounds width
     *
     * @return Bounds width of object
     */
    double getWidth();

    /**
     * Getter for object's bounds height
     *
     * @return Bounds height of object
     */
    double getHeight();

    /**
     * Getter for object's bounds depth
     *
     * @return Bounds depth of object
     */
    double getDepth();

}
