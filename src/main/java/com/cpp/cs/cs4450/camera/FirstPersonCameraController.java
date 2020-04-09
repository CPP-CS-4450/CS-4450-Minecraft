package com.cpp.cs.cs4450.camera;

import com.cpp.cs.cs4450.model.GameAreaEntity;
import com.cpp.cs.cs4450.model.Movable;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.BoundBox;
import com.cpp.cs.cs4450.util.Bounded;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

public class FirstPersonCameraController implements CameraController, GameAreaEntity, Movable, Bounded {
    private static final float HORIZONTAL_DYAW = 90.0f;
    private static final double WIDTH = 0.2;
    private static final double HEIGHT = 0.3;
    private static final double DEPTH = 0.2;

    private final Vector3f position;
    private float yaw;
    private float pitch;

    public FirstPersonCameraController(){
        this(0,0,0);
    }

    public FirstPersonCameraController(final CameraController cameraController){
        this(cameraController.getPosition3f());
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
    public float yaw(final float change) {
        return yaw += change;
    }

    @Override
    public float pitch(final float change) {
        return pitch -= change;
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
    public float getPitch() {
        return pitch;
    }

    @Override
    public float getYaw() {
        return yaw;
    }

    @Override
    public float getPositionX() {
        return position.getX();
    }

    @Override
    public float getPositionY() {
        return position.getY();
    }

    @Override
    public float getPositionZ() {
        return position.getZ();
    }

    @Override
    public ReadableVector3f getPosition3f() {
        return position;
    }

    private static float calculateXOffset(final float distance, final float offset){
        return (float) (distance * Math.sin(Math.toRadians(offset)));
    }

    private static float calculateZOffset(final float distance, final float offset){
        return (float) (distance * Math.cos(Math.toRadians(offset)));
    }

    @Override
    public String toString(){
        return  "\nFirst Person Camera" +
                "\nX:\t" + getPositionX() +
                "\nY:\t" + getPositionY() +
                "\nZ:\t" + getPositionZ();
    }

    @Override
    public Bound getBounds() {
        return new BoundBox(calcMin(position.x, WIDTH), calcMin(position.y, HEIGHT), calcMin(position.z, DEPTH), WIDTH, HEIGHT, DEPTH);
    }

    @Override
    public double getWidth() {
        return WIDTH;
    }

    @Override
    public double getHeight() {
        return HEIGHT;
    }

    @Override
    public double getDepth() {
        return DEPTH;
    }

    private static double calcMin(double d, double n){
        return d - (n / 2);
    }

}
