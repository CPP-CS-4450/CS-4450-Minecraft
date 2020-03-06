/***************************************************************
 * file: FirstPersonCameraController.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 1
 * date last modified: 3/05/2020
 *
 * purpose: Class that controls a first person view camera
 *
 ****************************************************************/
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
    }//Changes the yaw

    @Override
    public void pitch(final float change) {
        pitch -= change;
    }//Changes the pitch

    @Override
    public void moveUp(final float distance) {
        position.setY(position.y - distance);
    }//Moves up

    @Override
    public void moveDown(final float distance) {
        position.setY(position.y + distance);
    }//Moves down

    @Override
    public void moveLeft(final float distance) {//Moves left
        final float xOffset = calculateXOffset(distance, yaw - HORIZONTAL_DYAW);
        final float zOffset = calculateZOffset(distance, yaw - HORIZONTAL_DYAW);

        position.setX(position.x - xOffset);
        position.setZ(position.z + zOffset);
    }

    @Override
    public void moveRight(final float distance) {//Moves right
        final float xOffset = calculateXOffset(distance, yaw + HORIZONTAL_DYAW);
        final float zOffset = calculateZOffset(distance, yaw + HORIZONTAL_DYAW);

        position.setX(position.x - xOffset);
        position.setZ(position.z + zOffset);
    }

    @Override
    public void moveForward(final float distance) {//Moves Forward
        final float xOffset = calculateXOffset(distance, yaw);
        final float zOffset = calculateZOffset(distance, yaw);

        position.setX(position.x - xOffset);
        position.setZ(position.z + zOffset);
    }

    @Override
    public void moveBackwards(final float distance) {//Moves Back
        final float xOffset = calculateXOffset(distance, yaw);
        final float zOffset = calculateZOffset(distance, yaw);

        position.setX(position.x + xOffset);
        position.setZ(position.z - zOffset);
    }

    @Override
    public void look() {//Sets up camera to look
        GL11.glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(position.getX(), position.getY(), position.getZ());
    }

    @Override
    public float getXPosition() {
        return position.getX();
    }//Returns camera x position

    @Override
    public float getYPosition() {
        return position.getY();
    }//Returns camera y position

    @Override
    public float getZPosition() {
        return position.getZ();
    }//Returns camera z position

    @Override
    public ReadableVector3f getPositionVector() {
        return position;
    }//Returns camera position

    //calulates x axis offset
    private static float calculateXOffset(final float distance, final float offset){
        return (float) (distance * Math.sin(Math.toRadians(offset)));
    }

    //Calculates z axis offset
    private static float calculateZOffset(final float distance, final float offset){
        return (float) (distance * Math.cos(Math.toRadians(offset)));
    }

}
