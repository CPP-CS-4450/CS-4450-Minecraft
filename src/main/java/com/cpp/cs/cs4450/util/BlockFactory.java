package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.model.cube.BlockType;
import com.cpp.cs.cs4450.model.cube.MultiTexturedBlock;
import com.cpp.cs.cs4450.model.cube.SingleTexturedBlock;
import com.cpp.cs.cs4450.util.BlockTextureLoader.BlockTexture;
import org.lwjgl.util.vector.ReadableVector3f;
import org.lwjgl.util.vector.Vector3f;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public final class BlockFactory {
    private static final int CUBE_SIDES = BlockSideType.values().length;
    private static final List<Color> COLORS = Collections.unmodifiableList(
            Arrays.asList(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE)
    );

    public enum BlockSideType {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        FRONT,
        BACK
    }

    private BlockFactory(){}

    public static Block createTexturedCube(final BlockType type, final float x, final float y, final float z, final float size, final BlockTexture texture, final BlockTexture invert){
        return createTexturedBlock(type, x, y, z, size, size, size, texture, invert);
    }

    public static Block createTexturedBlock(final BlockType type, final float x, final float y, final float z, final float l, final float h, final float d, final BlockTexture texture, final BlockTexture invert){
        final List<BlockSide> sides = new ArrayList<>(CUBE_SIDES);
        for(final BlockSideType side : BlockSideType.values()){
            sides.add(new BlockSide(side, calculateSideVertices(side, x, y, z, l, h, d), Color.BLUE));
        }

        final float minX = -(x - (l / 2)), minY = -(y - (h / 2)), minZ = -(z - (d / 2));
        final BoundBox bounds = new BoundBox(minX, minY, minZ, l, (!type.isSolid() ? h : 0), d);

        return type.isMultiTextured()
                ? new MultiTexturedBlock(x,y,z,type,bounds,sides, texture, invert)
                : new SingleTexturedBlock(x,y,z,type,bounds,sides, texture, invert);
    }

    public static Block createTexturedCube(final BlockType type, final float x, final float y, final float z, final float size, final BlockTexture texture){
        return createTexturedBlock(type, x, y, z, size, size, size, texture);
    }

    public static Block createTexturedBlock(final BlockType type, final float x, final float y, final float z, final float l, final float h, final float d, final BlockTexture texture){
        return createTexturedBlock(type, x, y, z, l, h, d, texture, BlockTextureLoader.invertBlockTexture(texture));
    }

    public static Cube createCube(float size, Color ...colors){
        return createCube(0,0,0, size, colors);
    }

    public static Cube createCube(final ReadableVector3f center, final float size, final Color ...colors){
        return createCube(center.getX(), center.getY(), center.getZ(), size, colors);
    }

    public static Cube createCube(final float x, final float y, final float z, final float size, final Color ...colors){
        return createBlock(x, y, z, size, size, size, colors);
    }

    public static Block createBlock(final float x, final float y, final float z, final float l, final float h, final float d, final Color ...colors){
        final Queue<Color> colorQueue = generateColors(colors);

        final List<BlockSide> sides = new ArrayList<>(CUBE_SIDES);
        for(BlockSideType type : BlockSideType.values()){
            sides.add(new BlockSide(type, calculateSideVertices(type, x, y, z, l, h, d), colorQueue.poll()));
        }

        return new BlockImpl(x, y, z, l, h, d, sides);
    }

    private static List<ReadableVector3f> calculateSideVertices(final BlockSideType type, final float x, final float y, final float z, final float l, final float h, final float d){
        final float a = l / 2;
        final float b = h / 2;
        final float c = d / 2;

        final List<Vector3f> vertices;
        switch (type){
            case TOP: vertices = calculateTopSideVertices(x, y, z, a, b, c);
                break;
            case BOTTOM: vertices = calculateBottomSideVertices(x, y, z, a, b, c);
                break;
            case LEFT: vertices = calculateLeftSideVertices(x, y, z, a, b, c);
                break;
            case RIGHT: vertices = calculateRightSideVertices(x, y, z, a, b, c);
                break;
            case FRONT: vertices = calculateFrontSideVertices(x, y, z, a, b, c);
                break;
            case BACK: vertices = calculateBackSideVertices(x, y, z, a, b, c);
                break;
            default: vertices = Collections.emptyList();
                break;
        }

        return Collections.unmodifiableList(vertices);
    }

    private static List<Vector3f> calculateTopSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y + h, z - d),
                new Vector3f(x + l, y + h, z - d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    private static List<Vector3f> calculateBottomSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x - l, y - h, z - d),
                new Vector3f(x + l, y - h, z - d)

        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
    private static List<Vector3f> calculateLeftSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x - l, y - h, z - d),
                new Vector3f(x - l, y + h, z - d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
    private static List<Vector3f> calculateRightSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x + l, y - h, z - d),
                new Vector3f(x + l, y + h, z - d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
    private static List<Vector3f> calculateFrontSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x + l, y - h, z + d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }
    private static List<Vector3f> calculateBackSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y + h, z - d),
                new Vector3f(x - l, y + h, z - d),
                new Vector3f(x - l, y - h, z - d),
                new Vector3f(x + l, y - h, z - d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    private static Queue<Color> generateColors(final Color ...colors){
        if(colors == null || colors.length < 1){
            return generateUniqueColors();
        }

        if(colors.length == 1){
            return generateSingleColor(colors[0]);
        }

        final Queue<Color> queue = new ArrayDeque<>(Arrays.asList(colors));
        while(queue.size() <= CUBE_SIDES){
            for(final Color color : COLORS){
                if(queue.size() > CUBE_SIDES){
                    break;
                }
                queue.add(color);
            }
        }

        return queue;
    }

    private static Queue<Color> generateUniqueColors(){
        if(COLORS.size() < CUBE_SIDES){
            return generateColors(COLORS.toArray(new Color[0]));
        }

        final List<Color> colors = new ArrayList<>(COLORS);

        final Set<Color> set = new LinkedHashSet<>(CUBE_SIDES);
        while(set.size() <= CUBE_SIDES){
            Collections.shuffle(colors);
            for(final Color color : colors){
                if(set.size() > CUBE_SIDES){
                    break;
                }
                set.add(color);
            }
        }

        return new ArrayDeque<>(set);
    }

    private static Queue<Color> generateSingleColor(final Color color){
        return IntStream.range(0, CUBE_SIDES).mapToObj(i -> color).collect(Collectors.toCollection(ArrayDeque::new));
    }

    private static class BlockImpl extends Block {
        private final float w;
        private final float h;
        private final float d;
        private final Bound b;

        private BlockImpl(final float x, final float y, final float z, final float w, final float h, final float d, final List<BlockSide> sides) {
            super(x, y, z, sides);
            this.w = w;
            this.h = h;
            this.d = d;
            this.b = new BoundBox(x,y,z,w,h,d);
        }

        @Override
        public Bound getBounds() {
            return b;
        }

        @Override
        public double getWidth() {
            return w;
        }

        @Override
        public double getHeight() {
            return h;
        }

        @Override
        public double getDepth() {
            return d;
        }

        @Override
        public BlockType getType() {
            return null;
        }
    }

    public static final class BlockSide {
        private final BlockSideType type;
        private final List<ReadableVector3f> vertices;
        private final Color color;


        private BlockSide(final BlockSideType type, final List<ReadableVector3f> vertices, final Color color) {
            this.type = type;
            this.vertices = vertices;
            this.color = color;
        }

        public List<ReadableVector3f> getVertices() {
            return Collections.unmodifiableList(vertices);
        }

        public Color getColor() {
            return color;
        }

        public BlockSideType getType() {
            return type;
        }

        public List<Float> getVerticesArray(){
            final List<Float> floats = new ArrayList<>(vertices.size() * 3);
            for(final ReadableVector3f vector : vertices){
                floats.add(vector.getX());
                floats.add(vector.getY());
                floats.add(vector.getZ());
            }

            return floats;
         }

    }

    public static final class BlockOptions {
        private final BlockType type;
        private final ReadableVector3f position;
        private final float l;
        private final float h;
        private final float d;


        private BlockOptions(final BlockType type, final ReadableVector3f position, final float l, final float h, final float d) {
            this.type = type;
            this.position = position;
            this.l = l;
            this.h = h;
            this.d = d;
        }

        public BlockType getType() {
            return type;
        }

        public float getXPosition() {
            return position.getX();
        }

        public float getYPosition() {
            return position.getY();
        }

        public float getZPosition() {
            return position.getZ();
        }

        public ReadableVector3f getPosition(){
            return position;
        }

        public float getLength() {
            return l;
        }

        public float getHeight() {
            return h;
        }

        public float getDepth() {
            return d;
        }

        public static Builder builder(){
            return new Builder();
        }

        public static final class Builder {
            private BlockType type;
            private Vector3f position;
            private float l;
            private float h;
            private float d;

            private Builder(){
                position = new Vector3f();
            }


            public BlockType getType() {
                return type;
            }

            public void setType(BlockType type) {
                this.type = type;
            }

            public Builder withType(BlockType type) {
                setType(type);
                return this;
            }

            public float getXPosition() {
                return position.getX();
            }

            public void setXPosition(float x){
                this.position.setX(x);
            }

            public Builder withXPosition(float x){
                setXPosition(x);
                return this;
            }

            public float getYPosition() {
                return position.getY();
            }

            public void setYPosition(float y){
                this.position.setY(y);
            }

            public Builder withYPosition(float y){
                setYPosition(y);
                return this;
            }

            public float getZPosition() {
                return position.getZ();
            }

            public void setZPosition(float z){
                this.position.setZ(z);
            }

            public Builder withZPosition(float z){
                setZPosition(z);
                return this;
            }

            public ReadableVector3f getPosition() {
                return position;
            }

            public void setPosition(ReadableVector3f position) {
                this.position.set(position);
            }

            public Builder withPosition(ReadableVector3f position) {
                setPosition(position);
                return this;
            }

            public void setPosition(float x, float y){
                this.position.set(x, y);
            }

            public Builder withPosition(float x, float y){
                setPosition(x, y);
                return this;
            }

            public void setPosition(float x, float y, float z){
                this.position.set(x, y, z);
            }

            public Builder withPosition(float x, float y, float z){
                setPosition(x, y, z);
                return this;
            }

            public float getLength() {
                return l;
            }

            public void setLength(float l) {
                this.l = l;
            }

            public Builder withLength(float l) {
                setLength(l);
                return this;
            }

            public float getHeight() {
                return h;
            }

            public void setHeight(float h) {
                this.h = h;
            }

            public Builder withHeight(float h) {
                setHeight(h);
                return this;
            }

            public float getDepth() {
                return d;
            }

            public void setDepth(float d) {
                this.d = d;
            }

            public Builder withDepth(float d) {
                setDepth(d);
                return this;
            }

            public void setSize(float size){
                setSize(size, size, size);
            }

            public Builder withSize(float size){
                setSize(size);
                return this;
            }

            public void setSize(float l, float h, float d){
                setLength(l);
                setHeight(h);
                setDepth(d);
            }

            public Builder withSize(float l, float h, float d){
                setSize(l, h, d);
                return this;
            }

            public BlockOptions build(){
                return new BlockOptions(type, position, l, h, d);
            }

        }

    }

}
