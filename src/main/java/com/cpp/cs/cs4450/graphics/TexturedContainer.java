/***************************************************************
 * file: TexturedContainer.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Defines behavior for an object that holds textured objects
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

import java.util.List;

/**
 * Interface for object that encapsulates Textured objects
 */
@FunctionalInterface
public interface TexturedContainer {

    /**
     * Getter for container's textured objects
     *
     * @return List of Textured objects
     */
    List<Textured> getTextured();

}
