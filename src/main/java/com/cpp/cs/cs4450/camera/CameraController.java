package com.cpp.cs.cs4450.camera;

import com.cpp.cs.cs4450.model.GameAreaEntity;
import com.cpp.cs.cs4450.model.Movable;

public interface CameraController extends GameAreaEntity, Movable, Cloneable {
    float DEFAULT_DYAW = 90.0f;


    float yaw(float change);

    float pitch(float change);

    void look();

    float getPitch();

    float getYaw();

    Object clone();

    default float xHorizontalOffset(float distance, int direction){
        return (float) (distance * Math.sin(Math.toRadians(getYaw() + (direction * DEFAULT_DYAW))));
    }

    default float zHorizontalOffset(float distance, int direction){
        return (float) (distance * Math.cos(Math.toRadians(getYaw() + (direction * DEFAULT_DYAW))));
    }

    default float xApplicateOffset(float distance, int direction){
        return (float) (direction * (distance * Math.sin(Math.toRadians(getYaw()))));
    }

    default float zApplicateOffset(float distance, int direction){
        return (float) (direction * (distance * Math.cos(Math.toRadians(getYaw()))));
    }

}
