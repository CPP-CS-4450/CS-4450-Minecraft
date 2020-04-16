/***************************************************************
 * file: Engine.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: This class contains all the logic that goes into running the program
 *
 ****************************************************************/

package com.cpp.cs.cs4450.application;

import com.cpp.cs.cs4450.camera.CameraController;
import com.cpp.cs.cs4450.graphics.GraphicsEngine;
import com.cpp.cs.cs4450.ui.UserInterface;
import com.cpp.cs.cs4450.util.CollisionDetector;

/**
 * The Engine class is the main class the contains the logic of the program. It
 * deals with running the game loop as well as acting as a controller between
 * all the different systems of the program.
 */
public final class Engine {
    /**
     * Default camera movement speed
     */
    private static final float MOVEMENT_SPEED = 0.1f;
    /**
     * Default mouse sensitivity
     */
    private static final float MOUSE_SENSITIVITY = 0.09f;

    /**
     * The interface which the engine uses to interact with the user
     */
    private final UserInterface ui;
    /**
     * The system that deals with the programs graphics
     */
    private final GraphicsEngine graphicsEngine;
    /**
     * The programs camera
     */
    private final CameraController camera;
    /**
     * The system used to detect collision
     */
    private final CollisionDetector collisionDetector;

    /**
     * The Engine class constructor
     *
     * @param ui The programs UserInterface implementation
     * @param graphicsEngine The programs GraphicsEngine interface
     * @param camera The programs camera
     * @param collisionDetector The programs collision detecting system
     */
    public Engine(final UserInterface ui, final GraphicsEngine graphicsEngine, final CameraController camera, final CollisionDetector collisionDetector) {
        this.ui = ui;
        this.graphicsEngine = graphicsEngine;
        this.camera = camera;
        this.collisionDetector = collisionDetector;
    }

    /**
     * This is the main game loop that runs until the program is ended
     */
    public final void run(){
        final double cw = camera.getWidth(), ch = camera.getHeight(), cd = camera.getDepth();
        while(!ui.quit()) {
            final float dx = ui.getMouseHorizontalChange();
            final float dy = ui.getMouseVerticalChange();

            camera.yaw(dx * MOUSE_SENSITIVITY);
            camera.pitch(dy * MOUSE_SENSITIVITY);

            final float x0 = camera.getPositionX(), y0 = camera.getPositionY(), z0 = camera.getPositionZ();

            if (ui.up() && collisionDetector.noCollision(x0, y0 - MOVEMENT_SPEED, z0, cw, ch, cd)) {
                camera.moveUp(MOVEMENT_SPEED);
            }

            if (ui.down() && collisionDetector.noCollision(x0, y0 + MOVEMENT_SPEED, z0, cw, ch, cd)) {
                camera.moveDown(MOVEMENT_SPEED);

            }

            if (ui.left() && collisionDetector.noCollision(x0 - camera.xHorizontalOffset(MOVEMENT_SPEED, -1), y0, z0 + camera.zHorizontalOffset(MOVEMENT_SPEED, -1), cw, ch, cd)) {
                camera.moveLeft(MOVEMENT_SPEED);

            }

            if (ui.right() && collisionDetector.noCollision(x0 - camera.xHorizontalOffset(MOVEMENT_SPEED, 1), y0, z0 + camera.zHorizontalOffset(MOVEMENT_SPEED, 1), cw, ch, cd)) {
                camera.moveRight(MOVEMENT_SPEED);

            }

            if (ui.forward() && collisionDetector.noCollision(x0 + camera.xApplicateOffset(MOVEMENT_SPEED, -1), y0, z0 + camera.zApplicateOffset(MOVEMENT_SPEED, 1), cw, ch, cd)) {
                camera.moveForward(MOVEMENT_SPEED);
            }

            if (ui.backward() && collisionDetector.noCollision(x0 + camera.xApplicateOffset(MOVEMENT_SPEED, 1), y0, z0 + camera.zApplicateOffset(MOVEMENT_SPEED, -1), cw, ch, cd)) {
                camera.moveBackwards(MOVEMENT_SPEED);

            }

            if (ui.invert()){
                graphicsEngine.invert();
            }

            final float x1 = camera.getPositionX(), y1 = camera.getPositionY(), z1 = camera.getPositionZ();

            graphicsEngine.updateLighting(x1 - x0, y1 - y0, z1 - z0);
            graphicsEngine.setPerspective(x1, y1, z1, camera.getPitch(), camera.getYaw());
            graphicsEngine.render();
            graphicsEngine.update();
        }
        shutdown();
    }

    /**
     * This method shuts down all the systems of the program before
     * exiting the program itself
     */
    public final void shutdown(){
        ui.shutdown();
        graphicsEngine.shutdown();
        System.exit(0);
    }

}
