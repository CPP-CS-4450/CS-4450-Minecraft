/***************************************************************
 * file: InvertibleContainer.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: This interface defines behavior for any object that could contain invertible objects
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

import java.util.List;

/**
 * Interface that defines behavior for an object that encapsulates
 * Invertible objects
 */
public interface InvertibleContainer {

    /**
     * Inverts all invertible objects in container
     */
    void invert();

    /**
     * Getter for container object's invertibles
     *
     * @return List of Invertibles
     */
    List<Invertible> getInvertibles();

}
