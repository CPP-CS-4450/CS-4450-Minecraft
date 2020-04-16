/***************************************************************
 * file: VBOArrayChunk.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Renders blocks using VBO Arrays
 *
 ****************************************************************/
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

/**
 * Implementation of Chunk that renders it's blocks using
 * a VBO array
 */
public class VBOArrayChunk extends Chunk implements Renderable, BoundedContainer, InvertibleContainer, TexturedContainer {
    /**
     * Block count
     */
    private final int count;
    /**
     * Block Vertices handle
     */
    private final int vboVertexHandle;
    /**
     * Chuck's color vertices handler
     */
    private final int vboColorHandle;
    /**
     * Chunks texture vertices handler
     */
    private final int vboTextureHandle;
    /**
     * Chunks texture
     */
    private final Texture texture;
    /**
     * Chunks inverted texture
     */
    private final Texture invert;
    /**
     * Inverted flag
     */
    private boolean inverted;

    /**
     * Constructor
     *
     * @param cubes Cubes in Chunk
     * @param bounds Bounds of Cubes
     * @param count Block count
     * @param vboVertexHandle Block Vertices handle
     * @param vboColorHandle Chunk Color handle
     * @param vboTextureHandle Chunk Texture handle
     * @param texture Chunk's texture
     * @param invert Chunk's inverted texture
     */
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

    /**
     * Renders the Chunk's blocks using VBO Arrays
     */
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

    /**
     * Inverts chunk
     */
    @Override
    public void invert(){ inverted = !inverted; }

    /**
     * Getter for Invertibles
     *
     * @return List of Invertible's the chunk uses
     */
    @Override
    public List<Invertible> getInvertibles(){
        return Collections.emptyList();
    }

    /**
     * Getter for Textured
     *
     * @return List of Textured the chunk uses
     */
    @Override
    public List<Textured> getTextured(){ return Collections.emptyList(); }

}
