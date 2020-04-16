/***************************************************************
 * file: TexturedBlock.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Block that is textured
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
import com.cpp.cs.cs4450.util.BlockFactory.BlockSideType;
import org.lwjgl.util.vector.ReadableVector2f;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Block implementation that is Textured.
 */
public abstract class TexturedBlock extends Block implements Renderable, Textured, Bounded, Invertible {
    /**
     * List of Texture coordinates
     */
    protected static final List<ReadableVector2f> TEX_COORDS = Collections.unmodifiableList(
            Arrays.asList(
                    new Vector2f(1,1),
                    new Vector2f(0,1),
                    new Vector2f(0,0),
                    new Vector2f(1,0)
            )
    );

    /**
     * The block's type
     */
    protected final BlockType type;
    /**
     * The block's bounds
     */
    protected final Bound bounds;
    /**
     * The block's textures
     */
    protected final BlockTexture textures;
    /**
     * The block's inverted textures
     */
    protected final BlockTexture inverts;
    /**
     * Inverted flag
     */
    protected boolean inverted;

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
    public TexturedBlock(
            final float x,
            final float y,
            final float z,
            final BlockType type,
            final Bound bounds,
            final List<BlockSide> sides,
            final BlockTexture textures,
            final BlockTexture inverts
    ) {
        super(x, y, z, sides);
        this.type = type;
        this.bounds = bounds;
        this.textures = textures;
        this.inverts = inverts;
        this.inverted = false;
    }

    /**
     * Getter for block's textures
     *
     * @return map of block's textures
     */
    @Override
    public Map<BlockSideType, ? extends Texture> getTextures() {
        return textures.getTextures();
    }

    /**
     * Getter for block's bounds
     *
     * @return block's bounds
     */
    @Override
    public Bound getBounds() {
        return bounds;
    }

    /**
     * Getter for blocks width
     *
     * @return block's width
     */
    @Override
    public double getWidth() {
        return bounds.getWidth();
    }

    /**
     * Getter for blocks height
     *
     * @return block's height
     */
    @Override
    public double getHeight() {
        return bounds.getHeight();
    }

    /**
     * Getter for blocks depth
     *
     * @return block's depth
     */
    @Override
    public double getDepth() {
        return bounds.getDepth();
    }

    /**
     * Inverts block
     */
    @Override
    public void invert(){ inverted = !inverted; }

    /**
     * Checks if block is inverted
     *
     * @return True if inverted, false otherwise.
     */
    @Override
    public boolean isInverted(){ return inverted; }

    /**
     *  toString method
     *
     * @return string representation of block
     */
    @Override
    public String toString(){
        return  "\nTextured Cube" +
                "\nX:\t" + getPositionX() +
                "\nY:\t" + getPositionY() +
                "\nZ:\t" + getPositionZ();
    }

    /**
     * Getter for block's type
     *
     * @return block's type
     */
    @Override
    public BlockType getType() {
        return type;
    }

}
