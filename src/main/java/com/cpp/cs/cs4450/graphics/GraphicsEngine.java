/***************************************************************
 * file: GraphicsEngine.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: This defines a system that is responsible for the program's graphics
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

/**
 * Interface for an object that handles the program's
 * graphics such as rendering
 */
public interface GraphicsEngine {

    /**
     * Renders objects in the program
     */
    void render();

    /**
     * Inverts the program's invertible objects
     */
    void invert();

    /**
     * Shuts down the programs graphics
     */
    void shutdown();

    /**
     * Updates the programs lighting
     *
     * @param x x-axis position
     * @param y y-axis position
     * @param z z-axis position
     */
    void updateLighting(float x, float y, float z);

    /**
     * Sets the programs view perspective to view the rendered graphics
     *
     * @param x x-axis position
     * @param y y-axis position
     * @param z z-axis position
     * @param pitch perspective's pitch value
     * @param yaw perspective's yaw value
     */
    void setPerspective(float x, float y, float z, float pitch, float yaw);

    /**
     * Updates the programs display 
     */
    void update();

}
