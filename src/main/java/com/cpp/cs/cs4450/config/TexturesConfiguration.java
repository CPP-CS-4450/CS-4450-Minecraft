/***************************************************************
 * file: TexturesConfiguration.java
 * team: Team Dood
 * author: Bryan Ayala, Laween Piromari, Rigoberto Canales Maldonado, Jaewon Hong
 * class: CS 4450 – Computer Graphics
 *
 * assignment: Semester Project - Final Checkpoint
 * date last modified: 04/25/2020
 *
 * purpose: Defines paths for the texture files used in the game
 *
 ****************************************************************/
package com.cpp.cs.cs4450.config;

import com.cpp.cs.cs4450.util.BlockFactory.BlockSideType;
import org.lwjgl.util.vector.ReadableVector2f;
import org.lwjgl.util.vector.Vector2f;

import java.util.Arrays;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Configuration for the program's textures
 */
public final class TexturesConfiguration {
    /**
     * File path for top grass texture.
     */
    private static final String GRASS_TOP_TEXTURE_PATH = "assets/grass-top-tex.png";

    /**
     * File path for grass side texture.
     */
    private static final String GRASS_SIDE_1_TEXTURE_PATH = "assets/grass-side-1-tex.png";

    /**
     * File path for grass side texture.
     */
    private static final String GRASS_SIDE_2_TEXTURE_PATH = "assets/grass-side-2-tex.png";

    /**
     * File path for dirt texture.
     */
    private static final String DIRT_TEXTURE_PATH = "assets/dirt-tex.png";

    /**
     * File path for water texture.
     */
    private static final String WATER_TEXTURE_PATH = "assets/water-tex.png";

    /**
     * File path for sand texture.
     */
    private static final String SAND_TEXTURE_PATH = "assets/sand-tex.png";

    /**
     * File path for stone texture.
     */
    private static final String STONE_TEXTURE_PATH = "assets/stone-tex.png";

    /**
     * File path for bedrock texture.
     */
    private static final String BEDROCK_TEXTURE_PATH = "assets/bedrock-tex.png";

    /**
     * File path for chunk texture.
     */
    public static final String TERRAIN_TEXTURE_PATH = "assets/terrain.png";

    /**
     * Map of grass textures
     */
    public static final Map<BlockSideType, String> GRASS_TEXTURES = Stream.of(
            new SimpleImmutableEntry<>(BlockSideType.TOP, GRASS_TOP_TEXTURE_PATH),
            new SimpleImmutableEntry<>(BlockSideType.BOTTOM, DIRT_TEXTURE_PATH),
            new SimpleImmutableEntry<>(BlockSideType.LEFT, GRASS_SIDE_1_TEXTURE_PATH),
            new SimpleImmutableEntry<>(BlockSideType.RIGHT, GRASS_SIDE_1_TEXTURE_PATH),
            new SimpleImmutableEntry<>(BlockSideType.FRONT, GRASS_SIDE_2_TEXTURE_PATH),
            new SimpleImmutableEntry<>(BlockSideType.BACK, GRASS_SIDE_2_TEXTURE_PATH)
    ).collect(Collectors.collectingAndThen(
            Collectors.toMap(Entry::getKey, Entry::getValue, (k0, k1) -> k0, () -> new EnumMap<>(BlockSideType.class)),
            Collections::unmodifiableMap
    ));

    /**
     * Map of dirt textures
     */
    public static final Map<BlockSideType, String> DIRT_TEXTURES = generateSingleTextureMap(DIRT_TEXTURE_PATH);

    /**
     * Map of water textures
     */
    public static final Map<BlockSideType, String> WATER_TEXTURES = generateSingleTextureMap(WATER_TEXTURE_PATH);

    /**
     * Map of sand textures
     */
    public static final Map<BlockSideType, String> SAND_TEXTURES = generateSingleTextureMap(SAND_TEXTURE_PATH);

    /**
     * Map of stone textures
     */
    public static final Map<BlockSideType, String> STONE_TEXTURES = generateSingleTextureMap(STONE_TEXTURE_PATH);

    /**
     * Map of bedrock textures
     */
    public static final Map<BlockSideType, String> BEDROCK_TEXTURES = generateSingleTextureMap(BEDROCK_TEXTURE_PATH);

