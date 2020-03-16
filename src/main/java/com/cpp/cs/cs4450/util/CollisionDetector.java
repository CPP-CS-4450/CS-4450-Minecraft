package com.cpp.cs.cs4450.util;



import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;


import java.util.Comparator;
import java.util.List;

public class CollisionDetector {
    private static final double VERTICAL_COLLISION_DETECTING_LIMIT = 6.0 * .1;
    private static final double INBOUNDS = 10;
    private static final Bounds inbounds = new BoundingBox(-(INBOUNDS / 2),-(INBOUNDS / 2),-(INBOUNDS / 2), INBOUNDS, INBOUNDS, INBOUNDS);
    private static final Comparator<Bounds> comparator = new BoundsComparator(Point3D.ZERO);

    private final List<Bounds> bounds;
    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    private final double minZ;
    private final double maxZ;

    public CollisionDetector(final List<Bounds> bounds){
        this.bounds = List.copyOf(bounds);

        double minX = Double.MAX_VALUE, minY = Double.MIN_VALUE, minZ = Double.MAX_VALUE;
        double maxX = Double.MAX_VALUE, maxY = Double.MIN_VALUE, maxZ = Double.MAX_VALUE;
        for(Bounds bound : this.bounds){
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

    public boolean collision(final double x, final double y, final double z, final double w, final double h, final double d){
        return collision(new BoundingBox(x, y, z, w, h, d));
    }

    public boolean collision(final Bounds obj){
        if(!inBounds(obj)) return true;

        return checkForCollision(obj) && collisionLinear(obj);
    }

    public boolean collision(final double x, final double y, final double z){
        for(Bounds bound : bounds){
            if(bound.contains(new Point3D(x, y, z))){
                return true;
            }
        }

        return false;
    }

    private boolean collisionBinarySearchIterative(final Bounds obj){
        int low = 0, high = bounds.size() - 1;
        while (low <= high){
            int mid = (low + high) / 2;
            if(bounds.get(mid).intersects(obj)){
                return true;
            } else {
                if(comparator.compare(bounds.get(mid), obj) < 0){
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return false;
    }

    private boolean collisionLinear(final Bounds obj){
        for(Bounds bound : bounds){
            if(bound.intersects(obj)){
                return true;
            }
        }

        return false;
    }


    private boolean checkForCollision(final Bounds obj){
        return obj.getMinX() < minX
                || obj.getMaxX() > maxX
                || obj.getMinY() < minY
                || obj.getMaxY() > maxY
                || obj.getMinZ() < minZ
                || obj.getMaxZ() > maxZ;
    }


    private static class BoundsComparator implements Comparator<Bounds>{

        private final Point3D origin;

        private BoundsComparator(final Point3D origin){
            this.origin = origin;
        }

        @Override
        public int compare(final Bounds b0, final Bounds b1) {

            final double x0 = center(b0.getMinX(), b0.getMaxX()), y0 = center(b0.getMinY(), b0.getMaxY()), z0 = center(b0.getMinZ(), b0.getMaxZ());
            final Point3D p0 = new Point3D(x0, y0, z0);

            final double x1 = center(b1.getMinX(), b1.getMaxX()), y1 = center(b1.getMinY(), b1.getMaxY()), z1 = center(b1.getMinZ(), b1.getMaxZ());
            final Point3D p1 = new Point3D(x1, y1, z1);

            final double d = (origin.distance(p0) - origin.distance(p1));

            if(d < 0){
                return -1;

            } else if(d == 0){
                return 0;
            } else {
                return 1;
            }
        }
    }

    private boolean inBounds(final Bounds obj){
        return inbounds.intersects(obj);
    }

    private static double center(final double min, final double max){
        return (max + min) / 2;
    }

}
