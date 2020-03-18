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

    public void shutdown(){
        ui.shutdown();
        graphicsEngine.shutdown();
        System.exit(0);
    }

}
