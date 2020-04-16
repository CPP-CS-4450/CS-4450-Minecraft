/***************************************************************
 * file: GameAreaEntity.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Interface that defines behavior for an object in the game
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model;

import org.lwjgl.util.vector.ReadableVector3f;

/**
 * Interface for an object that exists in the game's space
 */
public interface GameAreaEntity {

    /**
     * Getter for the object's x position
     *
     * @return x position
     */
    float getPositionX();

    /**
     * Getter for the object's y position
     *
     * @return x position
     */
    float getPositionY();

    /**
     * Getter for the object's z position
     *
     * @return x position
     */
    float getPositionZ();

    /**
     * Getter for the object's position
     *
     * @return position vector
     */
    ReadableVector3f getPosition3f();

}
