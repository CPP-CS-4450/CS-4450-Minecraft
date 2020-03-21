package com.cpp.cs.cs4450.util;


public class BoundBox extends Bound {

    public BoundBox(double minX, double minY, double minZ, double width, double height, double depth) {
        super(minX, minY, minZ, width, height, depth);
    }

    public BoundBox(double minX, double minY, double width, double height) {
        super(minX, minY, 0.0, width, height, 0.0);
    }

    public boolean isEmpty() {
        return this.getMaxX() < this.getMinX() || this.getMaxY() < this.getMinY() || this.getMaxZ() < this.getMinZ();
    }

    public boolean contains(double x, double y) {
        return this.contains(x, y, 0.0);
    }

    public boolean contains(double x, double y, double z) {
        if (this.isEmpty()) {
            return false;
        } else {
            return x >= this.getMinX() && x <= this.getMaxX() && y >= this.getMinY() && y <= this.getMaxY() && z >= this.getMinZ() && z <= this.getMaxZ();
        }
    }

    public boolean contains(Bound b) {
        return (b != null && !b.isEmpty()) && this.contains(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    public boolean contains(double x, double y, double w, double h) {
        return this.contains(x, y) && this.contains(x + w, y + h);
    }

    public boolean contains(double x, double y, double z, double w, double h, double d) {
        return this.contains(x, y, z) && this.contains(x + w, y + h, z + d);
    }

    public boolean intersects(Bound b) {
        return (b != null && !b.isEmpty()) && this.intersects(b.getMinX(), b.getMinY(), b.getMinZ(), b.getWidth(), b.getHeight(), b.getDepth());
    }

    public boolean intersects(double x, double y, double w, double h) {
        return this.intersects(x, y, 0.0, w, h, 0.0);
    }

    public boolean intersects(double x, double y, double z, double w, double h, double d) {
        if (!this.isEmpty() && w >= 0.0 && h >= 0.0 && d >= 0.0) {
            return x + w >= this.getMinX() && y + h >= this.getMinY() && z + d >= this.getMinZ() && x <= this.getMaxX() && y <= this.getMaxY() && z <= this.getMaxZ();
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        } else if (!(obj instanceof BoundBox)) {
            return false;
        } else {
            BoundBox other = (BoundBox)obj;
            return this.getMinX() == other.getMinX() && this.getMinY() == other.getMinY() && this.getMinZ() == other.getMinZ() && this.getWidth() == other.getWidth() && this.getHeight() == other.getHeight() && this.getDepth() == other.getDepth();
        }
    }

}
