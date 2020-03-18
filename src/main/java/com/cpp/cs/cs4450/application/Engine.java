package com.cpp.cs.cs4450.application;

import com.cpp.cs.cs4450.camera.CameraController;
import com.cpp.cs.cs4450.graphics.GraphicsEngine;
import com.cpp.cs.cs4450.ui.UserInterface;
import com.cpp.cs.cs4450.util.CollisionDetector;


public final class Engine {
    private static final float MOVEMENT_SPEED = 0.1f;
    private static final float MOUSE_SENSITIVITY = 0.09f;

    private final UserInterface ui;
    private final GraphicsEngine graphicsEngine;
    private final CameraController camera;
    private final CollisionDetector collisionDetector;


    public Engine(final UserInterface ui, final GraphicsEngine graphicsEngine, final CameraController camera, final CollisionDetector collisionDetector) {
        this.ui = ui;
        this.graphicsEngine = graphicsEngine;
        this.camera = camera;
        this.collisionDetector = collisionDetector;
    }

    public void run(){
        final double cw = camera.getWidth(), ch = camera.getHeight(), cd = camera.getDepth();
        while(!ui.quit()) {
            final float dx = ui.getMouseHorizontalChange();
            final float dy = ui.getMouseVerticalChange();

            camera.yaw(dx * MOUSE_SENSITIVITY);
            camera.pitch(dy * MOUSE_SENSITIVITY);

            final float x0 = camera.getPositionX(), y0 = camera.getPositionY(), z0 = camera.getPositionZ();

            if (ui.up() && !collisionDetector.collision(x0, y0 - MOVEMENT_SPEED, z0, cw, ch, cd)) {
                camera.moveUp(MOVEMENT_SPEED);
            }

            if (ui.down() && !collisionDetector.collision(x0, y0 + MOVEMENT_SPEED, z0, cw, ch, cd)) {
                camera.moveDown(MOVEMENT_SPEED);

            }

            if (ui.left() && !collisionDetector.collision(x0 - camera.xHorizontalOffset(MOVEMENT_SPEED, -1), y0, z0 + camera.zHorizontalOffset(MOVEMENT_SPEED, -1), cw, ch, cd)) {
                camera.moveLeft(MOVEMENT_SPEED);

            }

            if (ui.right() && !collisionDetector.collision(x0 - camera.xHorizontalOffset(MOVEMENT_SPEED, 1), y0, z0 + camera.zHorizontalOffset(MOVEMENT_SPEED, 1), cw, ch, cd)) {
                camera.moveRight(MOVEMENT_SPEED);

            }

            if (ui.forward() && !collisionDetector.collision(x0 + camera.xApplicateOffset(MOVEMENT_SPEED, -1), y0, z0 + camera.zApplicateOffset(MOVEMENT_SPEED, 1), cw, ch, cd)) {
                camera.moveForward(MOVEMENT_SPEED);
            }

            if (ui.backward() && !collisionDetector.collision(x0 + camera.xApplicateOffset(MOVEMENT_SPEED, 1), y0, z0 + camera.zApplicateOffset(MOVEMENT_SPEED, -1), cw, ch, cd)) {
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

    public void shutdown(){
        ui.shutdown();
        graphicsEngine.shutdown();
        System.exit(0);
    }

}
