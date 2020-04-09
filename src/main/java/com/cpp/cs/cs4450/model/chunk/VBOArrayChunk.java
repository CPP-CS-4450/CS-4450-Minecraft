package com.cpp.cs.cs4450.model.chunk;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.InvertibleContainer;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.graphics.TexturedContainer;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.BoundedContainer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.opengl.Texture;

import java.util.Collections;
import java.util.List;

public class VBOArrayChunk extends Chunk implements Renderable, BoundedContainer, InvertibleContainer, TexturedContainer {
    private final int count;
    private final int vboVertexHandle;
    private final int vboColorHandle;
    private final int vboTextureHandle;
    private final Texture texture;
    private final Texture invert;
    private boolean inverted;

    public VBOArrayChunk(
            final Block[][][] cubes,
            final List<Bound> bounds,
            final int count,
            final int vboVertexHandle,
            final int vboColorHandle,
            final int vboTextureHandle,
            final Texture texture,
            final Texture invert
    ) {
        super(cubes, bounds);
        this.count = count;
        this.vboVertexHandle = vboVertexHandle;
        this.vboColorHandle = vboColorHandle;
        this.vboTextureHandle = vboTextureHandle;
        this.texture = texture;
        this.invert = invert;
        this.inverted = false;
    }

    @Override
    public void render(){
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
        GL11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0L);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboColorHandle);
        GL11.glColorPointer(3, GL11.GL_FLOAT, 0, 0L);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTextureHandle);
        (inverted ? invert : texture).bind();
        GL11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0L);

        GL11.glColor4d(1, 1, 1, 1);

        GL11.glDrawArrays(GL11.GL_QUADS, 0, count * 24);

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    @Override
    public void invert(){ inverted = !inverted; }

    @Override
    public List<Invertible> getInvertibles(){
        return Collections.emptyList();
    }

    @Override
    public List<Textured> getTextured(){ return Collections.emptyList(); }

}
