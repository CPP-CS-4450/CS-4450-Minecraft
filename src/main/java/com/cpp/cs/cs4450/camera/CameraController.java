/***************************************************************
 * file: CameraController.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Interface to define how a camera should act and be used in the program/
 *
 ****************************************************************/
package com.cpp.cs.cs4450.camera;

import com.cpp.cs.cs4450.model.GameAreaEntity;
import com.cpp.cs.cs4450.model.Movable;

public interface CameraController extends GameAreaEntity, Movable, Cloneable {
    float DEFAULT_DYAW = 90.0f;

    /*
    Changes the yaw of the camera
     */
    float yaw(float change);

    /*
    Changes the pitch of the camera
     */
    float pitch(float change);

    /*
    Sets up the camera to be looked through
     */
    void look();

    /*
    Getter for the pitch
     */
    float getPitch();

    /*
    Getter for the yaw
     */
    float getYaw();

    /*
    Method that clones the camera
     */
    Object clone();


}
