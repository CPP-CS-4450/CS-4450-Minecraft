package com.cpp.cs.cs4450.model.block;

import com.cpp.cs.cs4450.graphics.Renderable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.ReadableVector3f;

import java.util.List;

public class Block implements Renderable {
    private final BlockType type;
    private List<ReadableVector3f> vertices;

    public Block(BlockType type, List<ReadableVector3f> vertices) {
        this.type = type;
        this.vertices = vertices;
    }


    @Override
    public void render() {
        GL11.glBegin(GL11.GL_QUADS);
        for(final ReadableVector3f vertex : vertices){
            GL11.glVertex3f(vertex.getX(), vertex.getY(), vertex.getZ());
        }
        GL11.glEnd();
    }
}