    /**
     * List of grass top texture vertices
     */
    private static final List<ReadableVector2f> GRASS_TOP_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(2,0), new Vector2f(1,0), new Vector2f(1,1), new Vector2f(2,1))
    );

    /**
     * List of grass side texture vertices
     */
    private static final List<ReadableVector2f> GRASS_SIDE_1_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(0,0), new Vector2f(0,1), new Vector2f(1,1), new Vector2f(1,0))
    );

    /**
     * List of grass side texture vertices
     */
    private static final List<ReadableVector2f> GRASS_SIDE_2_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,0), new Vector2f(0,0), new Vector2f(0,1), new Vector2f(1,1))
    );

    /**
     * List of dirt texture vertices
     */
    private static final List<ReadableVector2f> DIRT_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,1), new Vector2f(0,1), new Vector2f(0,2), new Vector2f(1,2))
    );

    /**
     * List of sand texture vertices
     */
    private static final List<ReadableVector2f> SAND_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,2), new Vector2f(0,2), new Vector2f(0,3), new Vector2f(1,3))
    );

    /**
     * List of water texture vertices
     */
    private static final List<ReadableVector2f> WATER_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,3), new Vector2f(0,3), new Vector2f(0,4), new Vector2f(1,4))
    );

    /**
     * List of stone texture vertices
     */
    private static final List<ReadableVector2f> STONE_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,4), new Vector2f(0,4), new Vector2f(0,5), new Vector2f(1,5))
    );

    /**
     * List of bedrock texture vertices
     */
    private static final List<ReadableVector2f> BEDROCK_TEXTURE_VERTICES_LIST = Collections.unmodifiableList(
            Arrays.asList(new Vector2f(1,5), new Vector2f(0,5), new Vector2f(0,6), new Vector2f(1,6))
    );

    /**
     * Map of grass texture vertices
     */
    public static final Map<BlockSideType, List<ReadableVector2f>> GRASS_TEXTURE_VERTICES = Stream.of(
            new SimpleImmutableEntry<>(BlockSideType.TOP, GRASS_TOP_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(BlockSideType.BOTTOM, DIRT_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(BlockSideType.LEFT, GRASS_SIDE_1_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(BlockSideType.RIGHT, GRASS_SIDE_1_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(BlockSideType.FRONT, GRASS_SIDE_2_TEXTURE_VERTICES_LIST),
            new SimpleImmutableEntry<>(BlockSideType.BACK, GRASS_SIDE_2_TEXTURE_VERTICES_LIST)
    ).collect(Collectors.collectingAndThen(
            Collectors.toMap(Entry::getKey, Entry::getValue, (k0, k1) -> k0, () -> new EnumMap<>(BlockSideType.class)),
            Collections::unmodifiableMap
    ));

    /**
     * Map of dirt texture vertices
     */
    public static final Map<BlockSideType, List<ReadableVector2f>> DIRT_TEXTURE_VERTICES = generateSingleTextureVerticesMap(DIRT_TEXTURE_VERTICES_LIST);

    /**
     * Map of water texture vertices
     */
    public static final Map<BlockSideType, List<ReadableVector2f>> WATER_TEXTURE_VERTICES = generateSingleTextureVerticesMap(WATER_TEXTURE_VERTICES_LIST);

    /**
     * Map of sand texture vertices
     */
    public static final Map<BlockSideType, List<ReadableVector2f>> SAND_TEXTURE_VERTICES = generateSingleTextureVerticesMap(SAND_TEXTURE_VERTICES_LIST);

    /**
     * Map of stone texture vertices
     */
    public static final Map<BlockSideType, List<ReadableVector2f>> STONE_TEXTURE_VERTICES = generateSingleTextureVerticesMap(STONE_TEXTURE_VERTICES_LIST);

    /**
     * Map of bedrock texture vertices
     */
    public static final Map<BlockSideType, List<ReadableVector2f>> BEDROCK_TEXTURE_VERTICES = generateSingleTextureVerticesMap(BEDROCK_TEXTURE_VERTICES_LIST);

    /**
     * Private Constructor
     */
    private TexturesConfiguration(){
        throw new UnsupportedOperationException();
    }

    /**
     * Builds map for all sides from single path
     *
     * @param path file path of texture
     * @return Map of sides to texture file paths
     */
    private static Map<BlockSideType, String> generateSingleTextureMap(final String path){
        return Stream.of(BlockSideType.FRONT)
                .map(cst -> new SimpleImmutableEntry<>(cst, path))
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Entry::getKey,
                                Entry::getValue,
                                (k0, k1) -> k0,
                                () -> new EnumMap<>(BlockSideType.class)
                        ),
                        Collections::unmodifiableMap
                ));
    }

    /**
     * Builds map for all sides from single path
     *
     * @param vertices list of vertices
     * @return map of sides to vertices
     */
    private static Map<BlockSideType, List<ReadableVector2f>> generateSingleTextureVerticesMap(List<ReadableVector2f> vertices){
        return Arrays.stream(BlockSideType.values())
                .map(cst -> new SimpleImmutableEntry<>(cst, vertices))
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                Entry::getKey,
                                Entry::getValue,
                                (k0, k1) -> k0,
                                () -> new EnumMap<>(BlockSideType.class)
                        ),
                        Collections::unmodifiableMap
                ));
    }

}
