package com.cpp.cs.cs4450.camera;

import com.cpp.cs.cs4450.model.Movable;
import org.lwjgl.util.vector.ReadableVector3f;

public interface CameraController extends Movable {

    void yaw(float change);

    void pitch(float change);

    void look();

    float getXPosition();

    float getYPosition();

    float getZPosition();

    ReadableVector3f getPositionVector();

}
