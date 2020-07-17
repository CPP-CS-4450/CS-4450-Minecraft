/***************************************************************
 * file: BlockFactory.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Factory to create Blocks
 *
 ****************************************************************/
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
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utility Factory Class to create blocks
 */
public final class BlockFactory {
    /**
     * Number of cube sides
     */
    private static final int CUBE_SIDES = BlockSideType.values().length;
    /**
     * Supported colors
     */
    private static final List<Color> COLORS = Collections.unmodifiableList(
            Arrays.asList(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE)
    );

    /**
     * Block side types
     */
    public enum BlockSideType {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        FRONT,
        BACK
    }

    /**
     * Private constructor
     */
    private BlockFactory(){
        throw new UnsupportedOperationException();
    }

    /**
     * Creates textured block
     *
     * @param type blocks
     * @param x blocks x-axis position
     * @param y blocks y-axis position
     * @param z blocks z-axis position
     * @param size blocks size
     * @param texture blocks texture
     * @param invert blocks
     * @return new textured block
     */
    public static Block createTexturedCube(
            final BlockType type,
            final float x,
            final float y,
            final float z,
            final float size,
            final BlockTexture texture,
            final BlockTexture invert
    ){
        return createTexturedBlock(type, x, y, z, size, size, size, texture, invert);
    }

