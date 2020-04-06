/***************************************************************
 * file: Engine.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: The class that is responsible for acting as a controller between all the systems of the program
 *
 ****************************************************************/
package com.cpp.cs.cs4450.application;

import com.cpp.cs.cs4450.camera.CameraController;
import com.cpp.cs.cs4450.graphics.GraphicsEngine;
import com.cpp.cs.cs4450.ui.UserInterface;

public final class Engine {
    private static final float MOVEMENT_SPEED = 0.1f;
    private static final float MOUSE_SENSITIVITY = 0.09f;

    private final UserInterface ui;
    private final GraphicsEngine graphicsEngine;
    private final CameraController camera;


    public Engine(final UserInterface ui, final GraphicsEngine graphicsEngine, final CameraController camera) {
        this.ui = ui;
        this.graphicsEngine = graphicsEngine;
        this.camera = camera;
    }

    /*
     * This method is responsible for running the game loop and calling on the other systems
     * for the program when needed
     */
    public void run(){
        while(!ui.quit()) {
            final float dx = ui.getMouseHorizontalChange();
            final float dy = ui.getMouseVerticalChange();

            camera.yaw(dx * MOUSE_SENSITIVITY);
            camera.pitch(dy * MOUSE_SENSITIVITY);

            final float x0 = camera.getPositionX(), y0 = camera.getPositionY(), z0 = camera.getPositionZ();

            if (ui.up()) {
                camera.moveUp(MOVEMENT_SPEED);
            }

            if (ui.down()) {
                camera.moveDown(MOVEMENT_SPEED);

            }

            if (ui.left()) {
                camera.moveLeft(MOVEMENT_SPEED);

            }

            if (ui.right()) {
                camera.moveRight(MOVEMENT_SPEED);

            }

            if (ui.forward()) {
                camera.moveForward(MOVEMENT_SPEED);
            }

            if (ui.backward()) {
                camera.moveBackwards(MOVEMENT_SPEED);

            }

            final float x1 = camera.getPositionX(), y1 = camera.getPositionY(), z1 = camera.getPositionZ();

            graphicsEngine.setPerspective(x1, y1, z1, camera.getPitch(), camera.getYaw());
            graphicsEngine.render();
            graphicsEngine.update();
        }
        shutdown();
    }

    /*
     * This Method Shuts down the program.
     */
    public void shutdown(){
        ui.shutdown();
        graphicsEngine.shutdown();
        System.exit(0);
    }

}
