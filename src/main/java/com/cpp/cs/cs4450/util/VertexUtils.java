package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import org.lwjgl.util.vector.ReadableVector3f;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;


public final class VertexUtils {

    private VertexUtils(){}

    public static Queue<ReadableVector3f> renderQueue(final CubeSideType type, final List<ReadableVector3f> vertices){
        final List<ReadableVector3f> queue = new ArrayList<>(vertices);

        if(type == CubeSideType.TOP || type == CubeSideType.BOTTOM || type == CubeSideType.LEFT || type == CubeSideType.RIGHT || type == CubeSideType.FRONT){
            Collections.rotate(queue, -1);
        }

        if(type == CubeSideType.TOP || type == CubeSideType.BOTTOM || type == CubeSideType.LEFT || type == CubeSideType.BACK){
            Collections.swap(queue, 1, 3);
        }

        return new ArrayDeque<>(queue);
    }

}
