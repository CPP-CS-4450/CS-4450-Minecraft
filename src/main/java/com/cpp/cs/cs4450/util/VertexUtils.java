package com.cpp.cs.cs4450.util;

import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

import java.util.ArrayList;
import java.util.List;

public final class VertexUtils {
    private static final int CUBE_VERTEX_COUNT = 8;

    private VertexUtils(){}



    public static List<ReadableVector3f> calculateCubeVertices(final ReadableVector3f start, final float length, final float height, final float depth){
        final float x = start.getX();
        final float y = start.getY();
        final float z = start.getZ();

        final List<ReadableVector3f> vertices = new ArrayList<>(CUBE_VERTEX_COUNT);
        for(int i = 0; i < 2; ++i){
            for(int j = 0; j < 2; ++j){
                for(int k = 0; k < 2; ++k){
                    final float a = (x * (k * length));
                    final float b = (y * (j * height));
                    final float c = (z * (i * depth));

                    vertices.add(new Vector3f(a, b, c));
                }
            }
        }

        return vertices;
    }


}
