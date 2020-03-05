package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.model.block.BlockTypes.BlockType;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class VertexUtils {
    private static final int CUBE_VERTEX_COUNT = 8;
    private static final int CUBE_TEXTURE_MATRIX_ROWS = 6;
    private static final int CUBE_TEXTURE_MATRIX_COL = 4;
    private static final int[] textureGrid = new int[]{0, 1, 0, 2, 0, 0};
    private static final Comparator<ReadableVector3f> VECTOR_COMPARATOR = new VertexComparator();


    private VertexUtils(){}


    public static List<ReadableVector3f> calculateCubeVertices(final ReadableVector3f start, final float length, final float height, final float depth){
        final float x = start.getX();
        final float y = start.getY();
        final float z = start.getZ();

        final List<ReadableVector3f> vertices = new ArrayList<>(CUBE_VERTEX_COUNT);
        for(int n = 0; n < 3; ++n) {
            for (int i = 0; i < 2; ++i) {
                for (int j = 0; j < 2; ++j) {
                    for (int k = 0; k < 2; ++k) {
                        final float a = (x + (k * length));
                        final float b = (y + (j * height));
                        final float c = (z + (i * depth));

                        vertices.add(new Vector3f(c, b, a));
                    }
                }
            }
        }

        return vertices;
    }

    public static List<ReadableVector3f> calculateVertices(float sx, float sy, float sz, float l, float h, float d){

       return null;
    }

    public static void calculateTextureVertices(){

    }

    public static List<ReadableVector3f> getSorted(Collection<ReadableVector3f> vertices){
        List<ReadableVector3f> list = new ArrayList<>(vertices);
        sort(list);
        return list;
    }

    public static void sort(List<ReadableVector3f> vertices){
        vertices.sort(VECTOR_COMPARATOR);
    }



    private static class VertexComparator implements Comparator<ReadableVector3f> {

        @Override
        public int compare(ReadableVector3f v1, ReadableVector3f v2) {
            if(v1.getX() < v2.getX()){
                return -1;
            } else if(v1.getX() == v2.getX()){
                if(v1.getY() < v2.getY()){
                    return -1;
                } else if(v1.getY() == v2.getY()){
                    return Float.compare(v1.getZ(), v2.getZ());
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        }
    }




/*
    public static Vector2f[][] calculateCubeTextureVertices(Texture texture, BlockType type, int size){
        int index = type.getIndex();

        int bottom = size * index;
        int top = bottom + size;

        float width = texture.getWidth();
        float height = texture.getHeight();

        Vector2f[][] vertices = new Vector2f[CUBE_TEXTURE_MATRIX_ROWS][CUBE_TEXTURE_MATRIX_COL];

        for(int i = 0; i < 4; ++i){
            int left = textureGrid[i] * size;
            int right = left + size;

            vertices[i][0] = new Vector2f(left / width, bottom / height);
            vertices[i][1] = new Vector2f(right / width, bottom / height);
            vertices[i][2] = new Vector2f(left / width, top / height);
            vertices[i][3] = new Vector2f(right / width, top / height);
        }

        for(int i = 4; i < 6; ++i){
            int left = textureGrid[i] * size;
            int right = left + size;

            vertices[i][0] = new Vector2f(left / width, top / height);
            vertices[i][1] = new Vector2f(left / width, bottom / height);
            vertices[i][2] = new Vector2f(right / width, bottom / height);
            vertices[i][3] = new Vector2f(right / width, top / height);
        }



        return vertices;
    }

 */


}
