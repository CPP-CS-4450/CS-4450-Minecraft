package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Invertible;
import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.graphics.Textured;
import com.cpp.cs.cs4450.util.BlockTextureLoader.BlockTexture;
import com.cpp.cs.cs4450.util.Bound;
import com.cpp.cs.cs4450.util.Bounded;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSide;
import com.cpp.cs.cs4450.util.BlockFactory.BlockSideType;
import com.cpp.cs.cs4450.util.TextureInverter;
import org.lwjgl.util.vector.ReadableVector2f;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;


import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;


public abstract class TexturedBlock extends Block implements Renderable, Textured, Bounded, Invertible {
    protected static final List<ReadableVector2f> TEX_COORDS = Collections.unmodifiableList(
            Arrays.asList(
                    new Vector2f(1,1),
                    new Vector2f(0,1),
                    new Vector2f(0,0),
                    new Vector2f(1,0)
            )
    );

    protected final BlockType type;
    protected final Bound bounds;
    protected final BlockTexture textures;
    protected final BlockTexture inverts;
    protected boolean inverted;


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

    @Override
    public Map<BlockSideType, ? extends Texture> getTextures() {
        return textures.getTextures();
    }

    @Override
    public Bound getBounds() {
        return bounds;
    }

    @Override
    public double getWidth() {
        return bounds.getWidth();
    }

    @Override
    public double getHeight() {
        return bounds.getHeight();
    }

    @Override
    public double getDepth() {
        return bounds.getDepth();
    }

    @Override
    public void invert(){ inverted = !inverted; }

    @Override
    public boolean isInverted(){ return inverted; }

    @Override
    public String toString(){
        return  "\nTextured Cube" +
                "\nX:\t" + getPositionX() +
                "\nY:\t" + getPositionY() +
                "\nZ:\t" + getPositionZ();
    }

    @Override
    public BlockType getType() {
        return type;
    }

}
