package com.cpp.cs.cs4450.config;

import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;
import org.lwjgl.util.vector.ReadableVector2f;
import org.lwjgl.util.vector.Vector2f;

import java.util.Arrays;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class TexturesConfiguration {
    private static final String GRASS_TOP_TEXTURE_PATH = "assets/grass-top-tex.png";
    private static final String GRASS_SIDE_1_TEXTURE_PATH = "assets/grass-side-1-tex.png";
    private static final String GRASS_SIDE_2_TEXTURE_PATH = "assets/grass-side-2-tex.png";
    private static final String DIRT_TEXTURE_PATH = "assets/dirt-tex.png";
    private static final String WATER_TEXTURE_PATH = "assets/water-tex.png";
    private static final String SAND_TEXTURE_PATH = "assets/sand-tex.png";
    private static final String STONE_TEXTURE_PATH = "assets/stone-tex.png";
    private static final String BEDROCK_TEXTURE_PATH = "assets/bedrock-tex.png";

    public static final String TERRAIN_TEXTURE_PATH = "assets/terrain.png";

    private static final List<ReadableVector2f> GRASS_TOP_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(2,0), new Vector2f(1,0), new Vector2f(1,1), new Vector2f(2,1))
    );

    private static final List<ReadableVector2f> GRASS_SIDE_1_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,0), new Vector2f(0,0), new Vector2f(0,1), new Vector2f(1,1))
    );

    private static final List<ReadableVector2f> GRASS_SIDE_2_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(Arrays.asList(new Vector2f(1,0), new Vector2f(0,0), new Vector2f(0,1), new Vector2f(1,1))
    );

    private static final List<ReadableVector2f> GRASS_SIDE_3_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(Arrays.asList(new Vector2f(0,0), new Vector2f(0,1), new Vector2f(1,1), new Vector2f(1,0))
    );

    private static final List<ReadableVector2f> GRASS_SIDE_4_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(Arrays.asList(new Vector2f(0,1), new Vector2f(1,1), new Vector2f(1,0), new Vector2f(0,0))
    );

    private static final List<ReadableVector2f> GRASS_SIDE_5_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(Arrays.asList(new Vector2f(1,1), new Vector2f(1,0), new Vector2f(0,0), new Vector2f(0,1))
    );


    private static final List<ReadableVector2f> DIRT_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,1), new Vector2f(0,1), new Vector2f(0,2), new Vector2f(1,2))
    );

    private static final List<ReadableVector2f> SAND_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,2), new Vector2f(0,2), new Vector2f(0,3), new Vector2f(1,3))
    );

    private static final List<ReadableVector2f> WATER_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,3), new Vector2f(0,3), new Vector2f(0,4), new Vector2f(1,4))
    );

    private static final List<ReadableVector2f> STONE_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,4), new Vector2f(0,4), new Vector2f(0,5), new Vector2f(1,5))
    );

    private static final List<ReadableVector2f> BEDROCK_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,5), new Vector2f(0,5), new Vector2f(0,6), new Vector2f(1,6))
    );

    public static final Map<CubeSideType, String> GRASS_TEXTURES = Stream.of(
            new SimpleImmutableEntry<>(CubeSideType.TOP, GRASS_TOP_TEXTURE_PATH),
            new SimpleImmutableEntry<>(CubeSideType.BOTTOM, DIRT_TEXTURE_PATH),
            new SimpleImmutableEntry<>(CubeSideType.LEFT, GRASS_SIDE_1_TEXTURE_PATH),
            new SimpleImmutableEntry<>(CubeSideType.RIGHT, GRASS_SIDE_1_TEXTURE_PATH),
            new SimpleImmutableEntry<>(CubeSideType.FRONT, GRASS_SIDE_2_TEXTURE_PATH),
            new SimpleImmutableEntry<>(CubeSideType.BACK, GRASS_SIDE_2_TEXTURE_PATH)
    ).collect(Collectors.collectingAndThen(
            Collectors.toMap(Entry::getKey, Entry::getValue, (k0, k1) -> k0),
            Collections::unmodifiableMap
    ));

    public static final Map<CubeSideType, String> DIRT_TEXTURES = generateSingleTextureMap(DIRT_TEXTURE_PATH);

    public static final Map<CubeSideType, String> WATER_TEXTURES = generateSingleTextureMap(WATER_TEXTURE_PATH);

    public static final Map<CubeSideType, String> SAND_TEXTURES = generateSingleTextureMap(SAND_TEXTURE_PATH);

    public static final Map<CubeSideType, String> STONE_TEXTURES = generateSingleTextureMap(STONE_TEXTURE_PATH);

    public static final Map<CubeSideType, String> BEDROCK_TEXTURES = generateSingleTextureMap(BEDROCK_TEXTURE_PATH);

    public static final Map<CubeSideType, List<ReadableVector2f>> GRASS_TEXTURE_VERTICES = Stream.of(
            new SimpleImmutableEntry<>(CubeSideType.TOP, GRASS_TOP_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(CubeSideType.BOTTOM, DIRT_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(CubeSideType.LEFT, GRASS_SIDE_3_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(CubeSideType.RIGHT, GRASS_SIDE_3_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(CubeSideType.FRONT, GRASS_SIDE_2_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(CubeSideType.BACK, GRASS_SIDE_1_TEXTURE_VERTICES_LIST)
    ).collect(Collectors.collectingAndThen(
            Collectors.toMap(Entry::getKey, Entry::getValue, (k0, k1) -> k0),
            Collections::unmodifiableMap
    ));

    public static final Map<CubeSideType, List<ReadableVector2f>> DIRT_TEXTURE_VERTICES = generateSingleTextureVerticesMap(DIRT_TEXTURE_VERTICES_LIST);

    public static final Map<CubeSideType, List<ReadableVector2f>> WATER_TEXTURE_VERTICES = generateSingleTextureVerticesMap(WATER_TEXTURE_VERTICES_LIST);

    public static final Map<CubeSideType, List<ReadableVector2f>> SAND_TEXTURE_VERTICES = generateSingleTextureVerticesMap(SAND_TEXTURE_VERTICES_LIST);

    public static final Map<CubeSideType, List<ReadableVector2f>> STONE_TEXTURE_VERTICES = generateSingleTextureVerticesMap(STONE_TEXTURE_VERTICES_LIST);

    public static final Map<CubeSideType, List<ReadableVector2f>> BEDROCK_TEXTURE_VERTICES = generateSingleTextureVerticesMap(BEDROCK_TEXTURE_VERTICES_LIST);

    private TexturesConfiguration(){}

    private static Map<CubeSideType, String> generateSingleTextureMap(final String path){
        return Stream.of(CubeSideType.FRONT)
                .map(cst -> new SimpleImmutableEntry<>(cst, path))
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Entry::getKey, Entry::getValue, (k0, k1) -> k0),
                        Collections::unmodifiableMap
                ));
    }

    private static Map<CubeSideType, List<ReadableVector2f>> generateSingleTextureVerticesMap(List<ReadableVector2f> vertices){
        return Arrays.stream(CubeSideType.values())
                .map(cst -> new SimpleImmutableEntry<>(cst, vertices))
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Entry::getKey, Entry::getValue, (k0, k1) -> k0),
                        Collections::unmodifiableMap
                ));
    }

}
