package com.cpp.cs.cs4450.config;

import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;

import java.util.Arrays;

import java.util.Collections;
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

    private TexturesConfiguration(){}

    private static Map<CubeSideType, String> generateSingleTextureMap(final String path){
        return Arrays.stream(CubeSideType.values())
                .map(cst -> new SimpleImmutableEntry<>(cst, path))
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(Entry::getKey, Entry::getValue, (k0, k1) -> k0),
                        Collections::unmodifiableMap
                ));
    }

}
