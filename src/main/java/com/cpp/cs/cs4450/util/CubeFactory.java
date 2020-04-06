/***************************************************************
 * file: CubeFactory.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 2
 * date last modified: 04/06/2020
 *
 * purpose: Utility factory class responible for instantiating cube objects
 *
 ****************************************************************/
package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.model.cube.Cube;
import com.cpp.cs.cs4450.model.cube.Block;
import com.cpp.cs.cs4450.model.cube.BlockType;
import com.cpp.cs.cs4450.model.cube.MultiTexturedBlock;
import com.cpp.cs.cs4450.model.cube.SingleTexturedBlock;
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

public final class CubeFactory {
    private static final int CUBE_SIDES = CubeSideType.values().length;
    private static final List<Color> COLORS = Collections.unmodifiableList(Arrays.asList(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA, Color.CYAN, Color.ORANGE));

    private static final Random random = new Random();

    public enum CubeSideType {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        FRONT,
        BACK
    }

    /*
    Creates a random textured cube
     */
    public static Cube createRandomTexturedCube(final float x, final float y, final float z, final float size){
        return createTexturedCube(BlockType.values()[random.nextInt(BlockType.values().length)], x, y, z, size);
    }

    /*
    Crates a textured cube based on parameters passed in
     */
    public static Cube createTexturedCube(final BlockType type, final float x, final float y, final float z, final float size){
        return createTexturedCube(type, x, y, z, size, size, size);
    }

    /*
    Crates a textured cube based on parameters passed in
     */
    public static Cube createTexturedCube(final BlockType type, final float x, final float y, final float z, final float l, final float h, final float d){
        final List<CubeSide> sides = new ArrayList<>(CUBE_SIDES);
        for(CubeSideType side : CubeSideType.values()){
            sides.add(new CubeSide(side, calculateSideVertices(side, x, y, z, l, h, d), Color.BLUE));
        }

        return isMultiTextured(type)
                ? new MultiTexturedBlock(x,y,z,type,sides, type.getPaths())
                : new SingleTexturedBlock(x,y,z,type,sides, type.getPaths());
    }

    /*
    Creates a standard cube based on parameters passed in
     */
    public static Cube create(float size, Color ...colors){
        return create(0,0,0, size, colors);
    }

    /*
    Creates a standard cube based on parameters passed in
     */
    public static Cube create(final ReadableVector3f center, final float size, final Color ...colors){
        return create(center.getX(), center.getY(), center.getZ(), size, colors);
    }

    /*
    Creates a standard cube based on parameters passed in
     */
    public static Cube create(final float x, final float y, final float z, final float size, final Color ...colors){
        return create(x, y, z, size, size, size, colors);
    }

    /*
    Creates a standard cube based on parameters passed in
     */
    public static Cube create(final float x, final float y, final float z, final float l, final float h, final float d, final Color ...colors){
        final Queue<Color> colorQueue = generateColors(colors);

        final List<CubeSide> sides = new ArrayList<>(CUBE_SIDES);
        for(CubeSideType type : CubeSideType.values()){
            sides.add(new CubeSide(type, calculateSideVertices(type, x, y, z, l, h, d), colorQueue.poll()));
        }

        return new BlockImpl(x, y, z, sides);
    }

    /*
    Calculates vertices of cube sides
     */
    private static List<ReadableVector3f> calculateSideVertices(final CubeSideType type, final float x, final float y, final float z, final float l, final float h, final float d){
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
            default: vertices = new ArrayList<>();
                break;
        }

        return Collections.unmodifiableList(vertices);
    }

    /*
    Calculates top vertices
     */
    private static List<Vector3f> calculateTopSideVertices(float x, float y, float z, float l, float h, float d){
        return Arrays.asList(
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x + l, y + h, z - d),
                new Vector3f(x - l, y + h, z - d)
        );
    }

    /*
    Calculates bottom vertices
     */
    private static List<Vector3f> calculateBottomSideVertices(float x, float y, float z, float l, float h, float d){
        return Arrays.asList(
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x + l, y - h, z - d),
                new Vector3f(x - l, y - h, z - d)
        );
    }

    /*
    Calculates left vertices
     */
    private static List<Vector3f> calculateLeftSideVertices(float x, float y, float z, float l, float h, float d){
        return Arrays.asList(
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y + h, z - d),
                new Vector3f(x - l, y - h, z - d)
        );
    }

    /*
    Calculates right vertices
     */
    private static List<Vector3f> calculateRightSideVertices(float x, float y, float z, float l, float h, float d){
        return Arrays.asList(
                new Vector3f(x + l, y + h, z - d),
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x + l, y - h, z - d)
        );
    }

    /*
    Calculates front vertices
     */
    private static List<Vector3f> calculateFrontSideVertices(float x, float y, float z, float l, float h, float d){
        return Arrays.asList(
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y - h, z + d)
        );
    }

    /*
    Calculates back vertices
     */
    private static List<Vector3f> calculateBackSideVertices(float x, float y, float z, float l, float h, float d){
        return Arrays.asList(
                new Vector3f(x + l, y + h, z - d),
                new Vector3f(x + l, y - h, z - d),
                new Vector3f(x - l, y - h, z - d),
                new Vector3f(x - l, y + h, z - d)
        );
    }

    /*
    Generates color queue
     */
    private static Queue<Color> generateColors(final Color ...colors){
        if(colors == null || colors.length < 1){
            return generateUniqueColors();
        }

        if(colors.length == 1){
            return generateSingleColor(colors[0]);
        }

        Queue<Color> queue = new ArrayDeque<>(Arrays.asList(colors));
        while(queue.size() <= CUBE_SIDES){
            for(Color color : COLORS){
                if(queue.size() > CUBE_SIDES){
                    break;
                }
                queue.add(color);
            }
        }

        return queue;
    }

    /*
    Generates color queue of unique colors
     */
    private static Queue<Color> generateUniqueColors(){
        if(COLORS.size() < CUBE_SIDES){
            return generateColors(COLORS.toArray(new Color[0]));
        }

        final List<Color> colors = new ArrayList<>(COLORS);

        final Set<Color> set = new LinkedHashSet<>(CUBE_SIDES);
        while(set.size() <= CUBE_SIDES){
            Collections.shuffle(colors);
            for(Color color : colors){
                if(set.size() > CUBE_SIDES){
                    break;
                }
                set.add(color);
            }
        }

        return new ArrayDeque<>(set);
    }

    /*
    Generates color queue of single color
     */
    private static Queue<Color> generateSingleColor(final Color color){
        return IntStream.range(0, CUBE_SIDES).mapToObj(i -> color).collect(Collectors.toCollection(ArrayDeque::new));
    }

    /*
    Basic block implementaions
     */
    private static class BlockImpl extends Block {
        private BlockImpl(float x, float y, float z, List<CubeSide> sides) {
            super(x, y, z, sides);
        }
    }

    /*
    Checks if type is multitextured
     */
    private static boolean isMultiTextured(final BlockType type){
        return type.getPaths().values().stream().distinct().count() > 1;
    }

    /*
    Inner class representing a Cube's Side
     */
    public static final class CubeSide {
        private final CubeSideType type;
        private final List<ReadableVector3f> vertices;
        private final Color color;


        private CubeSide(final CubeSideType type, final List<ReadableVector3f> vertices, final Color color) {
            this.type = type;
            this.vertices = vertices;
            this.color = color;
        }

        public List<ReadableVector3f> getVertices() {
            return vertices;
        }

        public Color getColor() {
            return color;
        }

        public CubeSideType getType() {
            return type;
        }

    }

}
