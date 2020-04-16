/***************************************************************
 * file: FirstPersonCameraController.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: The first person view camera used to act as the players point of view
 *
 ****************************************************************/
package com.cpp.cs.cs4450.camera;

import com.cpp.cs.cs4450.model.GameAreaEntity;
import com.cpp.cs.cs4450.model.Movable;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.BoundBox;
import com.cpp.cs.cs4450.util.Bounded;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

/**
 * Implementation of the CameraController interface that uses
 * a first person perspective for it's camera
 */
public class FirstPersonCameraController implements CameraController, GameAreaEntity, Movable, Bounded {
    /**
     * Camera's horizontal yaw delta
     */
    private static final float HORIZONTAL_DYAW = 90.0f;
    /**
     * Camera's width
     */
    private static final double WIDTH = 0.2;
    /**
     * Camera's height
     */
    private static final double HEIGHT = 0.3;
    /**
     * Camera's depth
     */
    private static final double DEPTH = 0.2;

    /**
     * Camera's position vector
     */
    private final Vector3f position;
    /**
     * Camera's yaw value
     */
    private float yaw;
    /**
     * Cameras pitch value
     */
    private float pitch;

    /**
     * Default Constructor
     */
    public FirstPersonCameraController(){
        this(0,0,0);
    }

    /**
     * Constructor
     *
     * @param vector start position
     */
    public FirstPersonCameraController(final ReadableVector3f vector){
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Constructor
     *
     * @param x start x position
     * @param y start y position
     * @param z start z position
     */
    public FirstPersonCameraController(final float x, final float y, final float z){
        this.position = new Vector3f(x, y, z);
        this.yaw = 0.0f;
        this.pitch = 0.0f;
    }

    /**
     * Changes camera's yaw
     *
     * @param change Amount to change
     * @return Camera's new yaw value
     */
    @Override
    public float yaw(final float change) {
        return yaw += change;
    }

    /**
     * Changes camera's yaw
     *
     * @param change Amount to change
     * @return Camera's new pitch value
     */
    @Override
    public float pitch(final float change) {
        return pitch -= change;
    }

    /**
     * Moves the object up
     *
     * @param distance amount to move
     */
    @Override
    public void moveUp(final float distance) {
        position.setY(position.y - distance);
    }

    /**
     * Moves the object down
     *
     * @param distance amount to move
     */
    @Override
    public void moveDown(final float distance) {
        position.setY(position.y + distance);
    }

    /**
     * Moves the object left
     *
     * @param distance amount to move
     */
    @Override
    public void moveLeft(final float distance) {
        final float xOffset = calculateXOffset(distance, yaw - HORIZONTAL_DYAW);
        final float zOffset = calculateZOffset(distance, yaw - HORIZONTAL_DYAW);

        position.setX(position.x - xOffset);
        position.setZ(position.z + zOffset);
    }

    /**
     * Moves the object right
     *
     * @param distance amount to move
     */
    @Override
    public void moveRight(final float distance) {
        final float xOffset = calculateXOffset(distance, yaw + HORIZONTAL_DYAW);
        final float zOffset = calculateZOffset(distance, yaw + HORIZONTAL_DYAW);

        position.setX(position.x - xOffset);
        position.setZ(position.z + zOffset);
    }

    /**
     * Moves the object forward
     *
     * @param distance amount to move
     */
    @Override
    public void moveForward(final float distance) {
        final float xOffset = calculateXOffset(distance, yaw);
        final float zOffset = calculateZOffset(distance, yaw);

        position.setX(position.x - xOffset);
        position.setZ(position.z + zOffset);
    }

    /**
     * Moves the object backwards
     *
     * @param distance amount to move
     */
    @Override
    public void moveBackwards(final float distance) {
        final float xOffset = calculateXOffset(distance, yaw);
        final float zOffset = calculateZOffset(distance, yaw);

        position.setX(position.x + xOffset);
        position.setZ(position.z - zOffset);
    }

    /**
     * Sets up to see perspective through camera
     */
    @Override
    public void look() {
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(position.getX(), position.getY(), position.getZ());
    }

    /**
     * Getter for camera's pitch
     *
     * @return camera's pitch
     */
    @Override
    public float getPitch() {
        return pitch;
    }

    /**
     * Getter for camera's yaw
     *
     * @return camera's yaw
     */
    @Override
    public float getYaw() {
        return yaw;
    }

    /**
     * Getter for the object's x position
     *
     * @return x position
     */
    @Override
    public float getPositionX() {
        return position.getX();
    }

    /**
     * Getter for the object's y position
     *
     * @return x position
     */
    @Override
    public float getPositionY() {
        return position.getY();
    }

    /**
     * Getter for the object's z position
     *
     * @return x position
     */
    @Override
    public float getPositionZ() {
        return position.getZ();
    }

    /**
     * Getter for the object's position
     *
     * @return position vector
     */
    @Override
    public ReadableVector3f getPosition3f() {
        return position;
    }

    /**
     * Calculates camera's x offset when moved
     *
     * @param distance distance moved
     * @param offset offset delta value
     * @return x offset
     */
    private static float calculateXOffset(final float distance, final float offset){
        return (float) (distance * Math.sin(Math.toRadians(offset)));
    }

    /**
     * Calculates camera's z offset when moved
     *
     * @param distance distance moved
     * @param offset offset delta value
     * @return z offset
     */
    private static float calculateZOffset(final float distance, final float offset){
        return (float) (distance * Math.cos(Math.toRadians(offset)));
    }

    /**
     * Override of Objects toString() method.
     *
     * @return String representation of camera
     */
    @Override
    public String toString(){
        return  "\nFirst Person Camera" +
                "\nX:\t" + getPositionX() +
                "\nY:\t" + getPositionY() +
                "\nZ:\t" + getPositionZ();
    }

    /**
     * Getter for object's bounds
     *
     * @return Bounds for this object
     */
    @Override
    public Bound getBounds() {
        return new BoundBox(calcMin(position.x, WIDTH), calcMin(position.y, HEIGHT), calcMin(position.z, DEPTH), WIDTH, HEIGHT, DEPTH);
    }

    /**
     * Getter for object's bounds width
     *
     * @return Bounds width of object
     */
    @Override
    public double getWidth() {
        return WIDTH;
    }

    /**
     * Getter for object's bounds height
     *
     * @return Bounds height of object
     */
    @Override
    public double getHeight() {
        return HEIGHT;
    }

    /**
     * Getter for object's bounds depth
     *
     * @return Bounds depth of object
     */
    @Override
    public double getDepth() {
        return DEPTH;
    }

    /**
     * Calculates the min axis value
     *
     * @param d axis size value
     * @param n total axis size value
     * @return min value of axis
     */
    private static double calcMin(double d, double n){
        return d - (n / 2);
    }

}
