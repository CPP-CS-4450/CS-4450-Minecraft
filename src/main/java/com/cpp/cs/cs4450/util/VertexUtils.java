/***************************************************************
 * file: VertexUtils.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Util class that has helper functions for vertices
 *
 ****************************************************************/
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

    /*
    Orders the verticies list to be rendered
     */
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
