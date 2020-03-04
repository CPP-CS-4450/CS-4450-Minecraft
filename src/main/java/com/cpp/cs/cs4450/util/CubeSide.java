package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import org.lwjgl.util.vector.ReadableVector3f;

import java.awt.Color;
import java.util.List;

public class CubeSide {
    private final CubeSideType type;
    private final List<ReadableVector3f> vertices;
    private final Color color;


    CubeSide(CubeSideType type, List<ReadableVector3f> vertices, Color color) {
        this.type = type;
        this.vertices = vertices;
        this.color = color;
    }

    public List<ReadableVector3f> getVertices() {
        return vertices;
    }

    public Color getColor() {
        return color;
    }

    public CubeSideType getType() {
        return type;
    }
}
