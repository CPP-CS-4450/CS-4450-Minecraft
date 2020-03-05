package com.cpp.cs.cs4450.model.block;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.model.block.BlockTypes.BlockType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.ReadableVector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.List;

public class Block implements Renderable, Textured {
    private final float size;
    private final BlockType type;
    private final List<ReadableVector3f> vertices;
    private Texture texture;

    public Block(float size, BlockType type, List<ReadableVector3f> vertices) {
        this.size = size;
        this.type = type;
        this.vertices = vertices;
    }

    public Block(float size, BlockType type, List<ReadableVector3f> vertices, Texture texture) {
        this.size = size;
        this.type = type;
        this.vertices = vertices;
        this.texture = texture;
    }

    @Override
    public void render() {
        for(final ReadableVector3f vertex : vertices){
            GL11.glVertex3f(vertex.getX(), vertex.getY(), vertex.getZ());
        }
    }

    @Override
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void setTexture(Texture texture, int size) {
        this.texture = texture;
    }

    @Override
    public String getPath() {
        return null;
    }
}
