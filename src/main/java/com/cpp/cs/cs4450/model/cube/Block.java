/***************************************************************
 * file: Block.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Class that represents a block in the game
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.model.GameAreaEntity;
import com.cpp.cs.cs4450.util.Bounded;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSide;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Default abstract implementation of a Block.
 */
public abstract class Block extends Cube implements Renderable, GameAreaEntity, Bounded {
    /**
     * Sides of block
     */
    protected final List<BlockSide> sides;

    /**
     * Constructor
     *
     * @param x x-axis position
     * @param y y-axis position
     * @param z z-axis position
     * @param sides sides
     */
    protected Block(final float x, final float y, final float z, final List<BlockSide> sides) {
        super(x, y, z);
        this.sides = sides;
    }

    /**
     * Renders itself to the program
     */
    @Override
    public void render() {
        GL11.glBegin(GL11.GL_QUADS);
        for(final BlockSide side : sides){
            final Color color = side.getColor();
            GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            side.getVertices().forEach(v -> GL11.glVertex3f(v.getX(), v.getY(), v.getZ()));
        }
        GL11.glEnd();
    }

    /**
     * Getter for Block's sides
     * @return List of sides
     */
    public List<BlockSide> getSides(){
        return Collections.unmodifiableList(sides);
    }

    /**
     * Getter for blocks vertices
     * @return List of block's vertices
     */
    public List<Float> getVerticesArray(){
        final List<Float> floats = new ArrayList<>(sides.size() * 12);
        for(BlockSide side : sides){
            floats.addAll(side.getVerticesArray());
        }

        return floats;
    }

    /**
     * Getter for Blocks type
     *
     * @return Block's Type
     */
    public abstract BlockType getType();

}
