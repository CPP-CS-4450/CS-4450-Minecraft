/***************************************************************
 * file: CameraController.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 1
 * date last modified: 3/05/2020
 *
 * purpose: Interface for a movable camera
 *
 ****************************************************************/
package com.cpp.cs.cs4450.camera;

import com.cpp.cs.cs4450.model.Movable;
import org.lwjgl.util.vector.ReadableVector3f;

public interface CameraController extends Movable {

    //Changes the yaw
    void yaw(float change);

    //Changes the pitch
    void pitch(float change);

    //Sets up camera to look
    void look();

    //Returns camera x position
    float getXPosition();

    //Returns camera y position
    float getYPosition();


    //Returns camera z position
    float getZPosition();


    //Returns camera position
    ReadableVector3f getPositionVector();

}
