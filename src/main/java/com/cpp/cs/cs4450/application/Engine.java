/***************************************************************
 * file: Engine.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 1
 * date last modified: 3/05/2020
 *
 * purpose: Class responsible for running the game loop
 *
 ****************************************************************/
package com.cpp.cs.cs4450.application;

import com.cpp.cs.cs4450.camera.CameraController;
import com.cpp.cs.cs4450.graphics.GraphicsEngine;
import com.cpp.cs.cs4450.ui.UserInterface;


public final class Engine {
    private static final float MOVEMENT_SPEED = 0.35f;
    private static final float MOUSE_SENSITIVITY = 0.09f;

    private final UserInterface ui;
    private final GraphicsEngine graphicsEngine;
    private final CameraController camera;


    public Engine(final UserInterface ui, final GraphicsEngine graphicsEngine, final CameraController camera) {
        this.ui = ui;
        this.graphicsEngine = graphicsEngine;
        this.camera = camera;
    }

    //Runs the games loop so that the cube is rendered
    public void run(){
        while(!ui.quit()){
            final float dx = ui.getMouseHorizontalChange();
            final float dy = ui.getMouseVerticalChange();

            camera.yaw(dx * MOUSE_SENSITIVITY);
            camera.pitch(dy * MOUSE_SENSITIVITY);

            if(ui.up()){
               camera.moveUp(MOVEMENT_SPEED);
            }

            if(ui.down()){
                camera.moveDown(MOVEMENT_SPEED);
            }

            if(ui.left()){
                camera.moveLeft(MOVEMENT_SPEED);
            }

            if(ui.right()){
                camera.moveRight(MOVEMENT_SPEED);
            }

            if(ui.forward()){
                camera.moveForward(MOVEMENT_SPEED);
            }

            if(ui.backward()){
                camera.moveBackwards(MOVEMENT_SPEED);
            }

            camera.look();

            graphicsEngine.render();
        }

        shutdown();
    }

    //Shuts down the program
    public void shutdown(){
        ui.shutdown();
        graphicsEngine.shutdown();
        System.exit(0);
    }

}
