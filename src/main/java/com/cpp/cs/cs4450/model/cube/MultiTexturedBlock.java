/***************************************************************
 * file: MultiTexturedBlock.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Textured block with multiple textures for sides
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.util.BlockTextureLoader.BlockTexture;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.Bounded;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSide;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.util.vector.ReadableVector2f;
import org.lwjgl.util.vector.ReadableVector3f;
import org.newdawn.slick.opengl.Texture;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

/**
 * Implementation of a TexturedBlock that has multiple textures
 */
public class MultiTexturedBlock extends TexturedBlock implements Renderable, Textured, Bounded, Invertible {
    /**
     * Invalid vertices size error message
     */
    private static final String INVALID_VERTICES_ERROR_MESSAGE = "Invalid number of vertices";

    /**
     * Constructor
     *
     * @param x x-axis position
     * @param y y-axis position
     * @param z z-axis position
     * @param type block type
     * @param bounds block bounds
     * @param sides block sides
     * @param textures blocks textures
     * @param inverts block texture inverts
     */
    public MultiTexturedBlock(
            final float x,
            final float y,
            final float z,
            final BlockType type,
            final Bound bounds,
            final List<BlockSide> sides,
            final BlockTexture textures,
            final BlockTexture inverts
    ) {
        super(x, y, z, type, bounds, sides, textures, inverts);
    }

    /**
     * Renders the block and it's multiple textures
     */
    @Override
    public void render(){
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);

        GL11.glColor4d(1.0, 1.0, 1.0, 1.0);

        for(final BlockSide side : sides){
            if(side.getVertices().size() != TEX_COORDS.size()){
                throw new RuntimeException(INVALID_VERTICES_ERROR_MESSAGE);
            }

            final Texture texture = inverted ? inverts.getTexture(side) : textures.getTexture(side);
            texture.bind();
            GL11.glBegin(GL11.GL_QUADS);

            final float offset = texture.getWidth() / texture.getHeight();

            final Queue<ReadableVector3f> queue = new ArrayDeque<>(side.getVertices());

            while(!queue.isEmpty()) {
                for (final ReadableVector2f tex : TEX_COORDS) {
                    final ReadableVector3f vertex = queue.poll();
                    if (Objects.nonNull(vertex)) {
                        GL11.glTexCoord2f(tex.getX() * offset, tex.getY() * offset);
                        GL11.glVertex3f(vertex.getX(), vertex.getY(), vertex.getZ());
                    }
                }
            }
            GL11.glEnd();
        }

    }

}
