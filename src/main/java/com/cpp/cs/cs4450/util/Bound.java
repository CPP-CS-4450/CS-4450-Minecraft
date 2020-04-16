/***************************************************************
 * file: Bound.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Class to represent an objects bounds for collision detection
 *
 ****************************************************************/
package com.cpp.cs.cs4450.util;

/**
 * Class to represent object bounds
 */
public abstract class Bound {
    /**
     * Min x axis position
     */
    private final double minX;
    /**
     * Min y axis position
     */
    private final double minY;
    /**
     * Min z axis position
     */
    private final double minZ;
    /**
     * width
     */
    private final double width;
    /**
     * height
     */
    private final double height;
    /**
     * depth
     */
    private final double depth;
    private final double maxX;
    private final double maxY;
    private final double maxZ;

    public final double getMinX() {
        return this.minX;
    }

    public final double getMinY() {
        return this.minY;
    }

    public final double getMinZ() {
        return this.minZ;
    }

    public final double getWidth() {
        return this.width;
    }

    public final double getHeight() {
        return this.height;
    }

    public final double getDepth() {
        return this.depth;
    }

    public final double getMaxX() {
        return this.maxX;
    }

    public final double getMaxY() {
        return this.maxY;
    }

    public final double getMaxZ() {
        return this.maxZ;
    }

    public final double getCenterX() {
        return (getMaxX() + getMinX()) * 0.5;
    }

    public final double getCenterY() {
        return (getMaxY() + getMinY()) * 0.5;
    }

    public final double getCenterZ() {
        return (getMaxZ() + getMinZ()) * 0.5;
    }

    public abstract boolean isEmpty();

    public abstract boolean contains(final double x, final double y);

    public abstract boolean contains(final double x, final double y, final double z);

    public abstract boolean contains(final Bound b);

    public abstract boolean contains(final double x, final double y, final double w, final double h);

    public abstract boolean contains(final double x, final double y, final double z, final double w, final double h, final double d);

    public abstract boolean intersects(final Bound b);

    public abstract boolean intersects(final double x, final double y, final double w, final double h);

    public abstract boolean intersects(final double x, final double y, final double z, final double w, final double h, final double d);

    protected Bound(final double minX, final double minY, final double minZ, final double width, final double height, final double depth) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.maxX = minX + width;
        this.maxY = minY + height;
        this.maxZ = minZ + depth;
    }

}
