package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.util.CubeSide;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.List;

public class CubeBlock extends Cube implements Renderable {
    private final List<CubeSide> sides;


    protected CubeBlock(float x, float y, float z, List<CubeSide> sides) {
        super(x, y, z);
        this.sides = sides;
    }

    @Override
    public void render() {
        GL11.glBegin(GL11.GL_QUADS);
        for(CubeSide side : sides){
            Color color = side.getColor();
            GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            side.getVertices().forEach(v -> GL11.glVertex3f(v.getX(), v.getY(), v.getZ()));
        }
        GL11.glEnd();
    }
}
