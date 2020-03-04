package com.cpp.cs.cs4450.camera;

import com.cpp.cs.cs4450.model.Movable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

public class FirstPersonCameraController implements CameraController, Movable {
    private static final float HORIZONTAL_DYAW = 90.0f;

    private final Vector3f position;
    private float yaw;
    private float pitch;

    public FirstPersonCameraController(){
        this(0,0,0);
    }

    public FirstPersonCameraController(final ReadableVector3f vector){
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    public FirstPersonCameraController(final float x, final float y, final float z){
        this.position = new Vector3f(x, y, z);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }

    @Override
    public void yaw(final float change) {
        yaw += change;
    }

    @Override
    public void pitch(final float change) {
        pitch -= change;
    }

    @Override
    public void moveUp(final float distance) {
        position.setY(position.y - distance);
    }

    @Override
    public void moveDown(final float distance) {
        position.setY(position.y + distance);
    }

    @Override
    public void moveLeft(final float distance) {
        final float xOffset = calculateXOffset(distance, yaw - HORIZONTAL_DYAW);
        final float zOffset = calculateZOffset(distance, yaw - HORIZONTAL_DYAW);

        position.setX(position.x - xOffset);
        position.setZ(position.z + zOffset);
    }

    @Override
    public void moveRight(final float distance) {
        final float xOffset = calculateXOffset(distance, yaw + HORIZONTAL_DYAW);
        final float zOffset = calculateZOffset(distance, yaw + HORIZONTAL_DYAW);

        position.setX(position.x - xOffset);
        position.setZ(position.z + zOffset);
    }

    @Override
    public void moveForward(final float distance) {
        final float xOffset = calculateXOffset(distance, yaw);
        final float zOffset = calculateZOffset(distance, yaw);

        position.setX(position.x - xOffset);
        position.setZ(position.z + zOffset);
    }

    @Override
    public void moveBackwards(final float distance) {
        final float xOffset = calculateXOffset(distance, yaw);
        final float zOffset = calculateZOffset(distance, yaw);

        position.setX(position.x + xOffset);
        position.setZ(position.z - zOffset);
    }

    @Override
    public void look() {
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(position.getX(), position.getY(), position.getZ());
    }

    @Override
    public float getXPosition() {
        return position.getX();
    }

    @Override
    public float getYPosition() {
        return position.getY();
    }

    @Override
    public float getZPosition() {
        return position.getZ();
    }

    @Override
    public ReadableVector3f getPositionVector() {
        return position;
    }

    private static float calculateXOffset(final float distance, final float offset){
        return (float) (distance * Math.sin(Math.toRadians(offset)));
    }

    private static float calculateZOffset(final float distance, final float offset){
        return (float) (distance * Math.cos(Math.toRadians(offset)));
    }

}