    /**
     * Creates Textured Block
     *
     * @param type blocks
     * @param x blocks x-axis position
     * @param y blocks y-axis position
     * @param z blocks z-axis position
     * @param l blocks length
     * @param h blocks height
     * @param d blocks depth
     * @param texture blocks texture
     * @param invert blocks
     * @return new textured block
     */
    public static Block createTexturedBlock(
            final BlockType type,
            final float x,
            final float y,
            final float z,
            final float l,
            final float h,
            final float d,
            final BlockTexture texture,
            final BlockTexture invert
    ){
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

    /**
     * Creates textured block
     *
     * @param type blocks
     * @param x blocks x-axis position
     * @param y blocks y-axis position
     * @param z blocks z-axis position
     * @param size blocks size
     * @param texture blocks texture
     * @return new textured block
     */
    public static Block createTexturedCube(
            final BlockType type,
            final float x,
            final float y,
            final float z,
            final float size,
            final BlockTexture texture
    ){
        return createTexturedBlock(type, x, y, z, size, size, size, texture);
    }

    /**
     * Creates Textured Block
     *
     * @param type blocks
     * @param x blocks x-axis position
     * @param y blocks y-axis position
     * @param z blocks z-axis position
     * @param l blocks length
     * @param h blocks height
     * @param d blocks depth
     * @param texture blocks texture
     * @return new textured block
     */
    public static Block createTexturedBlock(
            final BlockType type,
            final float x,
            final float y,
            final float z,
            final float l,
            final float h,
            final float d,
            final BlockTexture texture
    ){
        return createTexturedBlock(type, x, y, z, l, h, d, texture, BlockTextureLoader.invertBlockTexture(texture));
    }

    /**
     * Creates block
     *
     * @param size block size
     * @param colors block colors
     * @return new block
     */
    public static Cube createCube(float size, Color ...colors){
        return createCube(0,0,0, size, colors);
    }

    /**
     * Creates block
     *
     * @param center position
     * @param size block size
     * @param colors block colors
     * @return new block
     */
    public static Cube createCube(final ReadableVector3f center, final float size, final Color ...colors){
        return createCube(center.getX(), center.getY(), center.getZ(), size, colors);
    }

    /**
     * Creates Block
     *
     * @param x x-axis
     * @param y y-axis
     * @param z z-axis
     * @param size size
     * @param colors colors
     * @return new block
     */
    public static Cube createCube(
            final float x,
            final float y,
            final float z,
            final float size,
            final Color ...colors
    ){
        return createBlock(x, y, z, size, size, size, colors);
    }

    /**
     * Creates block
     *
     * @param x x position
     * @param y y position
     * @param z z position
     * @param l length
     * @param h height
     * @param d depth
     * @param colors colors
     * @return new block
     */
    public static Block createBlock(
            final float x,
            final float y,
            final float z,
            final float l,
            final float h,
            final float d,
            final Color ...colors
    ){
        final Queue<Color> colorQueue = generateColors(colors);

        final List<BlockSide> sides = new ArrayList<>(CUBE_SIDES);
        for(final BlockSideType type : BlockSideType.values()){
            sides.add(new BlockSide(type, calculateSideVertices(type, x, y, z, l, h, d), colorQueue.poll()));
        }

        return new BlockImpl(x, y, z, l, h, d, sides);
    }

    /**
     * Calculates block's vertices
     *
     * @param type block type
     * @param x x position
     * @param y y position
     * @param z z position
     * @param l length
     * @param h height
     * @param d depth
     * @return block's vertices
     */
    private static List<ReadableVector3f> calculateSideVertices(
            final BlockSideType type,
            final float x,
            final float y,
            final float z,
            final float l,
            final float h,
            final float d
    ){
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

    /**
     * Calculates block's top vertices
     *
     * @param x x position
     * @param y y position
     * @param z z position
     * @param l length
     * @param h height
     * @param d depth
     * @return block's vertices
     */
    private static List<Vector3f> calculateTopSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y + h, z - d),
                new Vector3f(x + l, y + h, z - d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * Calculates block's bottom vertices
     *
     * @param x x position
     * @param y y position
     * @param z z position
     * @param l length
     * @param h height
     * @param d depth
     * @return block's vertices
     */
    private static List<Vector3f> calculateBottomSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x - l, y - h, z - d),
                new Vector3f(x + l, y - h, z - d)

        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * Calculates block's left vertices
     *
     * @param x x position
     * @param y y position
     * @param z z position
     * @param l length
     * @param h height
     * @param d depth
     * @return block's vertices
     */
    private static List<Vector3f> calculateLeftSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x - l, y - h, z - d),
                new Vector3f(x - l, y + h, z - d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * Calculates block's right vertices
     *
     * @param x x position
     * @param y y position
     * @param z z position
     * @param l length
     * @param h height
     * @param d depth
     * @return block's vertices
     */
    private static List<Vector3f> calculateRightSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x + l, y - h, z - d),
                new Vector3f(x + l, y + h, z - d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * Calculates block's front vertices
     *
     * @param x x position
     * @param y y position
     * @param z z position
     * @param l length
     * @param h height
     * @param d depth
     * @return block's vertices
     */
    private static List<Vector3f> calculateFrontSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x + l, y - h, z + d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * Calculates block's back side vertices
     *
     * @param x x position
     * @param y y position
     * @param z z position
     * @param l length
     * @param h height
     * @param d depth
     * @return block's vertices
     */
    private static List<Vector3f> calculateBackSideVertices(float x, float y, float z, float l, float h, float d){
        return Stream.of(
                new Vector3f(x + l, y + h, z - d),
                new Vector3f(x - l, y + h, z - d),
                new Vector3f(x - l, y - h, z - d),
                new Vector3f(x + l, y - h, z - d)
        ).collect(Collectors.collectingAndThen(Collectors.toList(), Collections::unmodifiableList));
    }

    /**
     * Generates colors
     *
     * @param colors colors
     * @return color queue
     */
    private static Queue<Color> generateColors(final Color ...colors){
        if(Objects.isNull(colors) || colors.length < 1){
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

    /**
     * Generates colors
     *
     * @return color queue
     */
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

    /**
     * Generates color
     * @param color color
     * @return color queue
     */
    private static Queue<Color> generateSingleColor(final Color color){
        return IntStream.range(0, CUBE_SIDES).mapToObj(i -> color).collect(Collectors.toCollection(ArrayDeque::new));
    }

    /**
     * Internal block implementation
     */
    private static final class BlockImpl extends Block {
        /**
         * width
         */
        private final float w;
        /**
         * height
         */
        private final float h;
        /**
         * depth
         */
        private final float d;
        /**
         * bounds
         */
        private final Bound b;

        /**
         * Constructor
         *
         * @param x x axis
         * @param y y axis
         * @param z z axis
         * @param w width
         * @param h height
         * @param d depth
         * @param sides sides
         */
        private BlockImpl(
                final float x,
                final float y,
                final float z,
                final float w,
                final float h,
                final float d,
                final List<BlockSide> sides
        ) {
            super(x, y, z, sides);
            this.w = w;
            this.h = h;
            this.d = d;
            this.b = new BoundBox(x,y,z,w,h,d);
        }

        /**
         * Getter for bounds
         *
         * @return bounds
         */
        @Override
        public Bound getBounds() {
            return b;
        }

        /**
         * Getter for length
         *
         * @return length
         */
        @Override
        public double getWidth() {
            return w;
        }

        /**
         * Getter for height
         *
         * @return height
         */
        @Override
        public double getHeight() {
            return h;
        }

        /**
         * Getter for depth
         *
         * @return depth
         */
        @Override
        public double getDepth() {
            return d;
        }

        /**
         * Getter for type
         *
         * @return type
         */
        @Override
        public BlockType getType() {
            return null;
        }
    }

    /**
     * Class to represent a blocks side
     */
    public static final class BlockSide {
        /**
         * type
         */
        private final BlockSideType type;
        /**
         * vertices
         */
        private final List<ReadableVector3f> vertices;
        /**
         * color
         */
        private final Color color;

        /**
         * Constructor
         *
         * @param type block side type
         * @param vertices side vertices
         * @param color side's color
         */
        private BlockSide(final BlockSideType type, final List<ReadableVector3f> vertices, final Color color) {
            this.type = type;
            this.vertices = vertices;
            this.color = color;
        }

        /**
         * Getter for vertices
         *
         * @return list of sides vertices
         */
        public List<ReadableVector3f> getVertices() {
            return Collections.unmodifiableList(vertices);
        }

        /**
         * Getter for color
         *
         * @return sides color
         */
        public Color getColor() {
            return color;
        }

        /**
         * Getter for type
         *
         * @return side's type
         */
        public BlockSideType getType() {
            return type;
        }

        /**
         * Getter for vertices
         *
         * @return side vertices
         */
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

}
