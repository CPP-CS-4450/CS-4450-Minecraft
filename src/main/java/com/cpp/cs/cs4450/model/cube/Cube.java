package com.cpp.cs.cs4450.model.cube;

import com.cpp.cs.cs4450.graphics.Renderable;
import com.cpp.cs.cs4450.model.GameAreaEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.Color;

public class Cube implements Renderable, GameAreaEntity {
    private static final Color DEFAULT_TOP_SIDE_COLOR = Color.BLUE;
    private static final Color DEFAULT_FRONT_SIDE_COLOR = Color.GREEN;
    private static final Color DEFAULT_RIGHT_SIDE_COLOR = Color.CYAN;
    private static final Color DEFAULT_LEFT_SIDE_COLOR = Color.MAGENTA;
    private static final Color DEFAULT_BOTTOM_SIDE_COLOR = Color.YELLOW;
    private static final Color DEFAULT_BACK_SIDE_COLOR = Color.RED;
    public static final float START_X = -0.5f;
    public static final float START_Y = -0.5f;
    public static final float START_Z = -0.5f;

    private final Color topSideColor;
    private final Color frontSideColor;
    private final Color rightSideColor;
    private final Color leftSideColor;
    private final Color bottomSideColor;
    private final Color backSideColor;
    private final float x;
    private final float y;
    private final float z;

    public Cube(final ReadableVector3f vector){
        this(vector.getX(), vector.getY(), vector.getZ());
    }

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

    private void renderTopSide(){
        GL11.glColor4f(topSideColor.getRed(), topSideColor.getGreen(), topSideColor.getBlue(), topSideColor.getAlpha());
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z - 1.0f);
    }

    private void renderBottomSide(){
        GL11.glColor4f(bottomSideColor.getRed(), bottomSideColor.getGreen(), bottomSideColor.getBlue(), bottomSideColor.getAlpha());
        GL11.glNormal3f(0.0f, -1.0f, 0.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z - 1.0f);
    }

    private void renderLeftSide(){
        GL11.glColor4f(leftSideColor.getRed(), leftSideColor.getGreen(), leftSideColor.getBlue(), leftSideColor.getAlpha());
        GL11.glNormal3f(-1.0f, 0.0f, 0.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z - 1.0f);
    }

    private void renderRightSide(){
        GL11.glColor4f(rightSideColor.getRed(), rightSideColor.getGreen(), rightSideColor.getBlue(), rightSideColor.getAlpha());
        GL11.glNormal3f(1.0f, 0.0f, 0.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z - 1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z - 1.0f);
    }

    private void renderFrontSide(){
        GL11.glColor4f(frontSideColor.getRed(), frontSideColor.getGreen(), frontSideColor.getBlue(), frontSideColor.getAlpha());
        GL11.glNormal3f(0.0f, 0.0f, 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z + 1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z + 1.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z + 1.0f);
    }

    private void renderBackSide(){
        GL11.glColor4f(backSideColor.getRed(), backSideColor.getGreen(), backSideColor.getBlue(), backSideColor.getAlpha());
        GL11.glNormal3f(0.0f, 0.0f, -1.0f);
        GL11.glVertex3f(x + 1.0f, y + 1.0f, z - 1.0f);
        GL11.glVertex3f(x + 1.0f, y - 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y - 1.0f, z - 1.0f);
        GL11.glVertex3f(x - 1.0f, y + 1.0f, z - 1.0f);
    }

    @Override
    public float getPositionX() {
        return x;
    }

    @Override
    public float getPositionY() {
        return y;
    }

    @Override
    public float getPositionZ() {
        return z;
    }

    @Override
    public ReadableVector3f getPosition3f() {
        return new Vector3f(x, y, z);
    }

    @Override
    public void init(){}

}
