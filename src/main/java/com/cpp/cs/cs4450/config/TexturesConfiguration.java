package com.cpp.cs.cs4450.config;

import com.cpp.cs.cs4450.util.CubeFactory.CubeSideType;

import java.util.Arrays;

import java.util.Map;
import java.util.stream.Collectors;

public final class TexturesConfiguration {
    private static final String GRASS_TOP_TEXTURE_PATH = "assets/grass-top-tex.png";
    private static final String GRASS_SIDE_1_TEXTURE_PATH = "assets/grass-side-1-tex.png";
    private static final String GRASS_SIDE_2_TEXTURE_PATH = "assets/grass-side-2-tex.png";
    private static final String DIRT_TEXTURE_PATH = "assets/dirt-tex.png";
    private static final String WATER_TEXTURE_PATH = "assets/water-tex.png";
    private static final String SAND_TEXTURE_PATH = "assets/sand-tex.png";
    private static final String STONE_TEXTURE_PATH = "assets/stone-tex.png";
    private static final String BEDROCK_TEXTURE_PATH = "assets/bedrock-tex.png";


    public static final Map<CubeSideType, String> GRASS_TEXTURES = Map.of(
            CubeSideType.TOP, GRASS_TOP_TEXTURE_PATH,
            CubeSideType.BOTTOM, DIRT_TEXTURE_PATH,
            CubeSideType.LEFT, GRASS_SIDE_1_TEXTURE_PATH,
            CubeSideType.RIGHT, GRASS_SIDE_1_TEXTURE_PATH,
            CubeSideType.FRONT, GRASS_SIDE_2_TEXTURE_PATH,
            CubeSideType.BACK, GRASS_SIDE_2_TEXTURE_PATH

    );

    public static final Map<CubeSideType, String> DIRT_TEXTURES = generateSingleTextureMap(DIRT_TEXTURE_PATH);

    public static final Map<CubeSideType, String> WATER_TEXTURES = generateSingleTextureMap(WATER_TEXTURE_PATH);

    public static final Map<CubeSideType, String> SAND_TEXTURES = generateSingleTextureMap(SAND_TEXTURE_PATH);

    public static final Map<CubeSideType, String> STONE_TEXTURES = generateSingleTextureMap(STONE_TEXTURE_PATH);

    public static final Map<CubeSideType, String> BEDROCK_TEXTURES = generateSingleTextureMap(BEDROCK_TEXTURE_PATH);

    private TexturesConfiguration(){}

    private static Map<CubeSideType, String> generateSingleTextureMap(final String path){
        return Map.copyOf(
                Arrays.stream(CubeSideType.values()).collect(
                        Collectors.toMap(
                                cst -> cst,
                                cst -> path,
                                (cst0, cst1) -> cst0
                        )
                )
        );
    }

}
