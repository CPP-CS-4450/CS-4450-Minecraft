package com.cpp.cs.cs4450.model;

import org.lwjgl.util.vector.ReadableVector3f;

public interface GameAreaEntity {

    float getPositionX();

    float getPositionY();

    float getPositionZ();

    ReadableVector3f getPosition3f();

}
