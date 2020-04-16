/***************************************************************
 * file: Renderable.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Defines behavior for an object that can be rendered
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

/**
 * Interface for objects to be rendered
 */
@FunctionalInterface
public interface Renderable {

    /**
     * Renders the object
     */
    void render();

}
