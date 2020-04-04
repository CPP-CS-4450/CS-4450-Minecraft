package com.cpp.cs.cs4450.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CollisionDetector {
    private static final double VERTICAL_COLLISION_DETECTING_LIMIT = 6.0 * .1;
    private static final double INBOUNDS = 10;

    private static final Bound inbounds = new BoundBox(-(INBOUNDS / 2),-(INBOUNDS / 2),-(INBOUNDS / 2), INBOUNDS, INBOUNDS, INBOUNDS);

    private final List<Bound> bounds;
    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    private final double minZ;
    private final double maxZ;

    public CollisionDetector(final List<Bound> bounds){
        this.bounds = bounds.stream().collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));

        double minX = Double.MAX_VALUE, minY = Double.MIN_VALUE, minZ = Double.MAX_VALUE;
        double maxX = Double.MAX_VALUE, maxY = Double.MIN_VALUE, maxZ = Double.MAX_VALUE;
        for(final Bound bound : this.bounds){
            minX = Math.min(minX, bound.getMinX());
            minY = Math.min(minY, bound.getMinY());
            minZ = Math.min(minZ, bound.getMinZ());
            maxX = Math.min(maxX, bound.getMaxX());
            maxZ = Math.min(maxZ, bound.getMaxZ());
        }
        maxY = Math.max(maxY, VERTICAL_COLLISION_DETECTING_LIMIT);

        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.minZ = minZ;
        this.maxZ = maxZ;

    }

    public boolean noCollision(final double x, final double y, final double z, final double w, final double h, final double d){
        return !collision(new BoundBox(x, y, z, w, h, d));
    }

    public boolean noCollision(final Bound obj){
        return !collision(obj);
    }

    public boolean collision(final double x, final double y, final double z, final double w, final double h, final double d){
        return collision(new BoundBox(x, y, z, w, h, d));
    }

    public boolean collision(final Bound obj){
        if(!inBounds(obj)) return true;

        return checkForCollision(obj) && collisionLinear(obj);
    }

    private boolean collisionLinear(final Bound obj){
        for(Bound bound : bounds){
            if(bound.intersects(obj)){
                return true;
            }
        }

        return false;
    }

    private boolean checkForCollision(final Bound obj){
        return obj.getMinX() < minX
                || obj.getMaxX() > maxX
                || obj.getMinY() < minY
                || obj.getMaxY() > maxY
                || obj.getMinZ() < minZ
                || obj.getMaxZ() > maxZ;
    }

    private boolean inBounds(final Bound obj){
        return inbounds.intersects(obj);
    }

}
