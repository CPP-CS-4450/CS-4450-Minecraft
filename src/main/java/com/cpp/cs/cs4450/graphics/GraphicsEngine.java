/***************************************************************
 * file: GraphicsEngine.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Interface that defines how a Graphics Engine should behave
 *
 ****************************************************************/
package com.cpp.cs.cs4450.graphics;

public interface GraphicsEngine {

    /*
    Renders objects to the display
     */
    void render();

    /*
    Shuts down the graphics
     */
    void shutdown();

    /*
    Sets the perspective of the view
     */
    void setPerspective(float x, float y, float z, float pitch, float yaw);

    /*
    Updates the graphics shown on screen
     */
    void update();

}
