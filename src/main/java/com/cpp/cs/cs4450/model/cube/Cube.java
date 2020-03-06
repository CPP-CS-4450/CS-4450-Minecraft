/***************************************************************
 * file: Cube.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 1
 * date last modified: 3/05/2020
 *
 * purpose: Model class for cube
 *
 ****************************************************************/package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.util.CubeFactory.CubeSide;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.util.List;

public abstract class Cube implements Renderable {
    protected final List<CubeSide> sides;

    public Cube(final List<CubeSide> sides) {
        this.sides = sides;
    }

    @Override
    public void render() {
        GL11.glBegin(GL11.GL_QUADS);
        for(CubeSide side : sides){
            final Color color = side.getColor();
            GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            side.getVertices().forEach(v -> GL11.glVertex3f(v.getX(), v.getY(), v.getZ()));
        }
        GL11.glEnd();
    }
}
