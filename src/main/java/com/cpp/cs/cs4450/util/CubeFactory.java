/***************************************************************
 * file: CubeFactory.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Checkpoint 1
 * date last modified: 3/05/2020
 *
 * purpose: Factory class to create cubes
 *
 ****************************************************************/
package com.cpp.cs.cs4450.util;

import com.cpp.cs.cs4450.model.cube.Cube;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CubeFactory {
    private static final int CUBE_SIDES = CubeSideType.values().length;
    private static final List<Color> COLORS = List.of(
            Color.BLUE,
            Color.GREEN,
            Color.RED,
            Color.YELLOW,
            Color.MAGENTA,
            Color.CYAN,
            Color.ORANGE
    );

    public enum CubeSideType {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        FRONT,
        BACK
    }

    public static Cube create(final float size, final Color ...colors){
        return create(0,0,0, size, colors);
    }

    public static Cube create(final ReadableVector3f center, final float size, final Color ...colors){
        return create(center.getX(), center.getY(), center.getZ(), size, colors);
    }

    public static Cube create(final float x, final float y, final float z, final float size, final Color ...colors){
        return create(x, y, z, size, size, size, colors);
    }

    public static Cube create(final float x, final float y, final float z, final float l, final float h, final float d, final Color ...colors){
        final Queue<Color> colorQueue = generateColors(colors);

        final List<CubeSide> sides = new ArrayList<>(CUBE_SIDES);
        for(CubeSideType type : CubeSideType.values()){
            sides.add(new CubeSide(type, calculateSideVertices(type, x, y, z, l, h, d), colorQueue.poll()));
        }

        return new CubeImpl(sides);
    }

    private static List<ReadableVector3f> calculateSideVertices(final CubeSideType type, float x, float y, float z, float l, float h, float d){
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

    private static List<Vector3f> calculateTopSideVertices(float x, float y, float z, float l, float h, float d){
        return List.of(
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x + l, y + h, z - d),
                new Vector3f(x - l, y + h, z - d)
        );
    }

    private static List<Vector3f> calculateBottomSideVertices(float x, float y, float z, float l, float h, float d){
        return List.of(
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x + l, y - h, z - d),
                new Vector3f(x - l, y - h, z - d)
        );
    }
    private static List<Vector3f> calculateLeftSideVertices(float x, float y, float z, float l, float h, float d){
        return List.of(
                new Vector3f(x - l, y - h, z + d),
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y + h, z - d),
                new Vector3f(x - l, y - h, z - d)
        );
    }
    private static List<Vector3f> calculateRightSideVertices(float x, float y, float z, float l, float h, float d){
        return List.of(
                new Vector3f(x + l, y + h, z - d),
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x + l, y - h, z - d)
        );
    }
    private static List<Vector3f> calculateFrontSideVertices(float x, float y, float z, float l, float h, float d){
        return List.of(
                new Vector3f(x + l, y - h, z + d),
                new Vector3f(x + l, y + h, z + d),
                new Vector3f(x - l, y + h, z + d),
                new Vector3f(x - l, y - h, z + d)
        );
    }
    private static List<Vector3f> calculateBackSideVertices(float x, float y, float z, float l, float h, float d){
        return List.of(
                new Vector3f(x + l, y + h, z - d),
                new Vector3f(x + l, y - h, z - d),
                new Vector3f(x - l, y - h, z - d),
                new Vector3f(x - l, y + h, z - d)
        );
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
            for(Color color : COLORS){
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
        while(set.size() < CUBE_SIDES){
            for(Color color : colors){
                set.add(color);
                if(set.size() >= CUBE_SIDES){
                    break;
                }
            }
        }

        return new ArrayDeque<>(set);
    }

    private static Queue<Color> generateSingleColor(final Color color){
        return IntStream.range(0, CUBE_SIDES).mapToObj(i -> color).collect(Collectors.toCollection(ArrayDeque::new));
    }

    private static final class CubeImpl extends Cube {
        private CubeImpl(final List<CubeSide> sides) {
            super(sides);
        }
    }

    public static class CubeSide {
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
