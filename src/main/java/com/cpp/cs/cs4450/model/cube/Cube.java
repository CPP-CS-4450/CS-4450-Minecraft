/***************************************************************
 * file: Cube.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Basic Cube being rendered
 *
 ****************************************************************/
package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.model.GameAreaEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.Color;

/**
 * Basic cube that renders
 */
public class Cube implements Renderable, GameAreaEntity {
    /**
     * Cubes default top side Color
     */
    private static final Color DEFAULT_TOP_SIDE_COLOR = Color.BLUE;
    /**
     * Cubes default front side Color
     */
    private static final Color DEFAULT_FRONT_SIDE_COLOR = Color.GREEN;
    /**
     * Cubes default right side Color
     */
    private static final Color DEFAULT_RIGHT_SIDE_COLOR = Color.CYAN;
    /**
     * Cubes default left side Color
     */
    private static final Color DEFAULT_LEFT_SIDE_COLOR = Color.MAGENTA;
    /**
     * Cubes default bottom side Color
     */
    private static final Color DEFAULT_BOTTOM_SIDE_COLOR = Color.YELLOW;
    /**
     * Cubes default back side Color
     */
    private static final Color DEFAULT_BACK_SIDE_COLOR = Color.RED;

    /**
     * Cubes top side Color
     */
    private final Color topSideColor;
    /**
     * Cubes front side Color
     */
    private final Color frontSideColor;
    /**
     * Cubes right side Color
     */
    private final Color rightSideColor;
    /**
     * Cubes left side Color
     */
    private final Color leftSideColor;
    /**
     * Cubes bottom side Color
     */
    private final Color bottomSideColor;
    /**
     * Cubes back side Color
     */
    private final Color backSideColor;
    /**
     * Cubes x-axis position
     */
    private final float x;
    /**
     * Cubes y-axis position
     */
    private final float y;
    /**
     * Cubes z-axis position
     */
    private final float z;

    /**
     * Constructor
     *
     * @param vector position
     */
    public Cube(final ReadableVector3f vector){
        this(vector.getX(), vector.getY(), vector.getZ());
    }

    /**
     * Constructor
     *
     * @param x x-axis position
     * @param y y-axis position
     * @param z z-axis position
     */
    public Cube(final float x, final float y, final float z){
        this(
            x,
            y,
            z,
            DEFAULT_TOP_SIDE_COLOR,
            DEFAULT_FRONT_SIDE_COLOR,
            DEFAULT_RIGHT_SIDE_COLOR,
            DEFAULT_LEFT_SIDE_COLOR,
            DEFAULT_BOTTOM_SIDE_COLOR,
            DEFAULT_BACK_SIDE_COLOR
        );
    }

    /**
     * Constructor
     *
     * @param vector position
     * @param topSideColor top side color
     * @param frontSideColor front side color
     * @param rightSideColor right side color
     * @param leftSideColor left side color
     * @param bottomSideColor bottom side color
     * @param backSideColor back side color
     */
    public Cube(
            final ReadableVector3f vector,
            final Color topSideColor,
            final Color frontSideColor,
            final Color rightSideColor,
            final Color leftSideColor,
            final Color bottomSideColor,
            final Color backSideColor
    ){
        this(
            vector.getX(),
            vector.getY(),
            vector.getZ(),
            topSideColor,
            frontSideColor,
            rightSideColor,
            leftSideColor,
            bottomSideColor,
            backSideColor
        );
    }

    /**
     *
     * @param x x-axis position
     * @param y y-axis position
     * @param z z-axis position
     * @param topSideColor top side color
     * @param frontSideColor front side color
     * @param rightSideColor right side color
     * @param leftSideColor left side color
     * @param bottomSideColor bottom side color
     * @param backSideColor back side color
     */
    public Cube(
            final float x,
            final float y,
            final float z,
            final Color topSideColor,
            final Color frontSideColor,
            final Color rightSideColor,
            final Color leftSideColor,
            final Color bottomSideColor,
            final Color backSideColor
    ){
        this.topSideColor = topSideColor;
        this.frontSideColor = frontSideColor;
        this.rightSideColor = rightSideColor;
        this.leftSideColor = leftSideColor;
        this.bottomSideColor = bottomSideColor;
        this.backSideColor = backSideColor;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Renders Cube
     */
    @Override
    public void render(){
        GL11.glBegin(GL11.GL_QUADS);
        renderTopSide();
        renderBottomSide();
        renderLeftSide();
        renderRightSide();
        renderFrontSide();
        renderBackSide();
        GL11.glEnd();
    }

    /**
     * Renders top side
     */
    private void renderTopSide(){
        GL11.glColor4f(topSideColor.getRed(), topSideColor.getGreen(), topSideColor.getBlue(), topSideColor.getAlpha());
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z - 1.0f);
    }

    /**
     * Renders bottom side
     */
    private void renderBottomSide(){
        GL11.glColor4f(bottomSideColor.getRed(), bottomSideColor.getGreen(), bottomSideColor.getBlue(), bottomSideColor.getAlpha());
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z - 1.0f);
    }

    /**
     * Renders left side
     */
    private void renderLeftSide(){
        GL11.glColor4f(leftSideColor.getRed(), leftSideColor.getGreen(), leftSideColor.getBlue(), leftSideColor.getAlpha());
        GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z - 1.0f);
    }

    /**
     * Renders right side
     */
    private void renderRightSide(){
        GL11.glColor4f(rightSideColor.getRed(), rightSideColor.getGreen(), rightSideColor.getBlue(), rightSideColor.getAlpha());
        GL11.glNormal3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z - 1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z - 1.0f);
    }

    /**
     * Renders front side
     */
    private void renderFrontSide(){
        GL11.glColor4f(frontSideColor.getRed(), frontSideColor.getGreen(), frontSideColor.getBlue(), frontSideColor.getAlpha());
        GL11.glNormal3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z + 1.0f);
    }

    /**
     * Renders back side
     */
    private void renderBackSide(){
        GL11.glColor4f(backSideColor.getRed(), backSideColor.getGreen(), backSideColor.getBlue(), backSideColor.getAlpha());
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z - 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z - 1.0f);
    }

    /**
     * Getter for the object's x position
     *
     * @return x position
     */
    @Override
    public float getPositionX() {
        return x;
    }

    /**
     * Getter for the object's y position
     *
     * @return x position
     */
    @Override
    public float getPositionY() {
        return y;
    }

    /**
     * Getter for the object's z position
     *
     * @return x position
     */
    @Override
    public float getPositionZ() {
        return z;
    }

    /**
     * Getter for the object's position
     *
     * @return position vector
     */
    @Override
    public ReadableVector3f getPosition3f() {
        return new Vector3f(x, y, z);
    }

}
