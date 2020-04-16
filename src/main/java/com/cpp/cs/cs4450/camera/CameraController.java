/***************************************************************
 * file: CameraController.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: This Interface defines the behavior of a Camera
 *
 ****************************************************************/
package com.cpp.cs.cs4450.camera;

import com.cpp.cs.cs4450.model.GameAreaEntity;
import com.cpp.cs.cs4450.model.Movable;
import com.cpp.cs.cs4450.util.Bounded;

/**
 * Interface that defines a Camera.
 */
public interface CameraController extends GameAreaEntity, Movable, Bounded {
    /**
     * Default delta yaw value
     */
    float DEFAULT_DYAW = 90.0f;

    /**
     * Changes camera's yaw
     *
     * @param change Amount to change
     * @return Camera's new yaw value
     */
    float yaw(float change);

    /**
     * Changes camera's yaw
     *
     * @param change Amount to change
     * @return Camera's new pitch value
     */
    float pitch(float change);

    /**
     * Sets up to see perspective through camera
     */
    void look();

    /**
     * Getter for camera's pitch
     *
     * @return camera's pitch
     */
    float getPitch();

    /**
     * Getter for camera's yaw
     *
     * @return camera's yaw
     */
    float getYaw();

    /**
     * Default implementation to calculate horizontal x offset
     *
     * @param distance Offset delta
     * @param direction Direction of offset
     * @return Horizontal x offset
     */
    default float xHorizontalOffset(float distance, int direction){
        return (float) (distance * Math.sin(Math.toRadians(getYaw() + (direction * DEFAULT_DYAW))));
    }

    /**
     * Default implementation to calculate horizontal z offset
     *
     * @param distance Offset delta
     * @param direction Direction of offset
     * @return Horizontal z offset
     */
    default float zHorizontalOffset(float distance, int direction){
        return (float) (distance * Math.cos(Math.toRadians(getYaw() + (direction * DEFAULT_DYAW))));
    }

    /**
     * Default implementation to calculate applicate x offset
     *
     * @param distance Offset delta
     * @param direction Direction of offset
     * @return Applicate x offset
     */
    default float xApplicateOffset(float distance, int direction){
        return (float) (direction * (distance * Math.sin(Math.toRadians(getYaw()))));
    }

    /**
     * Default implementation to calculate applicate z offset
     *
     * @param distance Offset delta
     * @param direction Direction of offset
     * @return Applicate z offset
     */
    default float zApplicateOffset(float distance, int direction){
        return (float) (direction * (distance * Math.cos(Math.toRadians(getYaw()))));
    }

}
