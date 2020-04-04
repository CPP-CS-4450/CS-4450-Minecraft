package com.cpp.cs.cs4450.model.chunk;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.InvertibleContainer;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured3D;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.BoundedContainer;
import com.cpp.cs.cs4450.util.TextureInverter;
import com.cpp.cs.cs4450.util.TextureLoader;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.newdawn.slick.opengl.Texture;

import java.nio.FloatBuffer;
import java.util.Collections;
import java.util.List;

public class VBOArrayChunk extends Chunk implements Renderable, Textured3D, BoundedContainer, InvertibleContainer {
    private final int count;
    private final String texturePath;
    private final FloatBuffer vertexPositionData;
    private final FloatBuffer colorData;
    private final FloatBuffer textureData;

    private int vboVertexHandle;
    private int vboColorHandle;
    private int vboTextureHandle;
    private Texture texture;
    private Texture invert;
    private boolean inverted;

    public VBOArrayChunk(
            final Block[][][] cubes,
            final List<Bound> bounds,
            final FloatBuffer vertexPositionData,
            final FloatBuffer colorData,
            final FloatBuffer textureData,
            final String texturePath,
            final int count
    ) {
        super(cubes, bounds);
        this.vertexPositionData = vertexPositionData;
        this.colorData = colorData;
        this.textureData = textureData;
        this.texturePath = texturePath;
        this.count = count;
        this.inverted = false;
    }

    @Override
    public void init(){
        texture = TextureLoader.getTexture(texturePath);
        invert = TextureInverter.invert(texture);

        vboVertexHandle = GL15.glGenBuffers();
        vboColorHandle = GL15.glGenBuffers();
        vboTextureHandle = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboVertexHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexPositionData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboColorHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboTextureHandle);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, textureData, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
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
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, inverted ? invert.getTextureID() : texture.getTextureID());
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

}
