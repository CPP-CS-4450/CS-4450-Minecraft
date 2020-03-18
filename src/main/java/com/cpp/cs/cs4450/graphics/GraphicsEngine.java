package com.cpp.cs.cs4450.graphics;

public interface GraphicsEngine {

    void render();

    void invert();

    void shutdown();

    void updateLighting(float x, float y, float z);

    void setPerspective(float x, float y, float z, float pitch, float yaw);

    void update();

}
