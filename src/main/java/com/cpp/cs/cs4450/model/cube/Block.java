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

public abstract class Block extends Cube implements Renderable, GameAreaEntity, Bounded {
    protected final List<BlockSide> sides;


    protected Block(final float x, final float y, final float z, final List<BlockSide> sides) {
        super(x, y, z);
        this.sides = sides;
    }

    @Override
    public void render() {
        GL11.glBegin(GL11.GL_QUADS);
        for(BlockSide side : sides){
            final Color color = side.getColor();
            GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            side.getVertices().forEach(v -> GL11.glVertex3f(v.getX(), v.getY(), v.getZ()));
        }
        GL11.glEnd();
    }

    public List<BlockSide> getSides(){
        return Collections.unmodifiableList(sides);
    }

    public List<Float> getVerticesArray(){
        List<Float> floats = new ArrayList<>(sides.size() * 12);
        for(BlockSide side : sides){
            floats.addAll(side.getVerticesArray());
        }

        return floats;
    }

    public abstract BlockType getType();

}
